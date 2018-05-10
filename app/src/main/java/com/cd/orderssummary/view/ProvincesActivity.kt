package com.cd.orderssummary.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cd.orderssummary.R
import com.cd.orderssummary.model.Province

class ProvincesActivity : AppCompatActivity() {

    // Declare fields for province section in this activity
    private lateinit var provDetailedRecyclerView: RecyclerView
    private lateinit var provDetailedAdapter: RecyclerView.Adapter<*>
    private lateinit var provDetailedLayoutManager: RecyclerView.LayoutManager

    // Declare province dataset
    private lateinit var provinceDataset: ArrayList<Province>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provinces)

        var intent: Intent = getIntent()
        this.provinceDataset =
                intent.getParcelableArrayListExtra<Parcelable>("province_dataset")
                        as ArrayList<Province>

        this.provDetailedLayoutManager = LinearLayoutManager(this)
        this.provDetailedAdapter = ProvinceDetailedAdapter(provinceDataset)
        this.provDetailedRecyclerView =
                findViewById<RecyclerView>(R.id.province_detailed_recycler_view).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = provDetailedLayoutManager

                    // specify an viewAdapter (see also next example)
                    adapter = provDetailedAdapter

                }
    }
}
