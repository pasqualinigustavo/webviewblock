package com.webviewblock.presentation.settings.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webviewblock.databinding.LayoutEmptyHistoryBinding;
import com.webviewblock.databinding.RowHistoryBinding;
import com.webviewblock.domain.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_EMPTY_STATE = 1;
    private List<History> items = null;
    private ClickListenerHistory listener;

    @Override
    public int getItemViewType(int position) {
        if (items.isEmpty())
            return VIEW_TYPE_EMPTY_STATE;
        return VIEW_TYPE_ITEM;
    }

    public interface ClickListenerHistory {
        void clickItem(String url);
    }

    public void setup(List<History> newItems, ClickListenerHistory listener) {
        if(items != null)
            items.clear();
        else items = new ArrayList<>();
        this.listener = listener;
        if (!newItems.isEmpty()) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    public void setItems(List<History> newItems) {
        if(items != null)
            items.clear();
        else items = new ArrayList<>();
        if (!newItems.isEmpty()) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    private void clear() {
        if(items != null)
            items.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                RowHistoryBinding binding = RowHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new MessageViewHolder(binding);
            default:
                LayoutEmptyHistoryBinding bindingHist = LayoutEmptyHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(bindingHist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageViewHolder) {
            History item = items.get(position);
            //url
            MessageViewHolder viewHolder = (MessageViewHolder) holder;
            viewHolder.binding.rowHistoryTextviewUrl.setText(item.getUrl());
            //date
            viewHolder.binding.rowHistoryTextviewDate.setText(item.getTime());
            //click
                viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.clickItem(item.getUrl());
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(LayoutEmptyHistoryBinding binding) {
            super(binding.getRoot());
        }
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {

        RowHistoryBinding binding;

        public MessageViewHolder(RowHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

