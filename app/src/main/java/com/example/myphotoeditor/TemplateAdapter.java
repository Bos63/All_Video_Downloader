package com.example.myphotoeditor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditor.databinding.ItemTemplateBinding;

import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateVH> {

    public interface OnTemplateClick {
        void onClick(TemplateModel model);
    }

    private final List<TemplateModel> items;
    private final OnTemplateClick listener;

    public TemplateAdapter(List<TemplateModel> items, OnTemplateClick listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TemplateVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTemplateBinding binding = ItemTemplateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TemplateVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateVH holder, int position) {
        TemplateModel model = items.get(position);
        holder.binding.tvName.setText(model.name);
        holder.binding.ivThumb.setImageBitmap(TemplateRenderer.createThumbnail(model, 220, 140));
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(model));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TemplateVH extends RecyclerView.ViewHolder {
        final ItemTemplateBinding binding;

        TemplateVH(ItemTemplateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
