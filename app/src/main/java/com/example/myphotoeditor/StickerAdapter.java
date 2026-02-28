package com.example.myphotoeditor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditor.databinding.ItemStickerBinding;

import java.util.ArrayList;
import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerVH> {

    public interface OnStickerClick {
        void onClick(StickerModel model);
    }

    private final List<StickerModel> allItems;
    private final List<StickerModel> visibleItems;
    private final OnStickerClick listener;

    public StickerAdapter(List<StickerModel> items, OnStickerClick listener) {
        this.allItems = new ArrayList<>(items);
        this.visibleItems = new ArrayList<>(items);
        this.listener = listener;
    }

    public void setCategory(String category) {
        visibleItems.clear();
        if ("Hepsi".equals(category)) {
            visibleItems.addAll(allItems);
        } else {
            for (StickerModel item : allItems) {
                if (item.category.equals(category)) {
                    visibleItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StickerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStickerBinding binding = ItemStickerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StickerVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerVH holder, int position) {
        StickerModel model = visibleItems.get(position);
        holder.binding.tvName.setText(model.name);
        holder.binding.ivThumb.setImageBitmap(StickerRenderer.createThumbnail(model, 160, 120));
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(model));
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    static class StickerVH extends RecyclerView.ViewHolder {
        final ItemStickerBinding binding;

        StickerVH(ItemStickerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
