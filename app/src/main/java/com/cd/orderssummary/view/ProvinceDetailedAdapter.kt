package com.cd.orderssummary.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cd.orderssummary.R
import com.cd.orderssummary.model.Province

class ProvinceDetailedAdapter (private val provinceDataset: ArrayList<Province>) :
        RecyclerView.Adapter<ProvinceDetailedAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(private val provLongItemView: View, private val contxt: Context) :
            RecyclerView.ViewHolder(provLongItemView) {
        var view: View = provLongItemView
        var context: Context = contxt
        var provLongHeader: TextView = provLongItemView.findViewById(R.id.province_long_header)
        lateinit var provLongRecyclerView: RecyclerView
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProvinceDetailedAdapter.ViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.province_long_item, parent, false) as ConstraintLayout
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(cardView, cardView.context)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        var prov: Province = provinceDataset[position]
        holder.provLongHeader.text = "${prov.name}"
        var provLongAdapter: RecyclerView.Adapter<*> = ProvinceLongAdapter(prov.orders)
        var provLongLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(holder.context,
        LinearLayoutManager.HORIZONTAL, false)

        holder.provLongRecyclerView =
                holder.view.findViewById<RecyclerView>(R.id.province_long_recycler_view).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = provLongLayoutManager

                    // specify an viewAdapter (see also next example)
                    adapter = provLongAdapter
                }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = provinceDataset.size
}