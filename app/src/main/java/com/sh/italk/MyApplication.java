package com.sh.italk;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.io.IOException;

/**
 * author： TongGuHermit
 * created on： 2018/10/8 15:52
 */

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
		NIMClient.init(this, loginInfo(), options());

			// 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
			initUiKit();

		OkGo.getInstance().init(this);

	}

	private void initUiKit() {
		// 初始化
//		NimUIKit.init(this);
	}

	private SDKOptions options() {
		SDKOptions options = new SDKOptions();
		// 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
		options.userInfoProvider = new UserInfoProvider() {
			@Override
			public UserInfo getUserInfo(String account) {
				return null;
			}

			@Override
			public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
				return null;
			}

			@Override
			public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
				return null;
			}

		};
		return options;
	}

	private LoginInfo loginInfo() {
		return null;
	}
}
