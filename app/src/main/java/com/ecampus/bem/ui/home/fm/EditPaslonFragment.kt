package com.ecampus.bem.ui.home.fm

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.data.model.PaslonRespon
import com.ecampus.bem.databinding.FragmentEditPaslonBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditPaslonFragment : Fragment() {
    companion object {
        fun newInstance() = EditPaslonFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentEditPaslonBinding? = null
    private val binding get() = _binding!!
    private lateinit var id_paslon: String
    private var data_id: PaslonRespon? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditPaslonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
        binding.tvPertanyaan.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id_paslon", id_paslon)
            bundle.putString("stts", "false")

            val fragmen = ListQuesFragment.newInstance()
            fragmen.arguments = bundle
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmen).commit()
        }

        var data = SharedPrefManager.getInstance(requireContext())!!.user
        RetrofitClient.instance.getDataPaslon(data.nim!!)
            .enqueue(object : Callback<BaseRespon<PaslonRespon>> {
                override fun onResponse(
                    call: Call<BaseRespon<PaslonRespon>>,
                    response: Response<BaseRespon<PaslonRespon>>,
                ) {
                    val res = response.body()?.data
                    data_id = res

                    binding.tvPresiden.text = "${res?.nama_presiden} (${res?.nim_presiden})"
                    binding.tvWakil.text = "Wakil : ${res?.nama_wakil} (${res?.nim_wakil})"
                    binding.tvStts.text = "Status : ${res?.stts}"
                    binding.tvUrutan.text = "Nomor : ${res?.urutan}"
                    binding.visi.text = Html.fromHtml("${res?.visi}")
                    binding.misi.text = Html.fromHtml("${res?.misi}")
                    id_paslon = res?.id!!
                    Glide.with(requireContext())
                        .load(RetrofitClient.BASE_URL + "assets/image/" + res.image)
                        .into(binding.image)
                }

                override fun onFailure(call: Call<BaseRespon<PaslonRespon>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
        binding.editVisi.setOnClickListener {
            showCustomAlertVisi("edit_visi")
        }
        binding.editMisi.setOnClickListener {
            showCustomAlertVisi("edit_misis")
        }


        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }
    }

    private fun showCustomAlertVisi(stts: String) {
        val data = SharedPrefManager.getInstance(requireContext())!!.user
        val inflate = layoutInflater
        val infla_view = inflate.inflate(R.layout.custom_alert_edit_visi, null)
        val textQues = infla_view.findViewById<EditText>(R.id.visi)
        val simpan = infla_view.findViewById<Button>(R.id.simpan)
        val cencel = infla_view.findViewById<Button>(R.id.close)


        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(infla_view)
        alertDialog.setCancelable(false)

        val dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        simpan.setOnClickListener {
            val text = textQues.text.toString().trim()



        }
        cencel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


}