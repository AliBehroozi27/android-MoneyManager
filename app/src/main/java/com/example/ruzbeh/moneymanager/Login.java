package com.example.ruzbeh.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;


public class Login extends Fragment implements Serializable {
    private static View mainView;
    private String title;
    private int pageNum;
    UserManager user;
    TextInputEditText loginName, loginPassword;
    Button buttonLogin;
    Intent intent;

    public static Login newInstance(String title, int pageNum, View mainview) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(title, title);
        args.putInt("pageNum", pageNum);
        mainView = mainview;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(title);
            pageNum = getArguments().getInt("pageNum");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        loginName = view.findViewById(R.id.editTextLoginName);
        loginPassword = view.findViewById(R.id.editTextLoginPassword);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginName.getText().toString().equals("") || loginPassword.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "please fill the blanks ", Toast.LENGTH_SHORT).show();
                } else {
                    user = new UserManager(getContext(), loginName.getText().toString(), loginPassword.getText().toString());
                    if (!user.checkUser()) {
                        new FirstPage().setNextPage(mainView);
                    } else {
                        intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("snackBar", "");
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
