package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Model.RealTime;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by KNU2017 on 2018-02-21.
 */

class DetailListViewHolder extends RecyclerView.ViewHolder {
    TextView value, datetime;

    public DetailListViewHolder(View itemView) {
        super(itemView);
        value = (TextView)itemView.findViewById(R.id.detail_value);
        datetime = (TextView)itemView.findViewById(R.id.detail_datetime);
    }
}
public class DetailListAdapter extends RecyclerView.Adapter<DetailListViewHolder>{

    Context context;
    ArrayList<RealTime> realTimeArrayList;

    public DetailListAdapter(Context context, ArrayList<RealTime> realTimeArrayList) {
        this.context = context;
        this.realTimeArrayList = realTimeArrayList;
    }

    @Override
    public DetailListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_detail_list, parent, false);
        return new DetailListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(DetailListViewHolder holder, int position) {

        holder.value.setText(realTimeArrayList.get(position).getValue());
        holder.datetime.setText(realTimeArrayList.get(position).getDatetime());

    }

    @Override
    public int getItemCount() {
        return realTimeArrayList.size();
    }
}
