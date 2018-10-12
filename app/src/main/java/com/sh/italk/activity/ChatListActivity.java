package com.sh.italk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.sh.italk.R;
import com.sh.italk.RecentAdapter;
import com.sh.italk.RecentContactsCallback;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{

	@BindView(R.id.tv_listMsg)
	TextView tvListMsg;
	@BindView(R.id.recycleView)
	RecyclerView recycleView;

	private RecentContactsCallback callback;
	private Boolean msgLoaded = false;
	private RecentAdapter adapter ;
	private RxPermissions rxPermissions;

	@Override
	protected int getContentView() {
		return R.layout.activity_chat_list;
	}

	@Override
	protected void initViews() {
		rxPermissions = new RxPermissions(ChatListActivity.this);
		rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
				.subscribe(new io.reactivex.functions.Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						if (!aBoolean){
							Toast.makeText(ChatListActivity.this,"为保证应用正常运行,请在权限管理开启必要权限!",Toast.LENGTH_SHORT).show();
						}
					}
				});
		initCallback();
		requestMessages(true);
		recycleView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new RecentAdapter(null);
		recycleView.setAdapter(adapter);
		adapter.setOnItemClickListener(this);
	}

	private List<RecentContact> loadedRecents;

	private void requestMessages(boolean delay) {
		if (msgLoaded) {
			return;
		}
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (msgLoaded) {
					return;
				}
				//获取最近会话列表
				NIMClient.getService(MsgService.class).queryRecentContacts()
						.setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
							@Override
							public void onResult(int code, List<RecentContact> recents, Throwable e) {
								tvListMsg.setText(recents.get(0).getContactId() + recents.get(0).getContent());
								adapter.addData(recents);
							}
						});
			}
		}, delay ? 250 : 0);
	}

	private void initCallback() {
		if (callback != null) {
			return;
		}
		callback = new RecentContactsCallback() {
			@Override
			public void onRecentContactsLoaded() {

			}

			@Override
			public void onUnreadCountChange(int unreadCount) {

			}

			@Override
			public void onItemClick(RecentContact recent) {
				tvListMsg.setText(recent.getContactId());
				Log.e("recent", recent.getContactId());
			}

			@Override
			public String getDigestOfAttachment(RecentContact recent, MsgAttachment attachment) {
				return null;
			}

			@Override
			public String getDigestOfTipMsg(RecentContact recent) {
				return null;
			}
		};
	}

	@OnClick({R.id.item_list, R.id.item_list_n})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.item_list:
				startActivity(new Intent(ChatListActivity.this, SendMsgActivity.class).putExtra("account", "15067457982"));
				break;
			case R.id.item_list_n:
				startActivity(new Intent(ChatListActivity.this, SendMsgActivity.class).putExtra("account", "15067457981"));
				break;
		}
	}

	@Override
	public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
		RecentContact recentContact = (RecentContact)adapter.getData().get(position);
		startActivity(new Intent(ChatListActivity.this, SendMsgActivity.class).putExtra("account", recentContact.getContactId()));
	}
}
