package com.ecampus.bem.ui.home.fm

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecampus.bem.R
import com.ecampus.bem.data.adapter.ListQuesUserAdapter
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.data.model.Ques
import com.ecampus.bem.data.snackbar
import com.ecampus.bem.databinding.FragmentListQuesBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListQuesFragment : Fragment() {
    companion object {
        fun newInstance() = ListQuesFragment()
    }
    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentListQuesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListQuesBinding.inflate(inflater, container, false)
        return   binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
        val bundle =this.arguments
        val id_paslon = bundle?.getString("id_paslon")
        val stts = bundle?.getString("stts")
        if (stts== "false"){
            binding.addQues.visibility = View.GONE
        }

        binding.addQues.setOnClickListener {
            showCustomAlertQues(id_paslon!!)
        }
        setupRecler()
        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }


       viewLis(id_paslon!!)

        val handler = Handler()
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                viewLis(id_paslon)
                handler.postDelayed(this, 2000)
            }
        }
        handler.post(runnableCode)
    }

    private fun viewLis(id_paslon:String) {
        RetrofitClient.instance.getQues(id_paslon).enqueue(
            object :Callback<BaseRespon<List<Ques>>>{
                override fun onResponse(
                    call: Call<BaseRespon<List<Ques>>>,
                    response: Response<BaseRespon<List<Ques>>>,
                ) {
                    binding.rvQues.adapter?.let { adapter ->
                        if (adapter is ListQuesUserAdapter) {
                            adapter.setLagu(response.body()?.data!!)
                        }
                    }
                }

                override fun onFailure(call: Call<BaseRespon<List<Ques>>>, t: Throwable) {
                    view?.snackbar(t.message.toString())
                }
            }
        )
    }

    private fun setupRecler() {

        binding.rvQues.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            adapter = ListQuesUserAdapter(mutableListOf(), requireContext(), requireFragmentManager())

        }
    }

    private fun showCustomAlertQues(text : String) {
        val data = SharedPrefManager.getInstance(requireContext())!!.user
        val inflate = layoutInflater
        val infla_view = inflate.inflate(R.layout.custom_alert_add_ques, null)
        val textQues = infla_view.findViewById<EditText>(R.id.ques)
        val pulang = infla_view.findViewById<Button>(R.id.simpan)
        val cencel = infla_view.findViewById<Button>(R.id.close)




        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(infla_view)
        alertDialog.setCancelable(false)

        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pulang.setOnClickListener {

            RetrofitClient.instance.setQues(text, data.id.toString(), textQues.text.toString().trim())
                .enqueue(object :Callback<BaseResponData>{
                    override fun onResponse(
                        call: Call<BaseResponData>,
                        response: Response<BaseResponData>,
                    ) {
                        Toast.makeText(requireContext(), response.body()?.msg, Toast.LENGTH_SHORT).show()
                        viewLis(text)
                        dialog.dismiss()
                    }

                    override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }
                })



        }
        cencel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}