package com.account.box.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * 说明：
 * Created by Administrator on 2017/8/26.
 */
@Entity
public class UserBean {
    @Id
    private Long id;//主键
    private String username;//用户名
    private String passwordPrivate;//登录密码（机密,只能自己知道）
    private String passwordProtected;//登录密码（私密，亲人可以知道）
    private String passwordPublic;//登录密码（公开，任何人可以知道）
    private byte[] rsaPublicKey;//公钥
    private byte[] rsaPrivateKey;//私钥
    private String IMEIs;//多设备绑定字段
    private boolean isSham;//是否开启入口伪装
    private int passwordErrorNum;//密码错误次数


    @ToMany(referencedJoinProperty = "userId")
    private List<GroupBean> groupList;//组列表
    //二级密码的表。用来存放多种等级密码的；
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 83707551)
    private transient UserBeanDao myDao;


    @Generated(hash = 2060066771)
    public UserBean(Long id, String username, String passwordPrivate,
            String passwordProtected, String passwordPublic, byte[] rsaPublicKey,
            byte[] rsaPrivateKey, String IMEIs, boolean isSham,
            int passwordErrorNum) {
        this.id = id;
        this.username = username;
        this.passwordPrivate = passwordPrivate;
        this.passwordProtected = passwordProtected;
        this.passwordPublic = passwordPublic;
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
        this.IMEIs = IMEIs;
        this.isSham = isSham;
        this.passwordErrorNum = passwordErrorNum;
    }


    @Generated(hash = 1203313951)
    public UserBean() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return this.username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPasswordPrivate() {
        return this.passwordPrivate;
    }


    public void setPasswordPrivate(String passwordPrivate) {
        this.passwordPrivate = passwordPrivate;
    }


    public String getPasswordProtected() {
        return this.passwordProtected;
    }


    public void setPasswordProtected(String passwordProtected) {
        this.passwordProtected = passwordProtected;
    }


    public String getPasswordPublic() {
        return this.passwordPublic;
    }


    public void setPasswordPublic(String passwordPublic) {
        this.passwordPublic = passwordPublic;
    }


    public byte[] getRsaPublicKey() {
        return this.rsaPublicKey;
    }


    public void setRsaPublicKey(byte[] rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }


    public byte[] getRsaPrivateKey() {
        return this.rsaPrivateKey;
    }


    public void setRsaPrivateKey(byte[] rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }


    public String getIMEIs() {
        return this.IMEIs;
    }


    public void setIMEIs(String IMEIs) {
        this.IMEIs = IMEIs;
    }


    public boolean getIsSham() {
        return this.isSham;
    }


    public void setIsSham(boolean isSham) {
        this.isSham = isSham;
    }


    public int getPasswordErrorNum() {
        return this.passwordErrorNum;
    }


    public void setPasswordErrorNum(int passwordErrorNum) {
        this.passwordErrorNum = passwordErrorNum;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 361423316)
    public List<GroupBean> getGroupList() {
        if (groupList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GroupBeanDao targetDao = daoSession.getGroupBeanDao();
            List<GroupBean> groupListNew = targetDao._queryUserBean_GroupList(id);
            synchronized (this) {
                if (groupList == null) {
                    groupList = groupListNew;
                }
            }
        }
        return groupList;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 114754500)
    public synchronized void resetGroupList() {
        groupList = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1491512534)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserBeanDao() : null;
    }


}
