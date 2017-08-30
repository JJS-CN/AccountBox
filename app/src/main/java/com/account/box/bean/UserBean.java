package com.account.box.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：
 * Created by Administrator on 2017/8/26.
 */
@Entity
public class UserBean {
    @Id
    private Long id;//主键
    private String username;//用户名
    private String password;//密码
    private String rsaPublicKey;//公钥
    private String rsaPrivateKey;//私钥




    @Generated(hash = 1501062659)
    public UserBean(Long id, String username, String password, String rsaPublicKey,
            String rsaPrivateKey) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRsaPublicKey() {
        return this.rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return this.rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }


}
