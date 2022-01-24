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


    fun save(username: String?, password: String?) {
        val sp = mContext!!.getSharedPreferences("mysp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.commit()
        //  Toast.makeText(mContext, "Username and password are saved in SharedPreference", Toast.LENGTH_SHORT).show();
    }

    fun save(addresslist: ArrayList<String>?) {
        val sp = mContext!!.getSharedPreferences("hersp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        val set: Set<String> = HashSet(addresslist)
        editor.putStringSet("addresslist", set)
    }

    fun save(runNumber: String?, driverName: String?, data: String?) {
        val sp = mContext!!.getSharedPreferences("hissp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("runNumber", runNumber)
        editor.putString("driverName", driverName)
        editor.commit()
        //  Toast.makeText(mContext, "Username and password are saved in SharedPreference", Toast.LENGTH_SHORT).show();
    }

    fun read(): Map<String, String?> {
        val data: MutableMap<String, String?> = HashMap()
        val sp = mContext!!.getSharedPreferences("mysp", Context.MODE_PRIVATE)
        data["username"] = sp.getString("username", "")
        data["password"] = sp.getString("password", "")
        return data
    }

    fun readColorAndSize(): Map<String, String?> {
        val data: MutableMap<String, String?> = HashMap()
        val sp = mContext!!.getSharedPreferences("firstSP", Context.MODE_PRIVATE)
        data["fontcolor"] = sp.getString("fontcolor", "")
        data["fontsize"] = sp.getString("fontsize", "")
        return data
    }

    fun readAddressList(): ArrayList<String> {
        val sp = mContext!!.getSharedPreferences("hersp", Context.MODE_PRIVATE)
        val set = sp.getStringSet("addresslist", HashSet())
        return ArrayList(set)
    }

    fun readData(): Map<String, String?> {
        val data: MutableMap<String, String?> = HashMap()
        val sp = mContext!!.getSharedPreferences("hissp", Context.MODE_PRIVATE)
        data["runNumber"] = sp.getString("runNumber", "")
        data["driverName"] = sp.getString("driverName", "")
        return data
    }
}