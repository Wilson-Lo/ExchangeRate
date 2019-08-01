package com.wilson.android.exchangerate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final String TAG = "RecyclerViewAdapter";
    private Context context;
    private ArrayList<RateItem> rateHashMap;

    RecyclerViewAdapter(Context context, ArrayList<RateItem> rateHashMap) {
        this.context = context;
        this.rateHashMap = rateHashMap;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txCountryCode.setText("" + this.rateHashMap.get(position).getCountryCode());
        viewHolder.txtRate.setText("" + this.rateHashMap.get(position).getRate());
        String lowercaseCountryCode = this.rateHashMap.get(position).getCountryCode().toLowerCase();
        //try is the special word in the android, so change file name try to try_flag
        if (lowercaseCountryCode.equals("try")) {
            lowercaseCountryCode = lowercaseCountryCode + "_flag";
        }
        int checkExistence = context.getResources().getIdentifier(lowercaseCountryCode, "drawable", context.getPackageName());

        if (checkExistence != 0) {  // the resource exists...

            int resId = context.getResources().getIdentifier(lowercaseCountryCode, "drawable", context.getPackageName());

            viewHolder.imageViewFlag.setBackgroundResource(resId);
        } else {  // checkExistence == 0  // the resource does NOT exist!!
            viewHolder.imageViewFlag.setBackgroundResource(R.drawable.usd);
        }

    }

    @Override
    public int getItemCount() {
        if (this.rateHashMap != null) {
            return this.rateHashMap.size();
        } else {
            return 0;
        }
    }

    //Adapter need a ViewHolder，just do its constructor，view will stored in the itemView
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRate, txCountryCode;
        ImageView imageViewFlag;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewFlag = (ImageView) itemView.findViewById(R.id.imageView_flag);
            txtRate = (TextView) itemView.findViewById(R.id.tx_rate);
            txCountryCode = (TextView) itemView.findViewById(R.id.tx_country_code);
        }
    }
}
