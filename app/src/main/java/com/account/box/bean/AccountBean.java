package com.account.box.bean;

/**
 * 说明：只保存账号数据，层级关系由另一张表维护
 * Created by Administrator on 2017/8/26.
 */
public class AccountBean {
    private String id;
    private String title;
    private String remark;//标题
    private String account_name;
    private String password;
    private String group_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", account_name='" + account_name + '\'' +
                ", password='" + password + '\'' +
                ", group_id='" + group_id + '\'' +
                '}';
    }
}