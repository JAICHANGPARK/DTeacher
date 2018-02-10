package com.dreamwalker.knu2018.dteacher.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.dreamwalker.knu2018.dteacher.R
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener

class DangActivity : AppCompatActivity() {

    private var spaceNavigationView: SpaceNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang)

        spaceNavigationView = findViewById<View>(R.id.space)
        spaceNavigationView!!.initWithSaveInstanceState(savedInstanceState)
        spaceNavigationView!!.addSpaceItem(SpaceItem("HOME", R.drawable.ic_home_black_24dp))
        spaceNavigationView!!.addSpaceItem(SpaceItem("SEARCH", R.drawable.ic_search_black_24dp))

        spaceNavigationView!!.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                Toast.makeText(this@DangActivity, "onCentreButtonClick", Toast.LENGTH_SHORT).show()
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                Toast.makeText(this@DangActivity, itemIndex.toString() + " " + itemName, Toast.LENGTH_SHORT).show()
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                Toast.makeText(this@DangActivity, itemIndex.toString() + " " + itemName, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        spaceNavigationView!!.onSaveInstanceState(outState!!)
    }
}
