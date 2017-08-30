package com.account.box.http;

import com.account.box.bean.RxResult;
import com.account.box.bean.UserBean;
import com.jjs.base.http.JJsApiService;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public class ApiService extends JJsApiService {
    public interface Login {
        /**
         * 登陆
         */
        @POST("login")
        @FormUrlEncoded
        Observable<RxResult<UserBean>> login(@Field("phone") String phone, @Field("password") String pwd);

        /**
         * 注册
         */
        @POST("register")
        @FormUrlEncoded
        Observable<RxResult<UserBean>> register(@Field("phone") String phone, @Field("password") String pwd, @Field("code") String smsCode);

        /**
         * 修改密码
         */
        @POST("resetPwd")
        @FormUrlEncoded
        Observable<RxResult<UserBean>> resetPwd(@Field("phone") String phone, @Field("password") String pwd);

        /**
         * 发送验证码
         */
        @POST("sendSMS")
        @FormUrlEncoded
        Observable<RxResult<String>> sendSMS(@Field("phone") String phone);
    }
}
