package com.gooner10.simpletodo.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter for the SimpleTodo RecyclerView
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ItemHolder> {


    private List<ToDoModel> itemsName;
    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;

    public ToDoAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        itemsName = new ArrayList<>();
    }

    public void setItems(List<ToDoModel> itemsName) {
        this.itemsName = itemsName;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.single_row_recycler_adapter, parent, false);
        return new ItemHolder(itemView, this);
    }


    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.setItemName(itemsName.get(position).getToDoName());
    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ItemHolder item, int position);
    }

//    public void add(int location, String iName){
//        itemsName.add(location, iName);
//        notifyItemInserted(location);
//    }

    public void add(String iName) {

//        itemsName.add(iName);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        if (location >= itemsName.size())
            return;

        itemsName.get(location).deleteFromRealm();
        notifyItemRemoved(location);
    }

    public ToDoModel getItem(int position) {
        return itemsName.get(position);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ToDoAdapter parent;
        TextView textItemName;

        public ItemHolder(View itemView, ToDoAdapter parent) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.parent = parent;
            textItemName = (TextView) itemView.findViewById(R.id.item_name);
        }

        public void setItemName(CharSequence name) {
            textItemName.setText(name);
        }

        public CharSequence getItemName() {
            return textItemName.getText();
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}