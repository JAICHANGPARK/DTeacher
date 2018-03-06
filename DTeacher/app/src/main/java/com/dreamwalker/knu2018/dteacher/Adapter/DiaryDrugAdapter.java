package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Model.Drug;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2E313JCP on 2018-03-06.
 */


class DiaryDrugViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_type)
    TextView textType;
    @BindView(R.id.text_value)
    TextView textValue;


    public DiaryDrugViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

public class DiaryDrugAdapter extends RecyclerView.Adapter<DiaryDrugViewHolder>{

    Context context;
    ArrayList<Drug> drugArrayList;

    public DiaryDrugAdapter(Context context, ArrayList<Drug> drugArrayList) {
        this.context = context;
        this.drugArrayList = drugArrayList;
    }


    @Override
    public DiaryDrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_diary_dang,parent,false);
        DiaryDrugViewHolder diaryDrugViewHolder = new DiaryDrugViewHolder(itemView);
        return diaryDrugViewHolder;
    }

    @Override
    public void onBindViewHolder(DiaryDrugViewHolder holder, int position) {

        String date = drugArrayList.get(position).getDate();
        String time = drugArrayList.get(position).getTime();
        String dateTime = date + " " + time;
        String value = drugArrayList.get(position).getValueUnit() + " 단위";

        holder.textDate.setText(dateTime);
        holder.textType.setText(drugArrayList.get(position).getDrugName());
        holder.textValue.setText(value);

    }

    @Override
    public int getItemCount() {
        return drugArrayList.size();
    }
}
