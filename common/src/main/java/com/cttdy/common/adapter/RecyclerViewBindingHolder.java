package com.cttdy.common.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by what on 2017/2/8.
 */
public class RecyclerViewBindingHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;

    public RecyclerViewBindingHolder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
        //mBinding = DataBindingUtil.getBinding(itemView);
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }
}
