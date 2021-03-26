package com.study.Fragment.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		TextView textView = new TextView(getActivity());
		
		textView.setText("我是FragmentB");
		
		textView.setTextColor(Color.parseColor("#00ff00"));
		
		textView.setTextSize(30);
		
		return textView;
	}
}
