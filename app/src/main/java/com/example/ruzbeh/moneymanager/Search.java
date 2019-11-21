package com.example.ruzbeh.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;


public class Search extends Fragment {
    List<Record> records = null;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    ActionBar toolbar;
    RecordsManager recordManager;
    FragmentManager fragmentManager;
    FloatingActionButton fabAdd;
    FrameLayout frameLayout, totalsContainer;
    Boolean firstPageCheck = false;
    Bundle bundle;
    TextView name, price, date, describe, id;
    ImageView kind, more;
    EditText searchResult;
    Spinner searchTitle;
    Button buttonSearch;

    public static Search newInstance() {
        Log.d("HERE" , "search");
        Search fragment = new Search();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("HERE", "Serach");

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        frameLayout = view.findViewById(R.id.search_fl);
        //toolbar = ((MainActivity) getActivity()).getSupportActionBar();
        totalsContainer = view.findViewById(R.id.totals_container);
        BottomNavigationView navigation = view.findViewById(R.id.navigationSearch);
        assert navigation != null;
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_records:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("snackBar", "");
                        startActivity(intent);
                        return true;
                    case R.id.navigation_search:
                        return true;
                }
                return false;
            }
        });

        searchResult = view.findViewById(R.id.editTextSearch);
        //searchTitle = view.findViewById(R.id.spinnerSearch);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        recyclerView = view.findViewById(R.id.recyclerViewRecordsSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(buttonSearch.getContext(),R.anim.button_search);
                buttonSearch.startAnimation(animation);
                RecordsManager recordsManager = new RecordsManager(getActivity());
                records = recordsManager.searchData("", searchResult.getText().toString());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), records, frameLayout);
                LayoutAnimationController controller =  AnimationUtils.loadLayoutAnimation(recyclerView.getContext(),R.anim.layout_anim);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                setTotals(totalsContainer, records);
            }
        });
        buttonSearch.performClick();
        return view;
    }

    public void setTotals(FrameLayout totalsContainer, List<Record> records) {
        Integer lends = 0, borrows = 0, buys = 0;
        TextView lendTotal, borrowTotal, buyTotal;
        lendTotal = totalsContainer.findViewById(R.id.searchLendTotal);
        borrowTotal = totalsContainer.findViewById(R.id.searchBorrowTotal);
        buyTotal = totalsContainer.findViewById(R.id.searchBuyTotal);
        for (int i = 0; i < records.size(); i++) {
            switch (records.get(i).kind) {
                case "lend":
                    lends += Integer.parseInt(records.get(i).price);
                    break;
                case "buy":
                    buys += Integer.parseInt(records.get(i).price);
                    break;
                case "borrow":
                    borrows += Integer.parseInt(records.get(i).price);
                    break;
            }
        }
        lendTotal.setText(lends.toString());
        borrowTotal.setText(borrows.toString());
        buyTotal.setText(buys.toString());
    }
}
