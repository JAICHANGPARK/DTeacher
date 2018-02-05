package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;

import java.util.List;

/**
 * Created by KNU2017 on 2018-02-05.
 */

class OSLViewHolder extends RecyclerView.ViewHolder{
    TextView licenseText;

    public OSLViewHolder(View itemView) {
        super(itemView);
        licenseText = (TextView) itemView.findViewById(R.id.licenseText);
    }
}

public class OSLAdapter   extends RecyclerView.Adapter<OSLViewHolder>{

    private List<String> licenseList;
    private Context context;

    /**
     * 리스트와 컨택스트를 생성자에서 받는다.
     * @param licenseList
     * @param context
     */
    public OSLAdapter(List<String> licenseList, Context context) {
        this.licenseList = licenseList;
        this.context = context;
    }

    @Override
    public OSLViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_openlicense,parent,false);
        OSLViewHolder oslViewHolder = new OSLViewHolder(itemView);
        return oslViewHolder;
    }

    @Override
    public void onBindViewHolder(OSLViewHolder holder, int position) {
        holder.licenseText.setText(licenseList.get(position));
    }

    @Override
    public int getItemCount() {
        return licenseList.size();
    }
}
