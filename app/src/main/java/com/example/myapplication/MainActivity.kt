package com.example.myapplication

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id"
    private val notificationId = 101

    val FINE_LOCATION_RQ = 101
    val CAMERA_RQ = 102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Timber.e( "i2n CALISIYO")
        createNotificationChannel()

        val btnNoti = findViewById(R.id.btnNoti) as Button
        btnNoti.setOnClickListener {
            sendNotification()
        }
        buttonTaps()
    }
    private fun buttonTaps(){
    val btnCamera = findViewById(R.id.btnCamera) as Button
        btnCamera.setOnClickListener{
            checkForPermissions(android.Manifest.permission.CAMERA,"camera",CAMERA_RQ)
        }
        val btnLocation = findViewById(R.id.btnLocation) as Button
        btnLocation.setOnClickListener{
            checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,"location",FINE_LOCATION_RQ)
        }

    }
    private fun createNotificationChannel() {
        // Timber.e(       "notification CALISIYO")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.ic_launcher_foreground
        )
        val bitmapLargeIcon = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.ic_launcher_foreground
        )


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("my notifi")
            .setContentText("my fejges fey ")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText("much longer text that lds"))
            // .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(
                        applicationContext,
                        "$name permission granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    requestCode
                )
                else-> ActivityCompat.requestPermissions(this,arrayOf(permission),requestCode)
            }

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
      fun innerCheck(name: String){
          if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
              Toast.makeText(applicationContext,"$name permission refused",Toast.LENGTH_SHORT).show()
          }else{
              Toast.makeText(applicationContext,"$name permission granted",Toast.LENGTH_SHORT).show()
          }
      }
        when(requestCode){
            FINE_LOCATION_RQ -> innerCheck("location")
            CAMERA_RQ -> innerCheck("camera")
        }
    }
    private fun showDialog(permission: String,name:String,requestCode:Int){
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access your $name is requred to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){dialog,which->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}