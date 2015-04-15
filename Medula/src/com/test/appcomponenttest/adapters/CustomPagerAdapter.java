package com.test.appcomponenttest.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.appcomponenttest.HomeFragment;
import com.test.appcomponenttest.HostFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
	Context mContext;
	ArrayList<Fragment> fragment_list = new ArrayList<Fragment>();
	final int NUM_FRAGMENTS = 2;

	public CustomPagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		mContext = ctx;
		if (fragment_list.isEmpty()) {
			fragment_list.add(new HomeFragment());
			fragment_list.add(new HostFragment());
		}
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragment_list.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUM_FRAGMENTS;
	}

}
