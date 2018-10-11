package com.sh.italk.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sh.italk.R;

public class ChatListActivity extends AppCompatActivity {

	private LinearLayout list;
	private LinearLayout list1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_list);
		list = (LinearLayout)findViewById(R.id.item_list);
		list1 = (LinearLayout)findViewById(R.id.item_list_n);
		list.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ChatListActivity.this,SendMsgActivity.class).putExtra("account","15067457982"));
			}
		});
		list1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ChatListActivity.this,SendMsgActivity.class).putExtra("account","15067457981"));
			}
		});

	}
}
