package com.sh.italk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.sh.italk.R;

import java.util.List;

public class SendMsgActivity extends AppCompatActivity {
	private EditText edMsg;
	private EditText edAccount;
	private Button btnSend;
	private TextView tvMsg;
	private Observer<List<IMMessage>> incomingMessageObserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_msg);
		tvMsg = (TextView)findViewById(R.id.tv_msg);
		incomingMessageObserver =
				new Observer<List<IMMessage>>() {
					@Override
					public void onEvent(List<IMMessage> messages) {
						// 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
						int status = messages.get(messages.size()-1).getStatus().getValue();
						String msg =  messages.get(messages.size()-1).getContent();
						Log.e("status",String.valueOf(status));
						Log.e("msg",msg);
						tvMsg.setText(msg);

					}
				};
		NIMClient.getService(MsgServiceObserve.class)
				.observeReceiveMessage(incomingMessageObserver, true);
		edMsg = (EditText)findViewById(R.id.et_msg);
		edAccount = (EditText)findViewById(R.id.et_account);
		btnSend = (Button)findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMsg();
			}
		});

	}

	private void sendMsg() {
		//单聊
		// 以单聊类型为例
		SessionTypeEnum sessionType = SessionTypeEnum.P2P;
		// 创建一个文本消息
		IMMessage textMessage = MessageBuilder.createTextMessage(edAccount.getText().toString(), sessionType, edMsg.getText().toString());


		// 发送给对方
		NIMClient.getService(MsgService.class).sendMessage(textMessage, false)/*.setCallback(callback)*/;

		// 监听消息状态变化
//		NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(statusObserver, true);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NIMClient.getService(MsgServiceObserve.class)
				.observeReceiveMessage(incomingMessageObserver, false);
	}
}
