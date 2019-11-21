package com.example.ruzbeh.moneymanager;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Form extends Fragment implements Serializable {
    EditText name, itemName, price, itemPrice, itemDate, describe, itemDescribe, itemId;
    Spinner kind;
    DatePicker date;
    ImageView itemKind;
    Button done;
    String gen;
    Integer id;
    Calendar myCalendar;
    static Record editRecordItem;

    public static Form newInstance(String gen, Record record, Integer itemId) {
        Form fragment = new Form();
        Bundle args = new Bundle();
        args.putSerializable("record", record);
        args.putString("gen", gen);
        args.putInt("id", itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_form, container, false);
        name = view.findViewById(R.id.editTextFormName);
        price = view.findViewById(R.id.editTextFormPrice);
        date = view.findViewById(R.id.datePicker);
        describe = view.findViewById(R.id.editTextFormDescribe);
        done = view.findViewById(R.id.buttonDone);
        kind = view.findViewById(R.id.spinnerFormKind);

        if (getArguments() != null) {
            editRecordItem = (Record) getArguments().getSerializable("record");
            gen = getArguments().getString("gen");
            id = getArguments().getInt("id");
        }
        if (gen.equals("edit") && editRecordItem != null) {
            name.setText(editRecordItem.name);
            price.setText(editRecordItem.price);
            //date.setText(editRecordItem.date);
            describe.setText(editRecordItem.describe);
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordsManager userManager = new RecordsManager(getContext());
                if (gen.equals("add")) {
                    userManager.addRecord(new Record(name.getText().toString(), price.getText().toString()
                            , date.getYear() + "/" + date.getMonth() + "/" + date.getDayOfMonth(), kind.getSelectedItem().toString(), describe.getText().toString()));
                    ((MainActivity) getActivity()).callMainActivity("add");
                } else if (editRecordItem != null) {
                    userManager.editRecord(id, new Record(name.getText().toString(), price.getText().toString()
                            , date.getYear() + "/" + date.getMonth() + "/" + date.getDayOfMonth(), kind.getSelectedItem().toString(), describe.getText().toString()));
                    ((MainActivity) getActivity()).callMainActivity("edit");
                }

            }
        });

        return view;
    }

}
