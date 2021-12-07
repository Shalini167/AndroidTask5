package com.example.task5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button=findViewById<Button>(R.id.button)
        val inputauthor=findViewById<TextInputLayout>(R.id.authorinput).editText?.text
        val inputcountry=findViewById<TextInputLayout>(R.id.countryinput).editText?.text
        val displayview=findViewById<TextView>(R.id.displaytext)
        button.setOnClickListener(){
            var count=0
            val myApplication = application as MyApplication
            val httpApiService = myApplication.httpApiService


            CoroutineScope(Dispatchers.IO).launch{
                val decodedResult=httpApiService.getbooks()

                withContext(Dispatchers.Main){
                    val myStringBuilder = StringBuilder()
                    for (myData in decodedResult) {
                        if(count<3) {
                            if (inputauthor.toString() == myData.author && inputcountry.toString() == myData.country) {
                                myStringBuilder.append("Result : "+myData.title)
                                myStringBuilder.append("\n")
                                count++
                            }

                        }

                    }

                    displayview.text = "Results : "+count+"\n$myStringBuilder"
                }
            }
        }
    }
}