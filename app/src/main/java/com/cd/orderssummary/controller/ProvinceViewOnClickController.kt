package com.cd.orderssummary.controller

import android.content.Context
import android.content.Intent
import android.view.View
import com.cd.orderssummary.model.Province
import com.cd.orderssummary.view.ProvincesActivity

class ProvinceViewOnClickController(context: Context, dataset: ArrayList<Province>):
        View.OnClickListener {

    private val appContext: Context = context
    var provincesDataset: ArrayList<Province> = dataset

    override fun onClick(view: View?) {
        val sortedPorvinceIntent: Intent = Intent(this.appContext, ProvincesActivity::class.java)
        sortedPorvinceIntent.putExtra("province_dataset", this.provincesDataset)
        this.appContext.startActivity(sortedPorvinceIntent)
    }
}