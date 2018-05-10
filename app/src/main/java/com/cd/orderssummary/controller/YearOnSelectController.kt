package com.cd.orderssummary.controller

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.NumberPicker
import com.cd.orderssummary.R
import com.cd.orderssummary.model.Year
import com.cd.orderssummary.view.YearAdapter

class YearOnSelectController(context: Context, years: ArrayList<Year>, maxOpt: Int, minOpt: Int) :
        NumberPicker.OnValueChangeListener {

    private var appContext = context
    private var activity = this.appContext as AppCompatActivity
    private var years = years

    override fun onValueChange(numberPicker: NumberPicker?, oldYear: Int, newYear: Int) {

        var yearSelected: Year? = findYear(years, newYear)

        println("SIZE: " + yearSelected!!.orders.size)

        var yearLayoutManager = LinearLayoutManager(appContext)
        var yearAdapter = YearAdapter(yearSelected!!.orders)
        var yearRecyclerView = activity.findViewById<RecyclerView>(
                R.id.year_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = yearLayoutManager

            // specify an viewAdapter (see also next example)
            adapter = yearAdapter
        }

    }

    private fun findYear(list: ArrayList<Year>, year: Int): Year? {
        var low = 0
        var high = list.size - 1

        while ( high >= low) {
            var mid = (high + low) / 2

            if (list[mid].year == year) {
                return list[mid]
            } else if (list[mid].year > year) {
                high = mid - 1
            } else if (list[mid].year < year) {
                low = mid + 1
            }
        }

        return null
    }
}