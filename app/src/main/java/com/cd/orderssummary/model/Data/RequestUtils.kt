package com.cd.orders

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

/**
 * Created by cd on 2018-05-04.
 */
interface RequestUtils {

    fun jsonObjectRequest(context: Context, url: String,
                           completionHandler: (response: JSONObject?) -> Unit): JsonObjectRequest
}