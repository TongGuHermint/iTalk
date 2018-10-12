package com.sh.italk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());
		ButterKnife.bind(this);
		initViews();
	}
	protected abstract int getContentView();
	protected abstract void initViews();
}
