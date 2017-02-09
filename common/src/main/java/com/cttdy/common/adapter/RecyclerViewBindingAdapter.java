package com.cttdy.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yejingxian on 2017/2/8.
 */
public class RecyclerViewBindingAdapter<T> extends RecyclerView.Adapter<RecyclerViewBindingHolder> {

    private List<T> mList;
    private int variable;
    private int layout;

    private OnAdapterClickListener listener;

    public RecyclerViewBindingAdapter(List<T> list, int variable, int layout) {
        this.variable = variable;
        this.layout = layout;
        setList(list);
    }

    @Override
    public RecyclerViewBindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new RecyclerViewBindingHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewBindingHolder holder, int position) {
        final T obj = mList.get(position);
        final int p = position;
        final View view = holder.itemView;
        holder.getBinding().setVariable(variable, mList.get(position));
        holder.getBinding().executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(view, p, obj);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }

    public synchronized void remove(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public synchronized void add(int position) {
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void replaceData(List<T> list) {
        setList(list);
    }

    private void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public interface OnAdapterClickListener<T> {

        void onClick(View view, int position, T object);

    }
}
