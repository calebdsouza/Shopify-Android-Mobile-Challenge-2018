package com.cd.orderssummary.model


import java.io.Serializable

/**
 * Created by cd on 2018-05-02.
 */

class Order(id: Int, name: String, email: String, time: String, location: String,
            price: String, totalItems: Int) : Serializable {

    // Fields
        // General Order Object fields
    var orderId: Int
    var clientName: String?
    var clientEmail: String
    var createdTime: String
    var shippingLocation: String
        // Fields important to fulfillment
    var totalNumOfItems: Int
        // Fields important to payments
    var totalPrice: String
        // Fields important to cancellation
    var cancellationReason: String? = null
    var cancellationTime: String? = null

    // Initialized fields
    init {
        this.orderId = id
        this.clientName = name
        this.clientEmail = email
        this.createdTime = time
        this.shippingLocation = location
        this.totalNumOfItems = totalItems
        this.totalPrice = price
    }
}