package com.account.box.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：只保存账号数据，层级关系由另一张表维护
 * Created by Administrator on 2017/8/26.
 */
@Entity
public class AccountBean {
    @Id
    private Long id;
    private Long userId;

    private String accountName;
    private String accountPwd;
    private String accountMsg;
    private Long accountListId;//所属层级id
    @Generated(hash = 562823857)
    public AccountBean(Long id, Long userId, String accountName, String accountPwd,
            String accountMsg, Long accountListId) {
        this.id = id;
        this.userId = userId;
        this.accountName = accountName;
        this.accountPwd = accountPwd;
        this.accountMsg = accountMsg;
        this.accountListId = accountListId;
    }
    @Generated(hash = 1267506976)
    public AccountBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getAccountName() {
        return this.accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAccountPwd() {
        return this.accountPwd;
    }
    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }
    public String getAccountMsg() {
        return this.accountMsg;
    }
    public void setAccountMsg(String accountMsg) {
        this.accountMsg = accountMsg;
    }
    public Long getAccountListId() {
        return this.accountListId;
    }
    public void setAccountListId(Long accountListId) {
        this.accountListId = accountListId;
    }
}
