package com.example.androidpracticumcustomview

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidpracticumcustomview.ui.theme.CustomContainer
import com.example.androidpracticumcustomview.ui.theme.MainScreen

/*
Задание:
Реализуйте необходимые компоненты.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Раскомментируйте нужный вариант
         */
        startXmlPracticum() // «традиционный» android (XML)
//          setContent { // Jetpack Compose
//             MainScreen()
    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        setContentView(customContainer)

        val firstView = TextView(this).apply {
            text = context.getString(R.string.first_child)
            textSize = 24f
            setTextColor(Color.BLACK)
        }

        val secondView = TextView(this).apply {
            text = context.getString(R.string.second_child)
            textSize = 24f
            setTextColor(Color.BLACK)
        }

        customContainer.addView(firstView)

        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)
    }
}