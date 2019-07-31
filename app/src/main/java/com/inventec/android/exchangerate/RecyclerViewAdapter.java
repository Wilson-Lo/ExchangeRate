package com.inventec.android.exchangerate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RateItem> rateHashMap;

    RecyclerViewAdapter(Context context,    ArrayList<RateItem> rateHashMap) {
        this.context = context;
        this.rateHashMap = rateHashMap;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.txCountryCode.setText(""+ this.rateHashMap.get(position).getCountryCode());
        viewHolder.txtRate.setText(""+ this.rateHashMap.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return this.rateHashMap.size();
    }

    //Adapter need a ViewHolder，just do its constructor，view will stored in the itemView
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRate, txCountryCode;

        ViewHolder(View itemView) {
            super(itemView);
            txtRate = (TextView) itemView.findViewById(R.id.tx_rate);
            txCountryCode = (TextView) itemView.findViewById(R.id.tx_country_code);
        }
    }
}
