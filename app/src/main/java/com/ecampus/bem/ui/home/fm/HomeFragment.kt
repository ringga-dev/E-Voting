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
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.databinding.FragmentHomeBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
        val data = SharedPrefManager.getInstance(requireContext())!!.user
        sttsVoting()
        RetrofitClient.instance.getStts(data.nim!!)
            .enqueue(object : Callback<BaseResponData> {
                override fun onResponse(
                    call: Call<BaseResponData>,
                    response: Response<BaseResponData>,
                ) {
                    if (response.body()?.stts!!) {
                        binding.btnDaftar.visibility = View.VISIBLE
                    } else {
                        binding.btnDaftar.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })



        binding.btnDaftarCalon.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ListCalonFragment.newInstance()).commit()
        }

        binding.btnInputSuara.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, PemilihanFragment.newInstance()).commit()
        }

        binding.hasil.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HasilFragment.newInstance()).commit()
        }

        binding.btnDaftar.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, EditPaslonFragment.newInstance()).commit()
        }
        binding.profile.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment.newInstance()).commit()
        }
        binding.tanyaJawab.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, QuesSttsFragment.newInstance()).commit()
        }
    }

    private fun sttsVoting() {
        RetrofitClient.instance.votingAkses()
            .enqueue(object : Callback<BaseResponData> {
                override fun onResponse(
                    call: Call<BaseResponData>,
                    response: Response<BaseResponData>,
                ) {
                    if (response.body()?.stts == true) {
                        binding.btnInputSuara.visibility = View.VISIBLE
                    } else {
                        binding.btnInputSuara.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }


}