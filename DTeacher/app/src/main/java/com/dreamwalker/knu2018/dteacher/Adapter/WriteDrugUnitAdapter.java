package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent;
import com.dreamwalker.knu2018.dteacher.Utils.DrugWriteEvent;
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by KNU2017 on 2018-02-09.
 */
class MyViewHoler extends RecyclerView.ViewHolder {
    TextView drugNameTextView;
    StepperTouch stepperTouch;

    public MyViewHoler(View itemView) {
        super(itemView);
        drugNameTextView = (TextView) itemView.findViewById(R.id.drugNameTextView);
        stepperTouch = (StepperTouch) itemView.findViewById(R.id.stepperTouch);
    }
}

public class WriteDrugUnitAdapter extends RecyclerView.Adapter<MyViewHoler> {
    ArrayList<String> arrayList;
    Context context;
    EventBus bus = EventBus.getDefault();
    public WriteDrugUnitAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_drug_unit_card, parent, false);
        MyViewHoler myViewHoler = new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(final MyViewHoler holder, final int position) {
        holder.drugNameTextView.setText(arrayList.get(position));

        holder.stepperTouch.stepper.setValue(1);
        holder.stepperTouch.stepper.setMin(1);
        holder.stepperTouch.stepper.setMax(30);
        holder.stepperTouch.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int i, boolean b) {
                bus.post(new DrugWriteEvent(i,arrayList.get(position), position));
                Log.e("onBindViewHolder", "onStep: " + i  + arrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
