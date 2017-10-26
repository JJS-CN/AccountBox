package com.account.box.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 说明：
 * Created by aa on 2017/10/14.
 */

public class MessageBean {
    /**
     * id : 1
     * type : 1
     * send_user_id : 1
     * receive_user_id : 2
     * content : 你好！邀请你加入群组"默认分组"
     * group_id : 1
     * state : 0
     * time : 1507715281
     */

    private String id;
    private String type;//邀请状态  1邀请加入  2同意  3不同意
    private String send_user_id;//发送人id
    private String receive_user_id;//接收人id
    private String content;//邀请说明
    private String group_id;//邀请分组id
    private String state;//是否已读，0未读  1已读
    private String time;//时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public boolean isRead() {
        //判断这条消息是否已经读取：0未读 1已读
        return getState().equals("0") ? false : true;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSend_user_id() {
        return send_user_id;
    }

    public void setSend_user_id(String send_user_id) {
        this.send_user_id = send_user_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public String getTimeStr() {
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss a");
        return format.format(date);
    }

    public void setTime(String time) {
        this.time = time;
    }
}
