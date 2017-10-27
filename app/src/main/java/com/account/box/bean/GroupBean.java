package com.account.box.bean;

import android.support.annotation.NonNull;

import com.account.box.APP;

import java.util.List;

/**
 * 说明：
 * Created by Administrator on 2017/8/26.
 */
public class GroupBean implements Comparable<GroupBean> {
    private String id;
    private String owner_id;
    private String group_name;
    private List<AccountBean> accounts;
    private boolean open = true;//控制是否展开

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<AccountBean> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountBean> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "id='" + id + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", group_name='" + group_name + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public int compareTo(@NonNull GroupBean o) {
        if (o.getOwner_id().equals(APP.getInstance().mUserBean.getUser().getId())) {
            return 0;
        } else {
            return -1;
        }
    }
}
