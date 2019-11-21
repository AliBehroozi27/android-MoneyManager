package com.example.ruzbeh.moneymanager;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.recordViewHolder> {
    TextView name, price, date, describe, id;
    ImageView kind;
    ImageButton more;
    Context context;
    List<Record> records;
    FrameLayout container;

    public RecyclerViewAdapter(Context context, List<Record> records , FrameLayout container) {
        this.context = context;
        this.records = records;
        this.container = container;
    }

    @Override
    public recordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.record_card_view, null, false);
        return new recordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(recordViewHolder holder, int position) {
        Record record = records.get(position);
        holder.id.setText(String.valueOf(record.id));
        holder.name.setText(record.name);
        holder.price.setText(record.price);
        holder.date.setText(record.date);
        holder.describe.setText(record.describe);
        switch (record.kind) {
            case "buy":
                holder.kind.setImageDrawable(context.getResources().getDrawable(R.drawable.buy));
                break;
            case "borrow":
                holder.kind.setImageDrawable(context.getResources().getDrawable(R.drawable.borrow));
                break;
            case "lend":
                holder.kind.setImageDrawable(context.getResources().getDrawable(R.drawable.lend));
                break;
        }
        holder.more.setImageDrawable(context.getResources().getDrawable(R.drawable.more));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class recordViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, describe, id;
        ImageView kind, more;

        public recordViewHolder(final View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textViewListId);
            name = itemView.findViewById(R.id.textViewListName);
            price = itemView.findViewById(R.id.textViewListPrice);
            describe = itemView.findViewById(R.id.textViewListDescribe);
            date = itemView.findViewById(R.id.textViewListDate);
            kind = itemView.findViewById(R.id.imageViewKind);
            more = itemView.findViewById(R.id.imageViewMore);
            more.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
                    final View view = inflater.inflate(R.layout.more, null, false);
                    final AlertDialog.Builder alertDialogMore = new AlertDialog.Builder(context);
                    alertDialogMore.setView(view);
                    alertDialogMore.setCancelable(true);
                    final AlertDialog more = alertDialogMore.create();
                    final ImageView delete = view.findViewById(R.id.imageViewDelete);
                    final ImageView edit = view.findViewById(R.id.imageViewEdit);

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) context).adapterUpdate(itemView, "delete" , container ,records);
                            more.dismiss();
                            more.cancel();
                        }
                    });
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) context).adapterUpdate(itemView, "edit" ,container, records);
                            more.dismiss();
                            more.cancel();
                        }
                    });
                    more.show();
                }
            });

            describe.setClickable(true);
            describe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(describe.getText().toString());
                    builder.show();
                }
            });
        }
    }
}