package com.cd.orderssummary.model

class Year(year: Int) {
    var year: Int
    var orders: ArrayList<Order> = ArrayList()

    init {
        this.year = year
    }
}