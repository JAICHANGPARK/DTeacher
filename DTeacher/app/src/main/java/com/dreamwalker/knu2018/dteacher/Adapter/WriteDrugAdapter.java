package com.dreamwalker.knu2018.dteacher.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Model.Person;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.List;

/**
 * Created by KNU2017 on 2018-02-09.
 */

public class WriteDrugAdapter extends RecyclerView.Adapter<WriteDrugAdapter.PersonViewHolder> {
    List<Person> people;
    OnItemClickListener mItemClickListener;

    public WriteDrugAdapter(List<Person> persons) {
        this.people = persons;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drug_card, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(people.get(i).name);
        personViewHolder.personAge.setText(people.get(i).age);
        personViewHolder.personPhoto.setImageResource(people.get(i).photoId);
        personViewHolder.cbSelected.setChecked(people.get(i).isSelected());

    }

    public void setItemSelected(int position, boolean isSelected) {
        if (position != -1) {
            people.get(position).setSelected(isSelected);
            notifyDataSetChanged();
        }
    }

    public List<Person> getPeople() {
        return people;
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        CheckBox cbSelected;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            cbSelected = (CheckBox) itemView.findViewById(R.id.cbSelected);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }


}
