package com.account.box.utils;


import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.jjs.base.utils.recyclerview.QuickHolder;

import java.util.List;


/**
 * 说明：
 * Created by aa on 2017/9/26.
 */

public abstract class SectionAdapter<T extends SectionEntity> extends BaseSectionQuickAdapter<T, QuickHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(QuickHolder helper, T item) {
        _convertHead(helper, item);
    }

    @Override
    protected void convert(QuickHolder helper, T item) {
        _convert(helper, item);
    }

    public abstract void _convert(QuickHolder helper, T item);

    public abstract void _convertHead(QuickHolder helper, T item);
}
