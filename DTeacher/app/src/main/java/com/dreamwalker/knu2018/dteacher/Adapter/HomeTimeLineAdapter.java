package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.Model.Global;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;

/**
 * Created by KNU2017 on 2018-02-11.
 */

class HomeViewHolder extends RecyclerView.ViewHolder{
    TextView valueTypeTv;
    TextView valueTv;
    TextView timeTv;
    ImageView imageView;
    public HomeViewHolder(View itemView) {
        super(itemView);
        valueTypeTv = itemView.findViewById(R.id.valueTypeTextView);
        valueTv = itemView.findViewById(R.id.valueTextView);
        timeTv = itemView.findViewById(R.id.timeTextView);
        imageView = itemView.findViewById(R.id.homeImageView);
    }
}

public class HomeTimeLineAdapter  extends RecyclerView.Adapter<HomeViewHolder>{
    Context context;
    ArrayList<Global> bloodSugarArrayList;

    public HomeTimeLineAdapter(Context context, ArrayList<Global> bloodSugarArrayList) {
        this.context = context;
        this.bloodSugarArrayList = bloodSugarArrayList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_home_card,parent,false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(itemView);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

        if (bloodSugarArrayList.get(position).getHead().equals("0")){
            String value = "혈당 값 :" + bloodSugarArrayList.get(position).getValue();
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.good_bloodsugar));
            holder.valueTypeTv.setText(bloodSugarArrayList.get(position).getType());
            holder.valueTv.setText(value);
            holder.timeTv.setText(bloodSugarArrayList.get(position).getTime());
        }else if (bloodSugarArrayList.get(position).getHead().equals("1")){
            String value = "칼로리 :" + bloodSugarArrayList.get(position).getValue();
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.good_fitness));
            holder.valueTypeTv.setText(bloodSugarArrayList.get(position).getType());
            holder.valueTv.setText(value);
            holder.timeTv.setText(bloodSugarArrayList.get(position).getTime());
        }else if (bloodSugarArrayList.get(position).getHead().equals("2")){
            // TODO: 2018-02-11 식사 처리 추가해야해요 여기에
        }else if (bloodSugarArrayList.get(position).getHead().equals("3")){
            String value = "투약단위 :" + bloodSugarArrayList.get(position).getValue();
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.fix_syringe));
            holder.valueTypeTv.setText(bloodSugarArrayList.get(position).getType());
            holder.valueTv.setText(value);
            holder.timeTv.setText(bloodSugarArrayList.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        return bloodSugarArrayList.size();
    }
}
