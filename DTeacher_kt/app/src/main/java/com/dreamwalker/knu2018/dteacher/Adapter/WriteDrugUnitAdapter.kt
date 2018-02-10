package com.dreamwalker.knu2018.dteacher.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent
import com.dreamwalker.knu2018.dteacher.Utils.DrugWriteEvent
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent

import org.greenrobot.eventbus.EventBus

import java.util.ArrayList

import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch

/**
 * Created by KNU2017 on 2018-02-09.
 */
class MyViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var drugNameTextView: TextView
    var stepperTouch: StepperTouch

    init {
        drugNameTextView = itemView.findViewById<View>(R.id.drugNameTextView) as TextView
        stepperTouch = itemView.findViewById<View>(R.id.stepperTouch) as StepperTouch
    }
}

class WriteDrugUnitAdapter(internal var arrayList: ArrayList<String>, internal var context: Context) : RecyclerView.Adapter<MyViewHoler>() {
    internal var bus = EventBus.getDefault()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoler {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_drug_unit_card, parent, false)
        return MyViewHoler(view)
    }

    override fun onBindViewHolder(holder: MyViewHoler, position: Int) {
        holder.drugNameTextView.text = arrayList[position]

        holder.stepperTouch.stepper.setValue(1)
        holder.stepperTouch.stepper.setMin(1)
        holder.stepperTouch.stepper.setMax(30)
        holder.stepperTouch.stepper.addStepCallback(object : OnStepCallback {
            override fun onStep(i: Int, b: Boolean) {
                bus.post(DrugWriteEvent(i, arrayList[position], position))
                Log.e("onBindViewHolder", "onStep: " + i + arrayList[position])
            }
        })
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}
