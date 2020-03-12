package com.snake.cloudnext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context mContext;
    private List<Data> dataList = new ArrayList<>();

    public Adapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewDesignation;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProfile);
            textViewName = itemView.findViewById(R.id.txtName);
            textViewDesignation = itemView.findViewById(R.id.txtDesignation);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Data data = dataList.get(position);
        holder.textViewName.setText(data.getName());
        holder.textViewDesignation.setText(data.getDesignation());
        Glide.with(mContext)
                .load(data.getImage())
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_account_circle_black_24dp))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
