package com.cd.orderssummary.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Dataset
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.NumberPicker
import android.widget.TextView
import com.cd.orders.RequestQueueSingleton
import com.cd.orders.RequestUtilsImpl
import com.cd.orderssummary.R
import com.cd.orderssummary.controller.ProvinceViewOnClickController
import com.cd.orderssummary.controller.YearOnSelectController
import com.cd.orderssummary.model.Order
import com.cd.orderssummary.model.Province
import com.cd.orderssummary.model.Year

class OrderSummaryActivity : AppCompatActivity() {

    // Declare fields for province section in this activity
    private lateinit var provinceRecyclerView: RecyclerView
    private lateinit var provinceViewAdapter: RecyclerView.Adapter<*>
    private lateinit var provinceViewLayoutManager: RecyclerView.LayoutManager

    // Declare fields for year section in this activity
    private lateinit var yearRecyclerView: RecyclerView
    private lateinit var yearAdapter: RecyclerView.Adapter<*>
    private lateinit var yearLayoutManager: RecyclerView.LayoutManager

    // Declare activity items
    private lateinit var provinceHeader: TextView
    private lateinit var yearNumPicker: NumberPicker

    // Declare datasets
    private lateinit var orderDataset: ArrayList<Order>
    private lateinit var provinceDataset: ArrayList<Province>
    private  lateinit var yearDataset: ArrayList<Year>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        this.provinceHeader = findViewById(R.id.province_header)
        this.yearNumPicker = findViewById(R.id.select_year)

        orderDataset = ArrayList()
        provinceDataset = ArrayList()
        yearDataset = ArrayList()

        // Parse JSON
        val requestQueue = RequestQueueSingleton.getInstance(this.applicationContext).requestQueue
        val requestUtil = RequestUtilsImpl()
        val url = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

        var request = requestUtil.jsonObjectRequest(this, url) {response ->

            this.orderDataset = requestUtil.parseOrders(response)
            setTitle("Order Summary: ${orderDataset.size} orders")


            provinceDataset = requestUtil.getProvinces(orderDataset)
            yearDataset = requestUtil.organizeOrdersByYear(orderDataset)

            this.provinceViewLayoutManager = LinearLayoutManager(this)
            this.provinceViewAdapter = ProvinceAdapter(provinceDataset)
            this.provinceRecyclerView =
                    findViewById<RecyclerView>(R.id.province_recycler_view).apply {
                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        setHasFixedSize(true)

                        // use a linear layout manager
                        layoutManager = provinceViewLayoutManager

                        // specify an viewAdapter (see also next example)
                        adapter = provinceViewAdapter

                    }

            //this.yearLayoutManager = LinearLayoutManager(this)
            //this.yearAdapter = YearAdapter(ye)
            var maxYear: Int = yearDataset.get(yearDataset.size -1).year
            var minYear: Int = yearDataset.get(0).year
            this.yearNumPicker.maxValue = maxYear
            this.yearNumPicker.minValue = minYear
            this.yearNumPicker.wrapSelectorWheel = false
            this.yearNumPicker.setOnValueChangedListener(YearOnSelectController(this,
                    yearDataset, maxYear, minYear))

            this.provinceHeader.setOnClickListener(ProvinceViewOnClickController(this,
                    provinceDataset))
        }

        RequestQueueSingleton.getInstance(this).addToRequestQueue(request)
    }
}
