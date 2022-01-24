package com.hansoft.currencyconverter


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.io.IOException


/**
 * created by qi zhu 23/01/2022
 * Create MainActivity to load json data and show the data
 */
class MainActivity : AppCompatActivity() {

    var data: String? = null
    val REQUEST_PERMISSION = 250
    val GET_DATA_SUCCESS = 1
    val NETWORK_ERROR = 2
    val SERVER_ERROR = 3
    val CALCULATE = 4
    var output : Double = 0.0

    var keysList: MutableList<String>? = null
    var spinnerCurrency: Spinner? = null
    var textViewResult: TextView? = null
    var textViewCurrency : TextView? = null
    var textViewAmount : TextView? = null
    var textViewRes : TextView? = null
    var editTextNumber : EditText? = null
    var buttonConvert : Button? = null
    private var fontcolor : String? = null
    private var fontsize : String? = null
    private var myFloatingActionButton: FloatingActionButton? = null
    private var mContext: Context? = null
    private var sharedHelper: SharedHelper? = null
    private var colorArray : Array<String> = arrayOf("Black", "White", "Red", "Blue", "Yellow", "Green")
    /**
     * created by qi zhu 23/01/2022
     * load json data and show the data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = applicationContext
        sharedHelper = SharedHelper(mContext)

        bindview()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), REQUEST_PERMISSION)
            return
        }


        loadJsonFile("https://www.westpac.com.au/bin/getJsonRates.wbc.fx.json")

    }

    override fun onResume() {
        super.onResume()
        val v: View? = spinnerCurrency?.getSelectedView()

        if (v == null) {

        } else {
            if ((fontcolor == null) || (fontsize == null))
            {

            }
            else {
                (v as TextView).textSize = fontsize!!.toFloat()
                if (fontcolor == "Black") {
                    (v as TextView).setTextColor(Color.BLACK)
                } else if (fontcolor == "White") {
                    (v as TextView)?.setTextColor(Color.WHITE)
                } else if (fontcolor == "Red") {
                    (v as TextView)?.setTextColor(Color.RED)
                } else if (fontcolor == "Blue") {
                    (v as TextView)?.setTextColor(Color.BLUE)
                } else if (fontcolor == "Yellow") {
                    (v as TextView)?.setTextColor(Color.YELLOW)
                } else if (fontcolor == "Green") {
                    (v as TextView).setTextColor(Color.GREEN)
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("aaa", "onStart: aaa")
        val data: Map<String, String?> = sharedHelper!!.readColorAndSize()

        fontcolor = data["fontcolor"]
        fontsize = data["fontsize"]
        if ((fontcolor == null) || (fontsize == null))
        {

        }
        else
        {

            textViewCurrency!!.textSize = fontsize!!.toFloat()
            textViewAmount!!.textSize = fontsize!!.toFloat()
            textViewRes!!.textSize = fontsize!!.toFloat()
            textViewResult!!.textSize = fontsize!!.toFloat()
            buttonConvert!!.textSize = fontsize!!.toFloat()
            editTextNumber!!.textSize = fontsize!!.toFloat()
            if (fontcolor == "Black") {
                textViewCurrency!!.setTextColor(Color.BLACK)
                textViewAmount!!.setTextColor(Color.BLACK)
                textViewRes!!.setTextColor(Color.BLACK)
                textViewResult!!.setTextColor(Color.BLACK)
                editTextNumber!!.setTextColor(Color.BLACK)
                buttonConvert!!.setTextColor(Color.BLACK)

            }
            else if (fontcolor == "White") {
                textViewCurrency!!.setTextColor(Color.WHITE)
                textViewAmount!!.setTextColor(Color.WHITE)
                textViewRes!!.setTextColor(Color.WHITE)
                textViewResult!!.setTextColor(Color.WHITE)
                editTextNumber!!.setTextColor(Color.WHITE)
                buttonConvert!!.setTextColor(Color.WHITE)

            }
            else if (fontcolor == "Red") {
                textViewCurrency!!.setTextColor(Color.RED)
                textViewAmount!!.setTextColor(Color.RED)
                textViewRes!!.setTextColor(Color.RED)
                textViewResult!!.setTextColor(Color.RED)
                editTextNumber!!.setTextColor(Color.RED)
                buttonConvert!!.setTextColor(Color.RED)

            }
            else if (fontcolor == "Blue") {
                textViewCurrency!!.setTextColor(Color.BLUE)
                textViewAmount!!.setTextColor(Color.BLUE)
                textViewRes!!.setTextColor(Color.BLUE)
                textViewResult!!.setTextColor(Color.BLUE)
                editTextNumber!!.setTextColor(Color.BLUE)
                buttonConvert!!.setTextColor(Color.BLUE)

            }
            else if (fontcolor == "Yellow") {
                textViewCurrency!!.setTextColor(Color.YELLOW)
                textViewAmount!!.setTextColor(Color.YELLOW)
                textViewRes!!.setTextColor(Color.YELLOW)
                textViewResult!!.setTextColor(Color.YELLOW)
                editTextNumber!!.setTextColor(Color.YELLOW)
                buttonConvert!!.setTextColor(Color.YELLOW)

            }
            else if (fontcolor == "Green") {
                textViewCurrency!!.setTextColor(Color.GREEN)
                textViewAmount!!.setTextColor(Color.GREEN)
                textViewRes!!.setTextColor(Color.GREEN)
                textViewResult!!.setTextColor(Color.GREEN)
                editTextNumber!!.setTextColor(Color.GREEN)
                buttonConvert!!.setTextColor(Color.GREEN)

            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val handler: Handler = Handler(Looper.getMainLooper(), Handler.Callback {
        when (it.what) {
            GET_DATA_SUCCESS -> {

                Log.d("aaa", ": bc")
                val spinnerArrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, keysList as ArrayList<String>)
                spinnerCurrency?.setAdapter(spinnerArrayAdapter);
                spinnerCurrency?.setSelection(0)
                /*
                val v: View? = spinnerCurrency?.getSelectedView()

                if (v == null) {

                } else {

                    if (fontcolor == "Black") {
                        (v as TextView).setTextColor(Color.BLACK)
                    } else if (fontcolor == "White") {
                        (v as TextView)?.setTextColor(Color.WHITE)
                    } else if (fontcolor == "Red") {
                        (v as TextView)?.setTextColor(Color.RED)
                    } else if (fontcolor == "Blue") {
                        (v as TextView)?.setTextColor(Color.BLUE)
                    } else if (fontcolor == "Yellow") {
                        (v as TextView)?.setTextColor(Color.YELLOW)
                    } else if (fontcolor == "Green") {
                        (v as TextView).setTextColor(Color.GREEN)
                    }
                }

                 */

            }
            NETWORK_ERROR -> Toast.makeText(
                    this,
                    "Newwork is not available",
                    Toast.LENGTH_SHORT
            ).show()
            SERVER_ERROR -> Toast.makeText(
                    this,
                    "Server does not work",
                    Toast.LENGTH_SHORT
            ).show()
            CALCULATE -> {
                textViewResult!!.text = String.format("%.2f", output)
            }
        }
        true

    })

    /**
     * Get every control by using findviewbyid
     */
    fun bindview()
    {
        spinnerCurrency = findViewById(R.id.spinnerCurrency)
        textViewCurrency = findViewById(R.id.textViewCurrency)
        textViewAmount = findViewById(R.id.textViewAmount)
        textViewRes = findViewById(R.id.textViewRes)
        editTextNumber = findViewById(R.id.editTextNumber)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)
        myFloatingActionButton = findViewById(R.id.floatingActionButton)
        myFloatingActionButton?.setOnClickListener(View.OnClickListener {
            val myintent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(myintent)

        })
        buttonConvert!!.setOnClickListener {
            if (!editTextNumber!!.text.toString().isEmpty()) {
                val toCurr = spinnerCurrency!!.selectedItem.toString()
                val euroVlaue = java.lang.Double.valueOf(editTextNumber!!.text.toString())
                val url = "https://www.westpac.com.au/bin/getJsonRates.wbc.fx.json"
                Toast.makeText(this@MainActivity, "Please Wait..", Toast.LENGTH_SHORT).show()
                try {


                    convertCurrency(url, toCurr, euroVlaue)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Please Enter a Value to Convert..", Toast.LENGTH_SHORT).show()
            }
        }
        spinnerCurrency?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                //Change the selected items's text size
                (view as TextView).textSize = fontsize!!.toFloat()
                //Change the selected item's text color
                if (fontcolor == "Black") {
                    (view as TextView).setTextColor(Color.BLACK)
                } else if (fontcolor == "White") {
                    (view as TextView).setTextColor(Color.WHITE)
                } else if (fontcolor == "Red") {
                    (view as TextView).setTextColor(Color.RED)
                } else if (fontcolor == "Blue") {
                    (view as TextView).setTextColor(Color.BLUE)
                } else if (fontcolor == "Yellow") {
                    (view as TextView).setTextColor(Color.YELLOW)
                } else if (fontcolor == "Green") {
                    (view as TextView).setTextColor(Color.GREEN)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    @GetJSONData("use okhttp3 to download json file")
    private fun downloadJsonFile(url: String): String? {

        try {
            data = NetworkService.INSTANCE.getString(url)

        } catch (e: Exception) {
            Log.d("aaa", "downloadJsonFile: Exception = " + e.localizedMessage)
        }
        return data
    }

    /**
     * created by qi zhu 23/01/2022
     * load json data and show the data
     */
    private fun loadJsonFile(url: String) {

        /**
         * created by qi zhu 23/01/2022
         * create new thread to loading the data , not block UI
         */

        object : Thread() {
            override fun run() {
                val result = downloadJsonFile(url)
                if (result == null) {
                    // can not get json file
                    handler.sendEmptyMessage(SERVER_ERROR)
                } else {



                    try {

                        val root = JSONObject(result)

                        val data: JSONObject = root.getJSONObject("data")

                        val brands : JSONObject = data.getJSONObject("Brands")

                        val wbc : JSONObject = brands.getJSONObject("WBC")

                        val portfolios : JSONObject = wbc.getJSONObject("Portfolios")
                        val fx : JSONObject = portfolios.getJSONObject("FX")
                        val products : JSONObject = fx.getJSONObject("Products")

                        val keysToCopyIterator: Iterator<*> = products.keys()
                        keysList = ArrayList<String>()
                        while (keysToCopyIterator.hasNext()) {
                            val key = keysToCopyIterator.next() as String

                            keysList!!.add(key)
                        }



                        handler.sendEmptyMessage(GET_DATA_SUCCESS)
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
    private fun convertCurrency(url: String, toCurr: String, euroVlaue: Double)
    {

        object : Thread() {
            override fun run() {
                val result = downloadJsonFile(url)
                if (result == null) {
                    // can not get json file
                    handler.sendEmptyMessage(SERVER_ERROR)
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
                            handler.sendEmptyMessage(CALCULATE)
                        }
                        catch (e: Exception)
                        {
                            textViewResult!!.text = ""
                        }




                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            }
        }.start()
    }


}