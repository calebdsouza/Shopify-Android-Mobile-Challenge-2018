package com.cd.orderssummary.model

import java.io.Serializable

class Province(name: String): Serializable {
    var name:String
    var numOrdes: Int = 0
    var orders: ArrayList<Order> = ArrayList()

    init {
        this.name = name
    }
}