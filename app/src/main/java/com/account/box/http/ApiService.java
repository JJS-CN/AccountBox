package com.account.box.http;

import com.account.box.bean.AccountBean;
import com.account.box.bean.RxResult;
import com.account.box.bean.UserBean;
import com.jjs.base.http.JJsApiService;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public class ApiService extends JJsApiService {
    public interface Login {
        /**
         * 登陆
         */
        @POST("user/login")
        @FormUrlEncoded
        Observable<RxResult<UserBean>> login(@Field("account") String account, @Field("password") String pwd, @Field("imei") String imei);

        /**
         * 注册
         */
        @POST("user/register")
        @FormUrlEncoded
        Observable<RxResult<String>> register(@Field("account") String account, @Field("password") String pwd);

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

    public interface Account {
        /**
         * 新建分组
         */
        @POST("group/createGroup")
        @FormUrlEncoded
        Observable<RxResult<String>> createGroup(@Field("userId") String userId, @Field("groupName") String groupName);

        /**
         * 新建账号
         */
        @POST("account/addAccountToGroup")
        @FormUrlEncoded
        Observable<RxResult<String>> addAccountToGroup(@Field("groupId") String groupId, @Field("title") String title, @Field("accountName") String accountName, @Field("password") String password, @Field("remark") String remark);

        /**
         * 更新账号
         */
        @POST("account/updateAccount")
        @FormUrlEncoded
        Observable<RxResult<AccountBean>> updateAccount(@Field("accountId") String accountId, @Field("groupId") String groupId, @Field("title") String title, @Field("accountName") String accountName, @Field("password") String password, @Field("remark") String remark);


    }

    public interface User {
        /**
         * 添加用户头像
         */
        @Multipart
        @POST("user/uploadUserHead")
        Observable<RxResult<String>> uploadUserHead2(@Part List<MultipartBody.Part> partList);
    }
}
