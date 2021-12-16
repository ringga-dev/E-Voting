package com.ecampus.bem.ui.home.fm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecampus.bem.R
import com.ecampus.bem.data.adapter.ListQuesAdapter
import com.ecampus.bem.data.adapter.PaslonAdapter
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.QuesStts
import com.ecampus.bem.databinding.FragmentQuesSttsBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuesSttsFragment : Fragment() {
    companion object {
        fun newInstance() = QuesSttsFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentQuesSttsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQuesSttsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }
        setupRecler()
        RetrofitClient.instance.getQuesStts().enqueue(
            object :Callback<BaseRespon<List<QuesStts>>>{
                override fun onResponse(
                    call: Call<BaseRespon<List<QuesStts>>>,
                    response: Response<BaseRespon<List<QuesStts>>>,
                ) {
                    binding.rvQuesStts.adapter?.let { adapter ->
                        if (adapter is ListQuesAdapter) {
                            adapter.setLagu(response.body()?.data!!)
                        }
                    }
                }

                override fun onFailure(call: Call<BaseRespon<List<QuesStts>>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun setupRecler() {

        binding.rvQuesStts.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = ListQuesAdapter(mutableListOf(), requireContext(), requireFragmentManager())

        }
    }
}