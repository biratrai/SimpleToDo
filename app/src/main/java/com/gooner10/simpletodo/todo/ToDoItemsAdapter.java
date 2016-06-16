package com.gooner10.simpletodo.todo;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.databinding.SingleRowTodoAdapterBinding;
import com.gooner10.simpletodo.model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter for the SimpleTodo RecyclerView
 */

public class ToDoItemsAdapter extends RecyclerView.Adapter<ToDoItemsAdapter.ItemHolder> {

    private List<ToDoModel> itemsName;
    private final OnItemClickListener onItemClickListener;

    public ToDoItemsAdapter(OnItemClickListener onItemClickListener) {
        itemsName = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(List<ToDoModel> itemsName) {
        this.itemsName = itemsName;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SingleRowTodoAdapterBinding todoAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_todo_adapter, parent, false);
        return new ItemHolder(todoAdapterBinding);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        holder.setItemName(itemsName.get(position).getToDoName());
        holder.textItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ItemHolder item, int position);
    }

    public ToDoModel getItem(int position) {
        return itemsName.get(position);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView textItemName;

        public ItemHolder(SingleRowTodoAdapterBinding todoAdapterBinding) {
            super(todoAdapterBinding.getRoot());
            textItemName = todoAdapterBinding.itemName;
        }

        public void setItemName(CharSequence name) {
            textItemName.setText(name);
        }
    }
}