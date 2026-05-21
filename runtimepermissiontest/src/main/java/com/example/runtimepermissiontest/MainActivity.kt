package com.example.runtimepermissiontest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf


class MainActivity : AppCompatActivity() {
    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var makeCall = findViewById<Button>(R.id.makeCall)
        makeCall.setOnClickListener {
            /*try {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:10086")
                startActivity(intent)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }*/

            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CALL_PHONE), 1
                )
            } else {
                call()
            }
            val values = contentValuesOf("column1" to "text", "column2" to 1)
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun readContacts() {

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
        )?.apply {
            while (moveToNext()) {
                val displayName =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$displayName\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }
}
