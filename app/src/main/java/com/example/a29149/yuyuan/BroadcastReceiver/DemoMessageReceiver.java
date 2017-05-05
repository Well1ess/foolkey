package com.example.a29149.yuyuan.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.a29149.yuyuan.Main.MainActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * Created by MaLei on 2017/5/4.
 * Email:ml1995@mail.ustc.edu.cn
 * 为了接收消息，您需要自定义一个继承自PushMessageReceiver类的BroadcastReceiver，
 * 实现其中的onReceivePassThroughMessage，onNotificationMessageClicked，onNotificationMessageArrived，
 * onCommandResult和onReceiveRegisterResult方法，然后把该receiver注册到AndroidManifest.xml文件中。
 * onReceivePassThroughMessage用来接收服务器发送的透传消息，onNotificationMessageClicked用来接收服务器发来的通知栏消息
 * （用户点击通知栏时触发），onNotificationMessageArrived用来接收服务器发来的通知栏消息
 * （消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息），
 * onCommandResult用来接收客户端向服务器发送命令消息后返回的响应，onReceiveRegisterResult
 * 用来接受客户端向服务器发送注册命令消息后返回的响应。
 */

public class DemoMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    //用来接收服务器发送的透传消息
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        //打印消息方便测试
        System.out.println("透传消息到达了");
        System.out.println("透传消息是"+message.toString());

        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }

    //onNotificationMessageClicked用来接收服务器发来的通知栏消息（用户点击通知栏时触发）
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
       /* //在此处做逻辑处理,用户点击后跳转到指定的activity中
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), MainActivity.class);
        //百度云推送默认点击后跳转到指定页面 需加上下面代码才能跳转到指定位置
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);*/
        //打印消息方便测试
        System.out.println("用户点击了通知消息");
        System.out.println("通知消息是" + message.toString());
        System.out.println("点击后,会进入应用" );

        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }

    //用来接收服务器发来的通知栏消息（消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息），
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        //打印消息方便测试
        System.out.println("通知消息到达了");
        System.out.println("通知消息是"+message.toString());

        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }

    //用来接收客户端向服务器发送命令消息后返回的响应
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
                //打印信息便于测试注册成功与否
                System.out.println("注册成功");
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    //用来接受客户端向服务器发送注册命令消息后返回的响应
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
    }
}
