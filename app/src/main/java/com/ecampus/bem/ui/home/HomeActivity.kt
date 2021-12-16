package com.ecampus.bem.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ecampus.bem.R
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.databinding.ActivityHomeBinding
import com.ecampus.bem.ui.auth.AuthActivity
import com.ecampus.bem.ui.home.fm.HasilFragment
import com.ecampus.bem.ui.home.fm.HomeFragment
import com.ecampus.bem.ui.home.fm.ListCalonFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var permissionsRequired = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val PERMISSION_CALLBACK_CONSTANT = 100
    private val REQUEST_PERMISSION_SETTING = 101
    private var permissionStatus: SharedPreferences? = null
    private var sentToSettings = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        permissionStatus = getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)
        requestPermission()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HomeFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        if (!SharedPrefManager.getInstance(this)!!.isLoggedIn) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[0]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[1]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[2]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[3]
            ) != PackageManager.PERMISSION_GRANTED

        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
            ) {
                //Show Information about why you need the permission
                getAlertDialog()
            } else if (permissionStatus!!.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Need Multiple Permissions")
                builder.setMessage("This app needs permissions.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    Toast.makeText(
                        applicationContext,
                        "Go to Permissions to Grant ",
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(
                    this,
                    permissionsRequired,
                    PERMISSION_CALLBACK_CONSTANT
                )
            }


            val editor = permissionStatus!!.edit()
            editor.putBoolean(permissionsRequired[0], true)
            editor.apply()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }

            if (allgranted) {
//                Toast.makeText(applicationContext, "Allowed All Permissions", Toast.LENGTH_LONG)
//                    .show()

//                showAlertSuccess()

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
            ) {
                getAlertDialog()
            } else {
                Toast.makeText(applicationContext, "Unable to get Permission", Toast.LENGTH_LONG)
                    .show()


            }
        }
    }

//    private fun showAlertSuccess() {
//        val inflate = layoutInflater
//        val infla_view = inflate.inflate(R.layout.custom_alert_ok, null)
//        val cencel = infla_view.findViewById<ImageView>(R.id.btn_close)
//        val alertDialog = AlertDialog.Builder(this)
//        alertDialog.setView(infla_view)
//        alertDialog.setCancelable(false)
//
//        val dialog = alertDialog.create()
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        cencel.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
//    }

    private fun getAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Multiple Permissions")
        builder.setMessage("This app needs permissions.")
        builder.setPositiveButton("Grant") { dialog, which ->
            dialog.cancel()
            ActivityCompat.requestPermissions(
                this,
                permissionsRequired,
                PERMISSION_CALLBACK_CONSTANT
            )
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permissionsRequired[0]
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //Got Permission
//                Toast.makeText(applicationContext, "Allowed All Permissions", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

}