package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dreamwalker.knu2018.dteacher.Model.Pattern;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.List;

/**
 * Created by KNU2017 on 2018-01-26.
 */

class WearableSyncViewHoler extends RecyclerView.ViewHolder {
    TextView subjectText, typeText;
    TextView valueTitleText, valueText;
    TextView dateText, dateTextValue;
    TextView timeText, timeTextValue;

    public WearableSyncViewHoler(View itemView) {
        super(itemView);
        subjectText = itemView.findViewById(R.id.subjectText);
        typeText = itemView.findViewById(R.id.typeText);
        valueTitleText = itemView.findViewById(R.id.valueTitleText);
        valueText = itemView.findViewById(R.id.valueText);
        dateText = itemView.findViewById(R.id.dateText);
        dateTextValue = itemView.findViewById(R.id.dateTextValue);
        timeText = itemView.findViewById(R.id.timeText);
        timeTextValue = itemView.findViewById(R.id.timeTextValue);
    }
}

public class WearableSyncAdapter extends RecyclerView.Adapter<WearableSyncViewHoler> {

    private static final String TAG = "WearableSyncAdapter";

    Context context;
    List<Pattern> patternList;

    public WearableSyncAdapter(Context context, List<Pattern> patternList) {
        this.context = context;
        this.patternList = patternList;
    }

    @Override
    public WearableSyncViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_wear_sync_data, parent, false);
        return new WearableSyncViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(WearableSyncViewHoler holder, int position) {
        String type;
        String date, time;
        String id;
        date = patternList.get(position).getYear() + "-" + patternList.get(position).getMonth() + "-" + patternList.get(position).getDay();
        time = patternList.get(position).getHour() + ":" + patternList.get(position).getMinute() + ":" + patternList.get(position).getSecond();
        type = patternList.get(position).getType();
        id = patternList.get(position).getId();

        if (id.equals("16")) {
            if (type.equals("0")) {
                holder.typeText.setText("걷기");
            } else if (type.equals("1")) {
                holder.typeText.setText("운동");
            } else {
                holder.typeText.setText("식사");
            }
        } else if (id.equals("32")) {
            holder.typeText.setText("당뇨 데이터");
        } else {

        }

        holder.valueText.setText(patternList.get(position).getValue());
        holder.dateTextValue.setText(date);
        holder.timeTextValue.setText(time);

    }

    @Override
    public int getItemCount() {
        return patternList.size();
    }
}
