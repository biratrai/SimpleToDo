package com.gooner10.simpletodo.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gooner10.simpletodo.R;
import com.gooner10.simpletodo.model.ToDoModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;


public class RealmDataAdapter extends RealmRecyclerViewAdapter<ToDoModel,RealmDataAdapter.ItemHolder> {

    private List<String> itemsName;
    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private Realm mRealm;
    private RealmResults<ToDoModel> mResults;

    public RealmDataAdapter(Context context, RealmResults<ToDoModel> mResults) {
        super(context,mResults,true);
        this.layoutInflater = LayoutInflater.from(context);
        setResults(mResults);
    }

    @Override
    public RealmDataAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.single_row_todo_adapter, parent, false);
        return new ItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RealmDataAdapter.ItemHolder holder, int position) {
        holder.setItemName(mResults.get(position).getToDoName());
    }

    public void setResults(RealmResults<ToDoModel> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mResults.size();
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

    public void add(String iName) {
        itemsName.add(iName);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        if (location >= itemsName.size())
            return;

        itemsName.remove(location);
        notifyItemRemoved(location);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private RealmDataAdapter parent;
        TextView textItemName;

        public ItemHolder(View itemView, RealmDataAdapter parent) {
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
