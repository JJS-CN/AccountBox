package com.account.box.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：只保存账号数据，层级关系由另一张表维护
 * Created by Administrator on 2017/8/26.
 */
@Entity
public class AccountBean {
    @Id(autoincrement = true)
    private Long sql_id;
    @Transient
    private String id;
    private String userId;
    private String title;
    private String remark;//标题
    private String account_name;
    private String password;
    private String group_id;


    @Generated(hash = 274189084)
    public AccountBean(Long sql_id, String userId, String title, String remark,
                       String account_name, String password, String group_id) {
        this.sql_id = sql_id;
        this.userId = userId;
        this.title = title;
        this.remark = remark;
        this.account_name = account_name;
        this.password = password;
        this.group_id = group_id;
    }

    @Generated(hash = 1267506976)
    public AccountBean() {
    }


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

    public Long getSql_id() {
        return this.sql_id;
    }

    public void setSql_id(Long sql_id) {
        this.sql_id = sql_id;
    }

    public boolean isSql() {
        return sql_id == null || sql_id == 0 ? false : true;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}