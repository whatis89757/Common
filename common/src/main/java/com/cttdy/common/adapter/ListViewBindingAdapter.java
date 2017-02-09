package com.cttdy.common.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yejingxian on 2017/2/8.
 */
public class ListViewBindingAdapter<T> extends BaseAdapter {

    private List<T> mList;
    private int variable;
    private int layout;


    public ListViewBindingAdapter(List<T> list, int variable, int layout) {
        this.variable = variable;
        this.layout = layout;
        setList(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void replaceData(List<T> list) {
        setList(list);
    }

    private void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewDataBinding binding;
        if (view == null) {
            // Inflate
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            // Create the binding
            binding = DataBindingUtil.inflate(inflater, layout, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(variable, mList.get(i));
        if(handler != null)
        binding.setVariable(handler.first,handler.second);
        binding.executePendingBindings();
        return binding.getRoot();
    }
    public void setHandler(int variable,Object o){
        handler = new Pair<>(variable,o);
        notifyDataSetChanged();
    }
    Pair<Integer,Object> handler;
}
