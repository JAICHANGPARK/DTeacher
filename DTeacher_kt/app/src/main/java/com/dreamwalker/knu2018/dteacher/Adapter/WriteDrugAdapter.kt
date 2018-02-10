package com.dreamwalker.knu2018.dteacher.Adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

import com.dreamwalker.knu2018.dteacher.Model.Person
import com.dreamwalker.knu2018.dteacher.R

/**
 * Created by KNU2017 on 2018-02-09.
 */

class WriteDrugAdapter(var people: List<Person>) : RecyclerView.Adapter<WriteDrugAdapter.PersonViewHolder>() {
    internal var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PersonViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_drug_card, viewGroup, false)
        return PersonViewHolder(v)
    }

    override fun onBindViewHolder(personViewHolder: PersonViewHolder, i: Int) {
        personViewHolder.personName.text = people[i].name
        personViewHolder.personAge.text = people[i].age
        personViewHolder.personPhoto.setImageResource(people[i].photoId)
        personViewHolder.cbSelected.isChecked = people[i].isSelected

    }

    fun setItemSelected(position: Int, isSelected: Boolean) {
        if (position != -1) {
            people[position].isSelected = isSelected
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class PersonViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var cv: CardView
        internal var personName: TextView
        internal var personAge: TextView
        internal var personPhoto: ImageView
        internal var cbSelected: CheckBox

        init {
            itemView.setOnClickListener(this)
            cv = itemView.findViewById<View>(R.id.cv) as CardView
            personName = itemView.findViewById<View>(R.id.person_name) as TextView
            personAge = itemView.findViewById<View>(R.id.person_age) as TextView
            personPhoto = itemView.findViewById<View>(R.id.person_photo) as ImageView
            cbSelected = itemView.findViewById<View>(R.id.cbSelected) as CheckBox
        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(v, position)
            }
        }
    }


}
