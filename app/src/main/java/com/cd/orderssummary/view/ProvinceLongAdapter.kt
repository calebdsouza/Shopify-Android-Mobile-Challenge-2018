package com.cd.orderssummary.view

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cd.orderssummary.R
import com.cd.orderssummary.model.Order
import com.cd.orderssummary.model.Province

class ProvinceLongAdapter(private val orderDataset: ArrayList<Order>) :
        RecyclerView.Adapter<ProvinceLongAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(private val itemCardView: View) : RecyclerView.ViewHolder(itemCardView) {
        var orderIdTxt: TextView = itemCardView.findViewById(R.id.order_id)
        var clientName: TextView = itemCardView.findViewById(R.id.client_name)
        var emailTxt: TextView = itemCardView.findViewById(R.id.client_email)
        var shippingLocTxt: TextView = itemCardView.findViewById(R.id.shipping_location)
        var totalPriceTxt: TextView = itemCardView.findViewById(R.id.total_price)
        var numItemsTxt: TextView = itemCardView.findViewById(R.id.num_items)
        var orderTimeTxt: TextView = itemCardView.findViewById(R.id.order_created_on)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProvinceLongAdapter.ViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        var order: Order= orderDataset[position]
        holder.orderIdTxt.text = "Order ID: ${order.orderId}"
        holder.clientName.text = "Buyer's Name: ${order.clientName}"
        holder.emailTxt.text = "Buyer's Email: ${order.clientEmail}"
        holder.numItemsTxt.text = "Number Of Items: ${order.totalNumOfItems}"
        holder.totalPriceTxt.text = "Total Price: ${order.totalPrice}"
        holder.shippingLocTxt.text = "Shipping Location: ${order.shippingLocation}"
        holder.orderTimeTxt.text = "Order Placed On: ${order.createdTime}"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = orderDataset.size
}