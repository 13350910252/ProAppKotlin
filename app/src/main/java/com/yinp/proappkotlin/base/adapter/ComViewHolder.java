package com.yinp.proappkotlin.base.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 通用ViewHolder
 */
public abstract class ComViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public ComViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        init(itemView);
    }

    public void init(View view) {

    }

    OnItemClickListener onItemClickListener;

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(getLayoutPosition(), v);
        }
    }

    public void onItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
