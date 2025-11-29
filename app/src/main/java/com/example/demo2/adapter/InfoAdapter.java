package com.example.demo2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo2.R;
import com.example.demo2.model.InfoItem;
import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.VH> {
    private final List<InfoItem> data = new ArrayList<>();

    public void setData(List<InfoItem> items) {
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvCategory, tvDate, tvTitle, tvDesc;
        VH(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        InfoItem it = data.get(position);
        holder.tvCategory.setText(it.category);
        holder.tvDate.setText(it.date);
        holder.tvTitle.setText(it.title);
        holder.tvDesc.setText(it.desc);
    }

    @Override
    public int getItemCount() { return data.size(); }
}
