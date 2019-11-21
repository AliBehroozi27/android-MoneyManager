package com.example.ruzbeh.moneymanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.io.Serializable;

public class LoginPagerAdapter extends FragmentPagerAdapter implements Serializable{
    private int PAGE_NUMS = 2;
    View view;


    public LoginPagerAdapter(FragmentManager fm,View view) {
        super(fm);
        this.view = view;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Login.newInstance("LOGIN", 1 ,view);
            case 1:
                return Create.newInstance("CREATE ACCOUNT ", 2 ,view);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "LOGIN";
        } else {
            return "CREATE ACCOUNT ";
        }
    }
}