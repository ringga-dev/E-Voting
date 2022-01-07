package com.ecampus.bem.ui.home.fm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.data.model.PaslonRespon
import com.ecampus.bem.databinding.FragmentHomeBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import com.ecampus.bem.ui.upload.UploadFileActivity
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
                        binding.uploadFile.visibility = View.VISIBLE
                        binding.hasil.visibility  = View.VISIBLE
                    } else {
                        binding.btnDaftar.visibility = View.GONE
                        binding.uploadFile.visibility = View.GONE
                        binding.hasil.visibility  = View.GONE
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

        binding.uploadFile.setOnClickListener{
            RetrofitClient.instance.getDataPaslon(data.nim!!)
                .enqueue(object : Callback<BaseRespon<PaslonRespon>> {
                    override fun onResponse(
                        call: Call<BaseRespon<PaslonRespon>>,
                        response: Response<BaseRespon<PaslonRespon>>,
                    ) {
                        val res = response.body()?.data
                        val i = Intent(requireContext(), UploadFileActivity::class.java)
                        i.putExtra("id", res?.id)
                        startActivity(i)
                    }

                    override fun onFailure(call: Call<BaseRespon<PaslonRespon>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                })
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
                        binding.hasil.visibility  = View.VISIBLE
                    } else {
                        binding.btnInputSuara.visibility = View.GONE
                        binding.hasil.visibility  = View.GONE
                    }
                }

                override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }


}