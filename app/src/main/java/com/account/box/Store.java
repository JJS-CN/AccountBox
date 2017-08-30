package com.account.box;


import com.jjs.base.JJsStore;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public class Store extends JJsStore {
    public static class Login {
        public static String isLogin = "isLogin";
        public static int openRegisterCode = 1;
        public static int openResetCode = 2;
    }
}
