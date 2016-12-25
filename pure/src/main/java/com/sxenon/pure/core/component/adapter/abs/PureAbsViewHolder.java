package com.sxenon.pure.core.component.adapter.abs;

import com.sxenon.pure.core.component.adapter.IPureViewHolder;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsViewHolder<T> implements IPureViewHolder<T> {
    private final int position;

    public PureAbsViewHolder(int position){
        this.position=position;
    }

    @Override
    public int getPosition() {
        return position;
    }
}