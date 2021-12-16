package com.ecampus.bem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.ecampus.bem.ui.auth.AuthActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        val time = findViewById<TextView>(R.id.tv_date)
        time.text = "Made with ❤️ from Indonesia \n ${currentDate}"

        Handler().postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 4500)
    }
}