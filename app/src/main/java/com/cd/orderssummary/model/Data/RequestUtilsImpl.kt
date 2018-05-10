package com.cd.orders

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.cd.orderssummary.model.Order
import com.cd.orderssummary.model.Province
import com.cd.orderssummary.model.Year
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by cd on 2018-05-03.
 */
class RequestUtilsImpl : RequestUtils {

    override fun jsonObjectRequest(context: Context, url: String,
                                    completionHandler: (response: JSONObject?) -> Unit): JsonObjectRequest {

        var request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    completionHandler(null)
                }
        )

        return request
    }

    fun parseOrders(response: JSONObject?): ArrayList<Order> {
        // Create place to store resultant list
        var orderDataset: ArrayList<Order> = ArrayList()
        // Get JSON array of Order Objects
        val orders: JSONArray? = response?.getJSONArray("orders")

        // Loop through each order to extrapolate information
        for (i in 0 until orders?.length()!!) {
            try {
                var order: JSONObject = orders.getJSONObject(i)
                var shippingLoc: String
                try {
                    // Parse shipping information
                    var shippingAddr: JSONObject = order.getJSONObject("shipping_address")
                    shippingLoc = ("${shippingAddr.getString("city")}," +
                            " ${shippingAddr.getString("province")}")
                } catch (e: JSONException) {
                    e.stackTrace
                    shippingLoc = "N/A"
                }
                // Parse fulfillment information
                var items: JSONArray = order.getJSONArray("line_items")
                var numOfItems: Int = 0
                for (j in 0..items.length() - 1) {
                    numOfItems += items.getJSONObject(j).getInt("quantity")
                }

                // Parse payment information
                var totalPrice: String = order.getString("total_price")

                // Cancellation information
                var isCancelled: Boolean = false
                var cancellationTime: String? = order.getString("cancelled_at")
                var cancellationReason: String? = null
                if (cancellationTime != null) {
                    isCancelled = true
                    cancellationReason = order.getString("cancel_reason")
                }

                // Parse general order information
                var orderId: Int = order.getInt("id")
                var clientName: String
                try {
                    clientName =
                            order.getJSONObject("customer").getString("first_name")
                } catch (e: JSONException) {
                    e.stackTrace
                    clientName = "N/A"
                }
                var clientEmail: String = order.getString("email")
                var timeOfOrder: String = order.getString("created_at")

                // Build Order
                var mOrder: Order = Order(orderId, clientName, clientEmail, timeOfOrder,
                        shippingLoc, totalPrice, numOfItems)

                if (isCancelled) {
                    mOrder.cancellationTime = cancellationTime
                    mOrder.cancellationReason = cancellationReason
                }

                // Add built Order to the data set
                orderDataset.add(mOrder)
            } catch (e: JSONException) {
                e.stackTrace
            }
        }

        return orderDataset
    }

    fun organizeOrdersByYear(orders: ArrayList<Order>): ArrayList<Year> {
        // Create a places to organize the given Orders by their year
        var map: HashMap<Int, Year> = HashMap()
        var result: ArrayList<Year> = ArrayList()

        for (i in 0 until orders.size) {
            var currOrder: Order = orders[i]
            var orderYear: Int = Integer.parseInt(
                    currOrder.createdTime.substringBefore("-"))

            var value: Year? = map[orderYear]
            if(value != null) {
                value.orders.add(currOrder)
                map[orderYear] = value
            } else {
                var new: Year = Year(orderYear)
                new.orders.add(currOrder)
                map[orderYear] = new
            }
        }

        var sortedYears: ArrayList<Int> = ArrayList(map.keys.sorted())

        for (j in 0 until sortedYears.size) {
            result.add(map.getValue(sortedYears[j]))
        }

        return result
    }


    fun getProvinces(orders: ArrayList<Order>): ArrayList<Province> {
        // Create a places to organize the given Orders by their province
        var map: HashMap<String, Province> = HashMap()
        var result: ArrayList<Province> = ArrayList()

        for (i in 0 until orders.size) {
            var currOrder: Order = orders[i]
            var orderProv:String = currOrder.shippingLocation.substringAfter(",")

            var value: Province? = map[orderProv]
            if (value != null) {
                value.numOrdes += 1
                value.orders.add(currOrder)

                map[orderProv] = value
            } else {
                var new: Province = Province(orderProv)
                new.numOrdes += 1
                new.orders.add(currOrder)

                map[orderProv] = new
            }
        }


        var sortedProvinces: ArrayList<String> = ArrayList(map.keys.sorted())

        for (j in 0 until sortedProvinces.size) {
            result.add(map.getValue(sortedProvinces[j]))
        }

        return result
    }
}