package com.dreamwalker.knu2018.dteacher.Fragment

import android.content.Context
import android.content.res.TypedArray
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dpizarro.autolabel.library.AutoLabelUI
import com.dpizarro.autolabel.library.Label
import com.dreamwalker.knu2018.dteacher.Adapter.WriteDrugAdapter
import com.dreamwalker.knu2018.dteacher.Model.Person
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.DrugDataEvent
import com.dreamwalker.knu2018.dteacher.Utils.TextChangedEvent

import org.greenrobot.eventbus.EventBus

import java.util.ArrayList
import java.util.Arrays

class WriteDrugFragment : Fragment() {
    private val KEY_INSTANCE_STATE_PEOPLE = "statePeople"
    private var mParam1: String? = null

    private var mAutoLabel: AutoLabelUI? = null
    private var mPersonList: MutableList<Person>? = null
    private var adapter: WriteDrugAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var rrapid: ArrayList<String>? = null
    private var rapid: ArrayList<String>? = null
    private var neutral: ArrayList<String>? = null
    private var longtime: ArrayList<String>? = null
    private var mixed: ArrayList<String>? = null

    internal var bus = EventBus.getDefault()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_write_drug, container, false)
        findViews(view)
        setListeners()
        setRecyclerView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rrapid = ArrayList()
        rapid = ArrayList()
        longtime = ArrayList()
        neutral = ArrayList()
        mixed = ArrayList()

        for (i in 0..4) {
            rrapid!!.add(i, "unknown")
            rapid!!.add(i, "unknown")
            longtime!!.add(i, "unknown")
            neutral!!.add(i, "unknown")
            mixed!!.add(i, "unknown")
        }
        for (i in 0..9) {
            mixed!!.add(i, "unknown")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            val people = savedInstanceState.getParcelableArrayList<Person>(KEY_INSTANCE_STATE_PEOPLE)
            if (people != null) {
                mPersonList = people
                adapter!!.people = people
                recyclerView!!.adapter = adapter
            }
        }
    }

    private fun itemListClicked(position: Int) {
        val person = mPersonList!![position]
        val isSelected = person.isSelected
        val success: Boolean
        val index = 0
        if (isSelected) {
            if (mParam1 == "1") {
                rrapid!![position] = "unknown"
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 1))
            } else if (mParam1 == "2") {
                rapid!![position] = "unknown"
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 2))
            } else if (mParam1 == "3") {
                neutral!![position] = "unknown"
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 3))
            } else if (mParam1 == "4") {
                longtime!![position] = "unknown"
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 4))
            } else if (mParam1 == "5") {
                mixed!![position] = "unknown"
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 5))
            }

            Log.e(TAG, "itemListClicked: removeLabel " + person.age + ", " + person.name + "position :" + position)
            success = mAutoLabel!!.removeLabel(position)
        } else {

            if (mParam1 == "1") {
                rrapid!![position] = person.name
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 1))
            } else if (mParam1 == "2") {
                rapid!![position] = person.name
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 2))
            } else if (mParam1 == "3") {
                neutral!![position] = person.name
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 3))
            } else if (mParam1 == "4") {
                longtime!![position] = person.name
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 4))
            } else if (mParam1 == "5") {
                mixed!![position] = person.name
                bus.post(DrugDataEvent(rrapid, rapid, neutral, longtime, mixed, index, 5))
            }

            Log.e(TAG, "itemListClicked:  addLabel " + person.age + ", " + person.name + "position :" + position)
            success = mAutoLabel!!.addLabel(person.name, position)
        }
        if (success) {
            Log.e(TAG, "itemListClicked:  success " + person.age + ", " + person.name + "position :" + position)
            adapter!!.setItemSelected(position, !isSelected)

            //            for (int i = 0; i < r.size(); i++) {
            //                Log.e(TAG, "ArrayList Value : " + "index : " + i + "value : " + temp.get(i));
            //            }
        }
    }

    private fun setListeners() {
        mAutoLabel!!.setOnLabelsCompletedListener { Snackbar.make(recyclerView!!, "Completed!", Snackbar.LENGTH_SHORT).show() }

        mAutoLabel!!.setOnRemoveLabelListener { view, position -> adapter!!.setItemSelected(position, false) }

        mAutoLabel!!.setOnLabelsEmptyListener { Snackbar.make(recyclerView!!, "EMPTY!", Snackbar.LENGTH_SHORT).show() }


        mAutoLabel!!.setOnLabelClickListener { v -> Snackbar.make(recyclerView!!, "" + v.id, Snackbar.LENGTH_SHORT).show() }
    }

    private fun findViews(view: View) {
        mAutoLabel = view.findViewById<View>(R.id.label_view) as AutoLabelUI
        mAutoLabel!!.backgroundResource = R.drawable.round_corner_background
        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
    }

    private fun setRecyclerView() {

        recyclerView!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = llm
        mPersonList = ArrayList()

        if (mParam1 == "1") {
            // TODO: 2018-02-09 초 속효성 데이터 리스트 처리
            val names = Arrays.asList(*resources.getStringArray(R.array.names_RRapid))
            val ages = Arrays.asList(*resources.getStringArray(R.array.description_RRapid))
            val photos = resources.obtainTypedArray(R.array.photos)
            //Populate list
            for (i in names.indices) {
                mPersonList!!.add(Person(names[i], ages[i], photos.getResourceId(i, -1), false))
            }
            photos.recycle()
        } else if (mParam1 == "2") {
            // TODO: 2018-02-09 속효성 데이터 리스트 처리
            val names = Arrays.asList(*resources.getStringArray(R.array.names_Rapid))
            val ages = Arrays.asList(*resources.getStringArray(R.array.description_Rapid))
            val photos = resources.obtainTypedArray(R.array.photos)
            for (i in names.indices) {
                mPersonList!!.add(Person(names[i], ages[i], photos.getResourceId(i, -1), false))
            }
            photos.recycle()
        } else if (mParam1 == "3") {
            // TODO: 2018-02-09 중간형 데이터 리스트 처리
            val names = Arrays.asList(*resources.getStringArray(R.array.names_netural))
            val ages = Arrays.asList(*resources.getStringArray(R.array.description_netural))
            val photos = resources.obtainTypedArray(R.array.photos)
            for (i in names.indices) {
                mPersonList!!.add(Person(names[i], ages[i], photos.getResourceId(i, -1), false))
            }
            photos.recycle()
        } else if (mParam1 == "4") {
            // TODO: 2018-02-09 지속형 데이터 리스트 처리
            val names = Arrays.asList(*resources.getStringArray(R.array.names_longtime))
            val ages = Arrays.asList(*resources.getStringArray(R.array.description_longtime))
            val photos = resources.obtainTypedArray(R.array.photos)
            for (i in names.indices) {
                mPersonList!!.add(Person(names[i], ages[i], photos.getResourceId(i, -1), false))
            }
            photos.recycle()
        } else if (mParam1 == "5") {
            // TODO: 2018-02-09 혼합형 데이터 리스트 처리
            val names = Arrays.asList(*resources.getStringArray(R.array.names_mixed))
            val ages = Arrays.asList(*resources.getStringArray(R.array.description_mixed))
            val photos = resources.obtainTypedArray(R.array.photos)
            for (i in names.indices) {
                mPersonList!!.add(Person(names[i], ages[i], photos.getResourceId(i, -1), false))
            }
            photos.recycle()
        } else {

        }

        adapter = WriteDrugAdapter(mPersonList!!)
        recyclerView!!.adapter = adapter
        adapter!!.setOnItemClickListener(object : WriteDrugAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                itemListClicked(position)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_INSTANCE_STATE_PEOPLE, adapter!!.people as ArrayList<out Parcelable>)
    }

    companion object {

        private val TAG = "RecyclerViewFragment"
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): WriteDrugFragment {
            val fragment = WriteDrugFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
