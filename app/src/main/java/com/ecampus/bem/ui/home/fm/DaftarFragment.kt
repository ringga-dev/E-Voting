package com.ecampus.bem.ui.home.fm

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
import com.ecampus.bem.databinding.FragmentDaftarBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarFragment : Fragment() {
    companion object {
        fun newInstance() = DaftarFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentDaftarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDaftarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel

        binding.btnRegister.setOnClickListener {
            val presiden = binding.btPresiden.text.toString().trim()
            val nimPresiden = binding.btNimPresiden.text.toString().trim()
            val wakil = binding.btWakil.text.toString().trim()
            val nimWakil = binding.btNimWakil.text.toString().trim()
            val visi = binding.btVisi.text.toString().trim()
            val misi = binding.btMisi.text.toString().trim()

            if (presiden.isEmpty()) {
                binding.btPresiden.error = "kosong"
                binding.btPresiden.requestFocus()
                return@setOnClickListener
            }
            if (nimPresiden.isEmpty()) {
                binding.btNimPresiden.error = "kosong"
                binding.btNimPresiden.requestFocus()
                return@setOnClickListener
            }
            if (wakil.isEmpty()) {
                binding.btWakil.error = "kosong"
                binding.btWakil.requestFocus()
                return@setOnClickListener
            }
            if (nimWakil.isEmpty()) {
                binding.btNimWakil.error = "kosong"
                binding.btNimWakil.requestFocus()
                return@setOnClickListener
            }
            if (visi.isEmpty()) {
                binding.btVisi.error = "kosong"
                binding.btVisi.requestFocus()
                return@setOnClickListener
            }
            if (misi.isEmpty()) {
                binding.btMisi.error = "kosong"
                binding.btMisi.requestFocus()
                return@setOnClickListener
            }

            daftar(presiden,nimPresiden, wakil, nimWakil,visi,misi)
        }
    }

    private fun daftar(presiden: String, nimPresiden: String, wakil: String, nimWakil: String, visi: String,misi: String) {
        RetrofitClient.instance.daftarPaslon(nimPresiden,presiden,nimWakil,wakil,visi,misi)
            .enqueue(object : Callback<BaseResponData> {
                override fun onResponse(
                    call: Call<BaseResponData>,
                    response: Response<BaseResponData>,
                ) {

                    if (response.body()?.stts == true) {
                        requireFragmentManager().beginTransaction()
                            .replace(R.id.container, HomeFragment()).commit()
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