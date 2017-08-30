package com.account.box.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：
 * Created by Administrator on 2017/8/26.
 */
@Entity
public class AccountListBean {
    @Id
    private Long id;

    private Long userId;
    private Long parentId;//父级id，先通过userId且无父级查询第一级，遍历所有结果查询第二级，进行循环；再通过查询出来的list层级，去遍历账号表
    @Generated(hash = 185813543)
    public AccountListBean(Long id, Long userId, Long parentId) {
        this.id = id;
        this.userId = userId;
        this.parentId = parentId;
    }
    @Generated(hash = 597413548)
    public AccountListBean() {
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
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
