
package com.hansoft.currencyconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import java.lang.Exception

class SettingActivity : AppCompatActivity() {
    private var mContext: Context? = null
    private var sharedHelper: SharedHelper? = null
    private var editTextFontSize : EditText? = null
    private var buttonApply : Button? = null
    private var buttonCancel : Button? = null
    private var spinnerColor : Spinner? = null
    private var colorArray : Array<String> = arrayOf("Black","White","Red","Blue","Yellow","Green")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        mContext = applicationContext
        sharedHelper = SharedHelper(mContext)

        bindview()
        Log.d("aaa", "onCreate: aaa")
    }

    override fun onStart() {
        super.onStart()
        val data: Map<String, String?> = sharedHelper!!.readColorAndSize()

        val fontcolor = data["fontcolor"]
        val fontsize = data["fontsize"]
        if ((fontcolor == null) || (fontsize == null))
        {

        }
        else
        {

            val position = colorArray.indexOf(fontcolor)
            spinnerColor!!.setSelection(position)
            editTextFontSize!!.setText(fontsize)
        }


    }

    private fun bindview()
    {
        editTextFontSize = findViewById(R.id.editTextFontSize)
        spinnerColor = findViewById(R.id.spinnerColor)
        buttonApply = findViewById(R.id.buttonApply)
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonCancel!!.setOnClickListener(View.OnClickListener {
            finish()
        })
        buttonApply!!.setOnClickListener(View.OnClickListener {

            try {
                Log.d("aaa", "bindview: " + editTextFontSize!!.text.toString())
                var fontsize = editTextFontSize!!.text.toString()
                var fontcolor = spinnerColor!!.selectedItem.toString()
                sharedHelper!!.saveColorAndSize(fontcolor,fontsize)
                Log.d("aaa", "bindview: fontsize = " + fontsize)
                Log.d("aaa", "bindview: font color = " + spinnerColor!!.selectedItem.toString())
                finish()
            } catch (e: Exception) {

            }

        })

    }
}