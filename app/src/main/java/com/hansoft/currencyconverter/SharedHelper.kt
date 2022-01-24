package com.hansoft.currencyconverter

import android.content.Context
import android.graphics.Color
import android.graphics.fonts.Font
import java.util.*

class SharedHelper {
    private var mContext: Context? = null

    constructor() {}
    constructor(mContext: Context?) {
        this.mContext = mContext
    }

    fun saveColorAndSize(fontcolor : String,fontsize: String)
    {
        val sp = mContext!!.getSharedPreferences("firstSP", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("fontcolor", fontcolor)
        editor.putString("fontsize", fontsize)
        editor.commit()
    }

    fun readColorAndSize(): Map<String, String?> {
        val data: MutableMap<String, String?> = HashMap()
        val sp = mContext!!.getSharedPreferences("firstSP", Context.MODE_PRIVATE)
        data["fontcolor"] = sp.getString("fontcolor", "")
        data["fontsize"] = sp.getString("fontsize", "")
        return data
    }

}