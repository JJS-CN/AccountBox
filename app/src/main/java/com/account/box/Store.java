package com.account.box;


import com.jjs.base.base.BaseStore;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public class Store extends BaseStore {
    public static class Login {
        public static String isLogin = "isLogin";
        public static int openRegisterCode = 1;
        public static int openResetCode = 2;
    }

    public static class Password {
        public static final int Private = 0;
        public static final int Protected = 1;
        public static final int Public = 2;
    }

    public static class ResultCode {
        public static final int Message = 101;
    }
}
