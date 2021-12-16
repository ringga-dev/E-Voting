package com.ecampus.bem.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ecampus.bem.R
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.databinding.AuthActivityBinding
import com.ecampus.bem.ui.auth.fm.LoginFragment
import com.ecampus.bem.ui.home.HomeActivity
import com.ecampus.bem.ui.home.fm.HasilFragment

class AuthActivity : AppCompatActivity() {
lateinit var binding: AuthActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        if (SharedPrefManager.getInstance(this)!!.isLoggedIn) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}