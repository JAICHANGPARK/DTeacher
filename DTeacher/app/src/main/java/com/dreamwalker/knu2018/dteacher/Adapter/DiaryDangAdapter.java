package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author JAICHANGPARK
 *         다이어리 프레그먼트에 사용될 어댑터 클래스 .
 *         Created by 2E313JCP on 2018-03-05.
 */

class DiaryDangViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_type)
    TextView textType;
    @BindView(R.id.text_value)
    TextView textValue;


    public DiaryDangViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

public class DiaryDangAdapter extends RecyclerView.Adapter<DiaryDangViewHolder> {

    Context context;
    ArrayList<BloodSugar> bloodSugarArrayList;


    public DiaryDangAdapter(Context context, ArrayList<BloodSugar> bloodSugarArrayList) {
        this.context = context;
        this.bloodSugarArrayList = bloodSugarArrayList;
    }

    @Override
    public DiaryDangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_diary_dang, parent, false);
        DiaryDangViewHolder diaryDangViewHolder = new DiaryDangViewHolder(itemView);
        return diaryDangViewHolder;
    }

    @Override
    public void onBindViewHolder(DiaryDangViewHolder holder, int position) {

        String date = bloodSugarArrayList.get(position).getBsDate();
        String time = bloodSugarArrayList.get(position).getBsTime();
        String dateTime = date + " " + time;
        String value = bloodSugarArrayList.get(position).getBsValue() + " mg/dL";
        holder.textDate.setText(dateTime);
        holder.textType.setText(bloodSugarArrayList.get(position).getBsType());
        holder.textValue.setText(value);
        // TODO: 2018-03-05 클릭 리스너 추가하기.
    }

    @Override
    public int getItemCount() {
        return bloodSugarArrayList.size();
    }
}
