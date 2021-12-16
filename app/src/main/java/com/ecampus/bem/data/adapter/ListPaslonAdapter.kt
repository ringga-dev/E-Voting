package com.ecampus.bem.data.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.api.RetrofitClient.BASE_URL
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.BaseResponData
import com.ecampus.bem.data.model.PaslonRespon
import com.ecampus.bem.databinding.FragmentCalonBinding
import com.ecampus.bem.databinding.ItemCalonBinding
import com.ecampus.bem.databinding.ItemPemilihanBinding
import com.ecampus.bem.ui.home.HomeActivity
import com.ecampus.bem.ui.home.fm.CalonFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListPaslonAdapter(
    private var lagus: MutableList<PaslonRespon>,
    private var context: Context,
    private var supportFragmentManager: FragmentManager,
) : RecyclerView.Adapter<ListPaslonAdapter.PageHolder>() {

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPemilihanBinding.bind(view)

    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        with(holder) {
            Glide.with(context).load(BASE_URL + "assets/image/" + lagus[position].image)
                .into(binding.imPaslon)
            binding.tvPresiden.text =
                "${lagus[position].nama_presiden} (${lagus[position].nim_presiden})"
            binding.tvWakil.text = "${lagus[position].nama_wakil}  (${lagus[position].nim_wakil})"
            binding.tvNomor.text = "Nomor : ${lagus[position].urutan}"

            binding.pilih.setOnClickListener {
                val data = SharedPrefManager.getInstance(context)!!.user
                RetrofitClient.instance.pemilihan(data.id.toString(), lagus[position].urutan)
                    .enqueue(object : Callback<BaseResponData> {
                        override fun onResponse(
                            call: Call<BaseResponData>,
                            response: Response<BaseResponData>,
                        ) {
                            Toast.makeText(context, response.body()?.msg, Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<BaseResponData>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        }

                    })
            }
        }
    }

    fun setLagu(r: List<PaslonRespon>) {
        lagus.clear()
        lagus.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_pemilihan, parent, false))
    }

    override fun getItemCount() = lagus.size
}