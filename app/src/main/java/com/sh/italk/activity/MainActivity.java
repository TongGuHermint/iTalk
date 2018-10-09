package com.sh.italk.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.sh.italk.R;
import com.sh.italk.request.Api;
import com.sh.italk.Model.RegisterDTO;
import com.sh.italk.Model.UpdateTokenDTO;
import com.tbruyelle.rxpermissions2.RxPermissions;

import static com.sh.italk.constants.RequestWhatI.REQUEST_WHAT_UPDATETOKEN;
import static com.sh.italk.constants.RequestWhatI.REQUEST_WHAT_USERREGIST;

public class MainActivity extends AppCompatActivity {
	private EditText edUserName;
	private EditText edUserPwd;
	private Button btnLogin;
	private Button btnReg;
	private RxPermissions rxPermissions;
	private Api mApi;
	private Gson mGson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mApi = new Api(handler,MainActivity.this);
		mGson = new Gson();
		rxPermissions = new RxPermissions(MainActivity.this);
		rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
				.subscribe(new io.reactivex.functions.Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						if (!aBoolean){
							Toast.makeText(MainActivity.this,"为保证应用正常运行,请在权限管理开启必要权限!",Toast.LENGTH_SHORT).show();
						}
					}
				});
		edUserName = (EditText)findViewById(R.id.userName);
		edUserPwd = (EditText)findViewById(R.id.userPassword);
		btnLogin = (Button)findViewById(R.id.btn_Login);
		btnReg = (Button)findViewById(R.id.btn_Register);
		btnReg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doRegister();
			}
		});
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateToken();
			}
		});

	}

	private void updateToken() {
		mApi.updateToken(REQUEST_WHAT_UPDATETOKEN,edUserName.getText().toString());
	}

	private void doRegister() {
		mApi.userRegist(REQUEST_WHAT_USERREGIST,edUserName.getText().toString());
	}

	public void doLogin(String token) {
		LoginInfo info = new LoginInfo(edUserName.getText().toString(), token); // config...
		RequestCallback<LoginInfo> callback =
				new RequestCallback<LoginInfo>() {
					@Override
					public void onSuccess(LoginInfo param) {
						Toast.makeText(MainActivity.this,param.getAccount()+"success",Toast.LENGTH_SHORT).show();
						startActivity(new Intent(MainActivity.this,SendMsgActivity.class));
					}

					@Override
					public void onFailed(int code) {
						Toast.makeText(MainActivity.this,String.valueOf(code),Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onException(Throwable exception) {

					}
					// 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
				};

				NIMClient.getService(AuthService.class).login(info)
				.setCallback(callback);
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 != -1) {
				switch (msg.what){
					case REQUEST_WHAT_USERREGIST:
						RegisterDTO registerDTO = mGson.fromJson(msg.obj.toString(),RegisterDTO.class);
						if (registerDTO.getCode().equals("414")){
							Toast.makeText(MainActivity.this,registerDTO.getDesc(),Toast.LENGTH_SHORT).show();
						}else if (registerDTO.getCode().equals("200")){
							Toast.makeText(MainActivity.this,"register success",Toast.LENGTH_SHORT).show();
						}
						break;
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
