package com.sh.italk;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * author： TongGuHermit
 * created on： 2018/10/12 14:29
 */

public class RecentAdapter extends BaseQuickAdapter<RecentContact,BaseViewHolder> {
	public RecentAdapter( @Nullable List<RecentContact> data) {
		super(R.layout.layout_item_recent, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, RecentContact item) {
		helper.setText(R.id.tv_r_phone,item.getContactId())
				.setText(R.id.tv_r_msg,item.getContent())
				.setText(R.id.tv_r_time,timeMinute(String.valueOf(item.getTime()/1000)));
	}
	public static String timeMinute(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		return times;
	}
}
