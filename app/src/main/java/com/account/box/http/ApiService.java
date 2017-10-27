package com.account.box.http;

import com.account.box.bean.AccountBean;
import com.account.box.bean.GroupBean;
import com.account.box.bean.MessageBean;
import com.account.box.bean.RxResult;
import com.account.box.bean.UserBean;
import com.jjs.base.http.JJsApiService;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
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
    public interface Test {
        //receipt-data
        @POST("verifyReceipt")
        Observable<RxResult<UserBean>> appleTest(@Body RequestBody requestBody);
    }

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
        Observable<RxResult<String>> addAccountToGroup(@Field("userId") String userId, @Field("groupId") String groupId, @Field("title") String title, @Field("accountName") String accountName, @Field("password") String password, @Field("remark") String remark);

        /**
         * 更新账号
         */
        @POST("account/updateAccount")
        @FormUrlEncoded
        Observable<RxResult<AccountBean>> updateAccount(@Field("accountId") String accountId, @Field("groupId") String groupId, @Field("title") String title, @Field("accountName") String accountName, @Field("password") String password, @Field("remark") String remark);

        /**
         * 获取用户下所有账号
         */
        @POST("group/getAllGroupDetailByUserId")
        @FormUrlEncoded
        Observable<RxResult<List<GroupBean>>> getGroupListAll(@Field("userId") String userId);

        /**
         * 删除账号
         */
        @POST("account/deleteAccount")
        @FormUrlEncoded
        Observable<RxResult<String>> deleteAccount(@Field("userId") String userId, @Field("accountId") String accountId);

        /**
         * 删除组
         */
        @POST("group/deleteGroupByCreator")
        @FormUrlEncoded
        Observable<RxResult<String>> deleteGroup(@Field("userId") String userId, @Field("groupId") String groupId);

        /**
         * 退出组
         */
        @POST("group/exitGroup")
        @FormUrlEncoded
        Observable<RxResult<String>> exitGroup(@Field("userId") String userId, @Field("groupId") String groupId);
    }

    public interface User {
        /**
         * 添加用户头像
         */
        @Multipart
        @POST("user/uploadUserHead")
        Observable<RxResult<String>> uploadUserHead2(@Part List<MultipartBody.Part> partList);

        /**
         * 修改密码
         */
        @POST("user/changePassword")
        @FormUrlEncoded
        Observable<RxResult<String>> changePassword(@Field("account") String account, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);
    }

    public interface Message {
        /**
         * 获取所有消息
         */
        @POST("message/getAllMessages")
        @FormUrlEncoded
        Observable<RxResult<List<MessageBean>>> getAllMessages(@Field("userId") String userId);

        /**
         * 获取消息：发送出去的
         */
        @POST("message/getSendMessages")
        @FormUrlEncoded
        Observable<RxResult<List<MessageBean>>> getSendMessages(@Field("userId") String userId);

        /**
         * 获取消息：接收到的
         */
        @POST("message/getReceiveMessages")
        @FormUrlEncoded
        Observable<RxResult<List<MessageBean>>> getReceiveMessages(@Field("userId") String userId);

        /**
         * 发送分组共享邀请
         */
        @POST("message/inviteUserJoinGroup")
        @FormUrlEncoded
        Observable<RxResult<String>> inviteUserJoinGroup(@Field("sendUserId") String sendUserId, @Field("receiveUserName") String receiveUserName, @Field("groupId") String groupId);

        /**
         * 同意邀请
         */
        @POST("message/acceptInvite")
        @FormUrlEncoded
        Observable<RxResult<String>> acceptInvite(@Field("userId") String userId, @Field("messageId") String messageId);

        /**
         * 拒绝邀请
         */
        @POST("message/rejectInvite")
        @FormUrlEncoded
        Observable<RxResult<String>> rejectInvite(@Field("userId") String userId, @Field("messageId") String messageId);

        /**
         * 改变消息状态
         */
        @POST("message/changeMsgState")
        @FormUrlEncoded
        Observable<RxResult<String>> changeMsgState(@Field("userId") String userId, @Field("messageId") String messageId);
    }
}
