package com.hansoft.currne

import android.util.Log
import org.json.JSONObject

class MyData {
    var data: String? = null
    var output : Double = 0.0


    var keysList: MutableList<String>? = null
    private fun downloadJsonFile(url: String): String? {
        // use okhttp3 to download json file
        try {
            data = NetworkService.INSTANCE.getString(url)

        } catch (e: Exception) {
            Log.d("aaa", "downloadJsonFile: Exception = " + e.localizedMessage)
        }
        return data
    }

    /**
     * created by qi zhu 23/01/2022
     * create new thread to loading the data , not block UI
     */
    fun loadJsonFile(url: String) {
        object : Thread() {
            override fun run() {
                val result = downloadJsonFile(url)
                if (result == null) {
                    // can not get json file

                } else {
                    // Log.d("aaa", "run: result = " + result)
                    Log.d("aaa", "run: aaa")

                    try {
                        // canadas.clear()
                        val root = JSONObject(result)



                        val data: JSONObject = root.getJSONObject("data")
                        //Log.d("aaa", "run: data = " + data.toString())
                        val brands : JSONObject = data.getJSONObject("Brands")
                        //Log.d("aaa", "run: brands = " + brands.toString())
                        val wbc : JSONObject = brands.getJSONObject("WBC")

                        val portfolios : JSONObject = wbc.getJSONObject("Portfolios")
                        val fx : JSONObject = portfolios.getJSONObject("FX")
                        val products : JSONObject = fx.getJSONObject("Products")
                        Log.d("aaa", "run: products = " + products.toString())
                        val keysToCopyIterator: Iterator<*> = products.keys()
                        keysList = ArrayList<String>()
                        while (keysToCopyIterator.hasNext()) {
                            val key = keysToCopyIterator.next() as String
                            Log.d("aaa", "run: key = " + key)
                            keysList!!.add(key)
                        }




                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            }
        }.start()
    }

    /**
     * created by qi zhu 23/01/2022
     * create new thread to loading the data , not block UI
     */
    fun convertCurrency(url: String, toCurr: String, euroVlaue: Double)
    {

        object : Thread() {
            override fun run() {
                val result = downloadJsonFile(url)
                if (result == null) {
                    // can not get json file

                } else {


                    try {

                        val root = JSONObject(result)
                        val data: JSONObject = root.getJSONObject("data")
                        val brands : JSONObject = data.getJSONObject("Brands")

                        val wbc : JSONObject = brands.getJSONObject("WBC")

                        val portfolios : JSONObject = wbc.getJSONObject("Portfolios")
                        val fx : JSONObject = portfolios.getJSONObject("FX")
                        val products : JSONObject = fx.getJSONObject("Products")

                        val keyobj = products.getJSONObject(toCurr)
                        val rateobj = keyobj.getJSONObject("Rates")
                        val childobj = rateobj.getJSONObject(toCurr)
                        try {


                            val buyTTobj = childobj.getString("buyTT").toDouble()
                            output = euroVlaue / buyTTobj


                        }
                        catch (e : Exception)
                        {
                            e.printStackTrace()

                        }




                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            }
        }.start()
    }

}