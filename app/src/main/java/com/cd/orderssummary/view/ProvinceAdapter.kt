package com.cd.orderssummary.view

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cd.orderssummary.R
import com.cd.orderssummary.model.Province

class ProvinceAdapter(private val provinceDataset: ArrayList<Province>) :
        RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(private val itemCardView: View) : RecyclerView.ViewHolder(itemCardView) {
        var provInfo: TextView = itemCardView.findViewById(R.id.province_info)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProvinceAdapter.ViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.province_short_item, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        var prov: Province = provinceDataset[position]
        holder.provInfo.text = "${prov.numOrdes} number of orders from ${prov.name}"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = provinceDataset.size
}