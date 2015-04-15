package com.test.appcomponenttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.test.appcomponenttest.adapters.CustomPagerAdapter;

public class MainActivity extends FragmentActivity implements
		AllFragment.EventListener, MildFragment.EventListener, SevereFragment.EventListener {

	FragmentManager fm = null;
	FragmentTransaction ft = null;
	CustomPagerAdapter myPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		myPagerAdapter = new CustomPagerAdapter(fm, this);
		ViewPager vpagers = (ViewPager) findViewById(R.id.pager);
		vpagers.setAdapter(myPagerAdapter);
	}

	public void onHandleEvent(int id) {
		Toast.makeText(this, "ListItem clicked", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("list", "all");
		startActivity(intent);
	}
	public void onHandleEventSevere(int id){
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("list", "severe");
		startActivity(intent);
	}
	public void onHandleEventMild(int id){
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("list", "mild");
		startActivity(intent);
	}
}
