package com.sh.italk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.sh.italk.Model.RegisterDTO;
import com.sh.italk.Model.UpdateTokenDTO;
import com.sh.italk.R;
import com.sh.italk.request.Api;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sh.italk.constants.RequestWhatI.REQUEST_WHAT_UPDATETOKEN;
import static com.sh.italk.constants.RequestWhatI.REQUEST_WHAT_USERREGIST;

public class LoginActivity extends BaseActivity {

	@BindView(R.id.ed_login_num)
	EditText edLoginNum;
	private Api mApi;
	private Gson mGson;
	@Override
	protected int getContentView() {
		return R.layout.activity_login;
	}

	@Override
	protected void initViews() {
		mApi = new Api(handler,LoginActivity.this);
		mGson = new Gson();
	}

	private void updateToken() {
		mApi.updateToken(REQUEST_WHAT_UPDATETOKEN,edLoginNum.getText().toString());
	}

	public void doLogin(String token) {
		LoginInfo info = new LoginInfo(edLoginNum.getText().toString(), token); // config...
		RequestCallback<LoginInfo> callback =
				new RequestCallback<LoginInfo>() {
					@Override
					public void onSuccess(LoginInfo param) {
						Toast.makeText(LoginActivity.this,param.getAccount()+"success",Toast.LENGTH_SHORT).show();
						startActivity(new Intent(LoginActivity.this,ChatListActivity.class));

					}

					@Override
					public void onFailed(int code) {
						Toast.makeText(LoginActivity.this,String.valueOf(code),Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onException(Throwable exception) {

					}
					// 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
				};

		NIMClient.getService(AuthService.class).login(info)
				.setCallback(callback);
	}

	@OnClick({R.id.btn_login, R.id.tv_register})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btn_login:
				updateToken();
				break;
			case R.id.tv_register:
				startActivity(new Intent(LoginActivity.this,MainActivity.class));
				break;
		}
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 != -1) {
				switch (msg.what){
					case REQUEST_WHAT_UPDATETOKEN:
						UpdateTokenDTO updateTokenDTO = mGson.fromJson(msg.obj.toString(),UpdateTokenDTO.class);
						if (updateTokenDTO.getCode().equals("200")){
							doLogin(updateTokenDTO.getInfo().getToken());
						}
						break;
				}
			}
		}
	};
}
