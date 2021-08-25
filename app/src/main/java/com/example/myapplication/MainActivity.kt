package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.myapplication.service.AlarmService
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var alarmService: AlarmService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmService = AlarmService(this)

        setExact.setOnClickListener {
            setAlarm { alarmService.setExactAlarm(it) }
        }

        setRepetitive.setOnClickListener { setAlarm { alarmService.setRepetitiveAlarm(it) } }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        val timer = object: CountDownTimer(20000,10000){
            override fun onTick(millisUntilFinished: Long) {
                Calendar.getInstance().apply {
                    this.set(Calendar.SECOND, 0)
                    this.set(Calendar.MILLISECOND, 0)
                    this.get(Calendar.HOUR)
                    this.get(Calendar.MINUTE)
                    callback(this.timeInMillis)

                }
            }

            override fun onFinish() {
              setAlarm (callback)
            }
        }
        timer.start()
        }

}