package com.szy.news.custom;

import java.util.List;
import java.util.Map;

import com.szy.news.activity.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CustomSimpleAdapter extends SimpleAdapter {

	public CustomSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}
	//重写父类方法，来完成初始界面的渲染效果

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v= super.getView(position, convertView, parent);
		if (position==0)
		{
			TextView categoryTitle = (TextView)v;
			categoryTitle.setBackgroundResource(R.drawable.categorybar_item_background);
			categoryTitle.setTextColor(0XFFFFFFFF);
		}
		return v;
	}
	
}
