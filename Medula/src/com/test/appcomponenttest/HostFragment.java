package com.test.appcomponenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HostFragment extends Fragment{
	private FragmentTabHost mTabHost;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    mTabHost = new FragmentTabHost(getActivity());
	    View v = inflater.inflate(R.layout.fragment_host, mTabHost);
	    mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

	    mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("All"),
	            AllFragment.class, null);
	    mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Mild"),
	    		MildFragment.class, null);
	    mTabHost.addTab(mTabHost.newTabSpec("Tab3").setIndicator("Severe"),
	    		SevereFragment.class, null);
	    
	    setTabStripColor();
	    ImageView im_export = (ImageView) v.findViewById(R.id.export);
	    im_export.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new CSVConverterBGTask(getActivity()).execute();
			}
		});
	    return mTabHost;
	}
	
	private void setTabStripColor(){
		    /*for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++) {
		    	mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.line_selector);
		    }*/
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.line_selector_all);
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.line_selector_mild);
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.line_selector_severe);
	}

	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    mTabHost = null;
	}
}
