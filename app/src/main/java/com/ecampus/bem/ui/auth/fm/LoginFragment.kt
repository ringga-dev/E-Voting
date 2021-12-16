package com.ecampus.bem.ui.auth.fm

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.UserRespon
import com.ecampus.bem.databinding.FragmentLoginBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import com.ecampus.bem.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.container, RegisterFragment()).commit()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPass.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Email required"
                binding.etEmail.requestFocus()
//                loading.visibility = View.GONE
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                binding.etPass.error = "Password required"
                binding.etPass.requestFocus()
//                loading.visibility = View.GONE
                return@setOnClickListener
            }
            login(email, pass)


        }
    }

    private fun login(email: String, pass: String) {
        RetrofitClient.instance.login(email, pass)
            .enqueue(object : Callback<BaseRespon<UserRespon>> {
                override fun onResponse(
                    call: Call<BaseRespon<UserRespon>>,
                    response: Response<BaseRespon<UserRespon>>,
                ) {
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity?.finish()
                    startActivity(intent)

                    SharedPrefManager.getInstance(requireContext())!!
                        .saveUser(response.body()?.data!!)
                }

                override fun onFailure(call: Call<BaseRespon<UserRespon>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

}