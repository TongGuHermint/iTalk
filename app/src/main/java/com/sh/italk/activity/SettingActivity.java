package com.sh.italk.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.sh.italk.R;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.internal.operators.flowable.FlowableTakeLastOne;

public class SettingActivity extends BaseActivity {


	@BindView(R.id.swichBtn)
	SwitchButton swichBtn;

	@Override
	protected int getContentView() {
		return R.layout.activity_setting;
	}

	@Override
	protected void initViews() {
		swichBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				if (isChecked){
					Toast.makeText(SettingActivity.this,"open", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(SettingActivity.this,"close", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
