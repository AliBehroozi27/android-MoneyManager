package com.example.ruzbeh.moneymanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.AlphabeticIndex;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Record> records;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    RecordsManager recordManager;
    FragmentManager fragmentManager;
    FloatingActionButton fabAdd;
    FrameLayout frameLayout;
    Boolean firstPageCheck = false;
    Bundle bundle;
    TextView name, price, date, describe, id;
    ImageView kind, more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.ma_frame_container);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        assert navigation != null;
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_records:

                        //return true;
                    case R.id.navigation_search:
                        //Search search = Search.newInstance();
                        Log.e("HERE", "GoingToSerach");
                        Search f = new Search();
                        frameLayout.removeAllViews();
                        getSupportFragmentManager().beginTransaction()
                                .replace(frameLayout.getId(), f, null)
                                .addToBackStack(null)
                                .commit();
                        //return true;
                }
                return true;
            }
        });


        //show and save records
        setRecyclerView();

        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragmentForm(frameLayout, "add", null, 0);
            }
        });

        bundle = getIntent().getExtras();
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.mmanager);

        if (bundle == null) {
            callFragmentFirstPage(frameLayout);
            getSupportActionBar().hide();
        } else if (bundle.getString("snackBar").equals("edit")) {
            //Snackbar.make(frameLayout, "record edited", Snackbar.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(frameLayout, "record edited", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null);
            //snackbar.setActionTextColor(Color.BLACK);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.rgb(233,76,136));
            snackbar.show();
        } else if (bundle.getString("snackBar").equals("add")) {
            //Snackbar.make(frameLayout, "record added", Snackbar.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(frameLayout, "record added", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null);
            //snackbar.setActionTextColor(Color.BLACK);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.rgb(233,76,136));
            snackbar.show();
        } else if (bundle.getString("snackBar").equals("")) {
        }
    }

    private void setRecyclerView() {
        recordManager = new RecordsManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecords);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(this, recordManager.getRecords(), frameLayout);
        records = recordManager.getRecords();
        LayoutAnimationController controller;
        controller = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_anim);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "sorry ,no setting sets", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_account:
                builder.setMessage("radio buttons of accounts");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "password input", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;

            case R.id.about_me:
                builder.setMessage("testing app \n hope useful \n email : ali.behroozi@gmail.com");
                builder.show();
                return true;

            case R.id.exit:
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callFragmentFirstPage(frameLayout);
                        getActionBar().hide();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void adapterUpdate(View itemView, String change, FrameLayout container, List<Record> records) {
        RecordsManager recordsManager = new RecordsManager(getApplicationContext());
        TextView id = itemView.findViewById(R.id.textViewListId);
        if (change.equals("delete")) {
            recordsManager.removeRecord(Integer.parseInt(id.getText().toString()));
            setChange(container, records);
        } else {
            callFragmentForm(frameLayout, "edit", itemView, Integer.parseInt(id.getText().toString()));
        }

    }

    public void callFragmentForm(FrameLayout frameLayout, String gen, View itemView, Integer itemId) {
        Form formFragment;
        if (itemView != null) {
            id = itemView.findViewById(R.id.textViewListId);
            name = itemView.findViewById(R.id.textViewListName);
            price = itemView.findViewById(R.id.textViewListPrice);
            describe = itemView.findViewById(R.id.textViewListDescribe);
            date = itemView.findViewById(R.id.textViewListDate);
            kind = itemView.findViewById(R.id.imageViewKind);
            more = itemView.findViewById(R.id.imageViewMore);
            formFragment = Form.newInstance(gen, new Record(name.getText().toString(), price.getText().toString(), date.getText().toString(), "buy", describe.getText().toString()), itemId);
        } else {
            formFragment = Form.newInstance(gen, new Record(null, null, null, null, null), 0);
        }
        frameLayout.removeAllViews();
        getSupportFragmentManager().beginTransaction()
                .add(frameLayout.getId(), formFragment, null)
                .addToBackStack(null)
                .commit();
    }

    public void setChange(FrameLayout container, List<Record> records) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecords);
        if (recyclerView == null) {
            recyclerView = container.findViewById(R.id.recyclerViewRecordsSearch);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewAdapter = new RecyclerViewAdapter(this, records, container);
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_anim);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.scheduleLayoutAnimation();
            recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            setRecyclerView();
        }
        //Snackbar.make(frameLayout, "record deleted", Snackbar.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(frameLayout, "record deleted", Snackbar.LENGTH_SHORT)
                .setAction("Action", null);
        //snackbar.setActionTextColor(Color.BLACK);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.rgb(233,76,136));
        snackbar.show();
    }


    public void callFragmentFirstPage(FrameLayout frameLayout) {
        FirstPage firstPageFragment = new FirstPage();
        frameLayout.removeAllViews();
        getSupportFragmentManager().beginTransaction()
                .replace(frameLayout.getId(), firstPageFragment, null)
                .addToBackStack(null)
                .commit();
        firstPageCheck = true;
    }

    public void callMainActivity(String gen) {
        Intent intent = new Intent(this, MainActivity.class);
        if (gen.equals("edit")) {
            intent.putExtra("snackBar", "edit");
        } else {
            intent.putExtra("snackBar", "add");
        }
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("snackBar", "edit");
//        startActivity(intent);
//    }
//
//    public void openFragment(Fragment fragment, FrameLayout frameLayout) {
//        frameLayout.removeAllViews();
//        getSupportFragmentManager().beginTransaction()
//                .replace(frameLayout.getId(), fragment)
//                .addToBackStack(null)
//                .commit();
//    }
}
