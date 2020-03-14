package com.snake.cloudnext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.snake.cloudnext.SQLiteDBHelper.TABLE_NAME;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context mContext;
    private List<Data> dataList = new ArrayList<>();
    private int dataId;
    private int[] listId;

    public Adapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewDesignation;
        private LinearLayout linearLayout;
        private CheckBox checkBox;
        private ImageView imageEdit;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProfile);
            textViewName = itemView.findViewById(R.id.txtName);
            textViewDesignation = itemView.findViewById(R.id.txtDesignation);
            linearLayout = itemView.findViewById(R.id.layout);
            imageEdit = itemView.findViewById(R.id.imgEdit);
            checkBox = itemView.findViewById(R.id.checkbox);

        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        final Data data = dataList.get(position);
        listId = new int[dataList.size()];
        holder.textViewName.setText(data.getName());
        if (data.getGender().equals("Male")) {
            holder.textViewDesignation.setText(data.getDesignation() + "    " + new String(Character.toChars(0x1F468)));
        } else if (data.getGender().equals("Female")) {
            holder.textViewDesignation.setText(data.getDesignation() + "    " + new String(Character.toChars(0x1F469)));
        } else {
            holder.textViewDesignation.setText(data.getDesignation() + "    " + new String(Character.toChars(0x26A7)));
        }
        Glide.with(mContext)
                .load(data.getImage())
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_account_circle_grey_24dp))
                .into(holder.imageView);
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateDetails.class);
                intent.putExtra("id", data.getId());
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (holder.imageEdit.getVisibility() == View.VISIBLE) {
                    holder.imageEdit.setVisibility(View.GONE);
                    holder.checkBox.setVisibility(View.VISIBLE);
                } else {
                    holder.imageEdit.setVisibility(View.VISIBLE);
                    holder.checkBox.setVisibility(View.GONE);
                }
                return true;
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.checkBox.isChecked()) {
                    listId[position] = dataList.get(position).getId();
                } else {

                }
            }
        });

    }


    public void deleteMultiple() {

        SQLiteDatabase database = new SQLiteDBHelper(mContext).getReadableDatabase();

        for (int i = 0; i < listId.length; i++) {
            database.delete(TABLE_NAME, "id=" + new String(String.valueOf(listId[i])), null);
        }
        database.close();
        notifyDataSetChanged();
        Toast.makeText(mContext, "Data Deleted ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
