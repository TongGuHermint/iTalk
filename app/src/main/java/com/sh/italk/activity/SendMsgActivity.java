package com.sh.italk.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.github.library.bubbleview.BubbleTextView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SendMsgActivity extends AppCompatActivity {
	private EditText edMsg;
	private EditText edAccount;
	private Button btnSend;
	private TextView tvMsg;
	private String account;
	private LinearLayout p2pList;
	private ScrollView scrollView;
	private Observer<List<IMMessage>> incomingMessageObserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_msg);
		account = getIntent().getStringExtra("account");
		tvMsg = (TextView)findViewById(R.id.tv_msg);
		p2pList = (LinearLayout)findViewById(R.id.p_chat);
		scrollView = (ScrollView)findViewById(R.id.scrollView) ;
		incomingMessageObserver =
				new Observer<List<IMMessage>>() {
					@Override
					public void onEvent(List<IMMessage> messages) {
						// 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
						int status = messages.get(messages.size()-1).getStatus().getValue();
						String msg =  messages.get(messages.size()-1).getContent();
						long time = messages.get(messages.size()-1).getTime()/1000;
						String second = timeMinute(String.valueOf(time));
						Log.e("status",String.valueOf(status));
						Log.e("msg",msg);
						Log.e("time",second);
						addListView(msg,second);
						downScroll();
					}
				};
		NIMClient.getService(MsgServiceObserve.class)
				.observeReceiveMessage(incomingMessageObserver, true);
		edMsg = (EditText)findViewById(R.id.et_msg);
		edAccount = (EditText)findViewById(R.id.et_account);
		btnSend = (Button)findViewById(R.id.btn_send);
		edMsg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				downScroll();
			}
		});
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!edMsg.getText().toString().isEmpty()){
					sendMsg();
				}
			}
		});

	}
	public static String timeMinute(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("HH:mm:ss");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		return times;
	}


	private void addListView(String msg,String time) {
		ViewGroup addListView = (ViewGroup) LayoutInflater.from(SendMsgActivity.this).inflate(R.layout.item_chatlist,null);
		BubbleTextView tvMsg = (BubbleTextView)addListView.findViewById(R.id.tv_item_msg);
		TextView tvTime = (TextView)addListView.findViewById(R.id.tv_item_time);
		ImageView imageView = (ImageView)addListView.findViewById(R.id.img_item);
		imageView.setImageResource(R.drawable.wz);
		tvMsg.setText(msg);
		tvTime.setText(time);
		p2pList.addView(addListView);
	}
	private void addRightListView(String msg,String time) {
		ViewGroup addListView = (ViewGroup) LayoutInflater.from(SendMsgActivity.this).inflate(R.layout.item_chat_right,null);
		BubbleTextView tvMsg = (BubbleTextView)addListView.findViewById(R.id.tv_item_msg);
		TextView tvTime = (TextView)addListView.findViewById(R.id.tv_item_time);
		ImageView imageView = (ImageView)addListView.findViewById(R.id.img_item);
		imageView.setImageResource(R.drawable.wz);
		tvMsg.setText(msg);
		tvTime.setText(time);
		p2pList.addView(addListView);
	}

	private void sendMsg() {
		//单聊
		// 以单聊类型为例
		SessionTypeEnum sessionType = SessionTypeEnum.P2P;
		// 创建一个文本消息
		IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, edMsg.getText().toString());
		// 发送给对方
		NIMClient.getService(MsgService.class).sendMessage(textMessage, false)/*.setCallback(callback)*/;
		String CurTime = String.valueOf(TimeUtils.getNowMills()/1000);

		addRightListView(edMsg.getText().toString(),timeMinute(CurTime));
		cleanEd();//隐藏键盘,清空输入框,滚动到底部
	}

	private void cleanEd() {
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(btnSend.getWindowToken(), 0); //强制隐藏键盘
		edMsg.setText("");
		downScroll();

	}

	//滚动到底部
	private void downScroll() {
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NIMClient.getService(MsgServiceObserve.class)
				.observeReceiveMessage(incomingMessageObserver, false);
	}

}
