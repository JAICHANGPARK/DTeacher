package com.dreamwalker.knu2018.dteacher.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.R

/**
 * Created by KNU2017 on 2018-02-05.
 */

class OSLViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var licenseText: TextView

    init {
        licenseText = itemView.findViewById<View>(R.id.licenseText) as TextView
    }
}

class OSLAdapter
/**
 * 리스트와 컨택스트를 생성자에서 받는다.
 * @param licenseList
 * @param context
 */
(private val licenseList: List<String>, private val context: Context) : RecyclerView.Adapter<OSLViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OSLViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_openlicense, parent, false)
        return OSLViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OSLViewHolder, position: Int) {
        holder.licenseText.text = licenseList[position]
    }

    override fun getItemCount(): Int {
        return licenseList.size
    }
}
