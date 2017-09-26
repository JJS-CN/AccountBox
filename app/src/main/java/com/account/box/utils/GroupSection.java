package com.account.box.utils;

import com.account.box.bean.GroupBean;
import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 说明：
 * Created by aa on 2017/9/26.
 */

public class GroupSection extends SectionEntity<GroupBean> {
    public GroupSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public GroupSection(GroupBean groupBean) {
        super(groupBean);
    }
}
