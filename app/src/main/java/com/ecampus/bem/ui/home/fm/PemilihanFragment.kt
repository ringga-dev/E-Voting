package com.ecampus.bem.ui.home.fm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecampus.bem.R
import com.ecampus.bem.data.adapter.ListPaslonAdapter
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.PaslonRespon
import com.ecampus.bem.databinding.FragmentPemilihanBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PemilihanFragment : Fragment() {
    companion object {
        fun newInstance() = PemilihanFragment()
    }
    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentPemilihanBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPemilihanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
        setupRecler()
        RetrofitClient.instance.getPaslon().enqueue(object :
            Callback<BaseRespon<List<PaslonRespon>>> {
            override fun onResponse(
                call: Call<BaseRespon<List<PaslonRespon>>>,
                response: Response<BaseRespon<List<PaslonRespon>>>,
            ) {
                binding.rvSuara.adapter?.let { adapter ->
                    if (adapter is ListPaslonAdapter) {
                        adapter.setLagu(response.body()?.data!!)
                    }
                }
            }

            override fun onFailure(call: Call<BaseRespon<List<PaslonRespon>>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }
    }
    private fun setupRecler() {

        binding.rvSuara.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            adapter = ListPaslonAdapter(mutableListOf(), requireContext(), requireFragmentManager())

        }


    }

}