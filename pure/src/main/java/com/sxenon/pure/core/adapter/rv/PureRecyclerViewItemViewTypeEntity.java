/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxenon.pure.core.adapter.rv;

import com.sxenon.pure.core.adapter.IItemViewTypeEntity;

/**
 * ItemViewTypeEntity for RecyclerView
 * Created by Sui on 2016/12/29.
 */

public class PureRecyclerViewItemViewTypeEntity implements IItemViewTypeEntity {
    private final int resourceId;
    private final Class<? extends PureRecyclerViewHolder> viewHolderClass;

    public PureRecyclerViewItemViewTypeEntity(int resourceId, Class<? extends PureRecyclerViewHolder> viewHolderClass) {
        this.resourceId = resourceId;
        this.viewHolderClass = viewHolderClass;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Class<? extends PureRecyclerViewHolder> getViewHolderClass() {
        return viewHolderClass;
    }
}
