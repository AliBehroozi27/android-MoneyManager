package com.example.ruzbeh.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;


public class Create extends Fragment implements Serializable {
    private static View mainView;
    private String title;
    private int pageNum;
    EditText createName, createPassword, createPasswordConfirm;
    Button buttonCreate;
    UserManager user;
    Intent intent;

    public static Create newInstance(String title, int pageNum, View mainview) {
        Create fragment = new Create();
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
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createName = view.findViewById(R.id.editTextCreateName);
        createPassword = view.findViewById(R.id.editTextCreatePassword);
        createPasswordConfirm = view.findViewById(R.id.editTextCreateConfirmPassword);
        buttonCreate = view.findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createName.getText().toString().equals("") || createPassword.getText().toString().equals("") || createPasswordConfirm.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "please fill the blanks ", Toast.LENGTH_SHORT).show();
                } else {
                    if (createPassword.getText().toString().equals(createPasswordConfirm.getText().toString())) {
                        user = new UserManager(getContext(), createName.getText().toString(), createPassword.getText().toString());
                        if (!user.createUser()) {
                            Toast.makeText(getContext(), "USER EXISTS", Toast.LENGTH_SHORT).show();
                            new FirstPage().setNextPage(mainView);
                        } else {
                            user.createUser();
                            intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("snackBar", "");
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getContext(), "Password not match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
