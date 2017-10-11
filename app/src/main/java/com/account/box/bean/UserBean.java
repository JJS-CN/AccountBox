package com.account.box.bean;

import java.util.List;

/**
 * 说明：
 * Created by Administrator on 2017/8/26.
 */
public class UserBean {
    private User user;//用户
    private List<GroupBean> groups ;//分组

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GroupBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBean> groups) {
        this.groups = groups;
    }


    public class User {
        private String id;//主键
        private String account;//用户名
        private String password;//登录密码
        private String last_login_time;//登录时间
        private String last_login_imei;//登录imei
        private String head_path;//头像

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getLast_login_imei() {
            return last_login_imei;
        }

        public void setLast_login_imei(String last_login_imei) {
            this.last_login_imei = last_login_imei;
        }

        public String getHead_path() {
            return head_path;
        }

        public void setHead_path(String head_path) {
            this.head_path = head_path;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", account='" + account + '\'' +
                    ", password='" + password + '\'' +
                    ", last_login_time='" + last_login_time + '\'' +
                    ", last_login_imei='" + last_login_imei + '\'' +
                    ", head_path='" + head_path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user=" + user +
                ", groups=" + groups +
                '}';
    }
}
