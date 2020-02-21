package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // 1度だけ代入するものはvalを使う
    val handler = Handler()
    // 繰り返し代入するためvarを使う
    var timeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeText = findViewById(R.id.timerText) as TextView
        val startButton = findViewById(R.id.start) as Button
        val stopButton = findViewById(R.id.stop) as Button
        val resetButton = findViewById(R.id.reset) as Button

        // 1秒ごとに処理を実行
        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let {
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }
        startButton.setOnClickListener {
            handler.post(runnable)
        }

        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                timeText.text = it
            }
        }
    }
    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}

