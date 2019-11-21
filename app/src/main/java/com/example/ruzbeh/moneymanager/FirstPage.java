package com.example.ruzbeh.moneymanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class FirstPage extends Fragment {
    FragmentPagerAdapter adapterViewPager;
    ViewPager loginVP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_page, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginVP = view.findViewById(R.id.vpPager);
        adapterViewPager = new LoginPagerAdapter(getActivity().getSupportFragmentManager(), view);
        loginVP.setCurrentItem(2);
        loginVP.setAdapter(adapterViewPager);
    }

    public void setNextPage(View view) {
        loginVP = view.findViewById(R.id.vpPager);
        if (loginVP.getCurrentItem() == 1) {
            loginVP.setCurrentItem(2);
        } else {
            loginVP.setCurrentItem(1);
        }
    }
}
