package com.ecampus.bem.ui.auth.fm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.databinding.FragmentRegisterBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }


    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.container, LoginFragment()).commit()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.btEmail.text.toString().trim()
            val name = binding.btName.text.toString().trim()
            val nim = binding.btNim.text.toString().trim()
            val phone = binding.btNoPhone.text.toString().trim()
            val pass = binding.btPass.text.toString().trim()


            if (email.isEmpty()) {
                binding.btEmail.error = "Email required"
                binding.btEmail.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()) {
                binding.btName.error = "Email required"
                binding.btName.requestFocus()
                return@setOnClickListener
            }
            if (nim.isEmpty()) {
                binding.btNim.error = "Email required"
                binding.btNim.requestFocus()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                binding.btNoPhone.error = "Email required"
                binding.btNoPhone.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                binding.btPass.error = "Email required"
                binding.btPass.requestFocus()
                return@setOnClickListener
            }

            registe(email, name, nim, phone, pass)
        }
    }

    private fun registe(email: String, name: String, nim: String, phone: String, pass: String) {
        RetrofitClient.instance.register(name, nim, email, phone, "", pass)
            .enqueue(object : Callback<BaseResponData> {
                override fun onResponse(
                    call: Call<BaseResponData>,
                    response: Response<BaseResponData>,
                ) {
                    if (response.body()?.stts == true) {
                        requireFragmentManager().beginTransaction()
                            .replace(R.id.container, LoginFragment()).commit()
                        activity?.finish()
                    }
                    Toast.makeText(requireContext(), response.body()?.msg, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                    Toast.makeText(requireContext(),   t.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}






