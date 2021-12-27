package com.itsrdb.handsignhelper

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class TranslatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        val myString = intent.getStringExtra("myString")
        val tv1 = findViewById<TextView>(R.id.tv_1)
        val tv2 = findViewById<TextView>(R.id.tv_2)
        val btn2 = findViewById<Button>(R.id.button2)
        btn2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.darker_grey))
        //tv1.setText(" ")
        var len = myString!!.length

        for (i in 0..len-1) {
//            Handler(Looper.getMainLooper()).postDelayed({
//                tv1.text = element.toString()
//                Log.e("string", element.toString())
//                // Your Code
//            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed({
                if(myString[i].isLetterOrDigit()){
                    tv1.setText(myString[i].toString())
                    //tv1.text = myString[i].toString()
                    tv2.text = myString[i].toString().uppercase()
                    YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(tv1)
                }else{
                    tv1.setText("_")
                    //tv1.text = "_"
                    tv2.text = myString[i].toString()
                }
                if(i.equals(len-1)){
                    Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show()
                }
            }, (i * 1000).toLong())
            //tv1.text = myString.toString()
//            val handler = Handler()
//            handler.postDelayed({
//                // do something after 1000ms
//            }, 500)
            //Thread.delay(500)
        }

        btn2.setOnClickListener {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}