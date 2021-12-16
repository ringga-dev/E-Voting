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
import com.ecampus.bem.data.api.RetrofitClient.BASE_URL
import com.ecampus.bem.data.model.PaslonRespon
import com.ecampus.bem.databinding.FragmentCalonBinding
import com.ecampus.bem.databinding.ItemCalonBinding
import com.ecampus.bem.ui.home.HomeActivity
import com.ecampus.bem.ui.home.fm.CalonFragment


class PaslonAdapter(
    private var lagus: MutableList<PaslonRespon>,
    private var context: Context,
    private var supportFragmentManager: FragmentManager,
) : RecyclerView.Adapter<PaslonAdapter.PageHolder>() {

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCalonBinding.bind(view)

    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        with(holder) {
            Glide.with(context).load(BASE_URL + "assets/image/" + lagus[position].image)
                .into(binding.ivCalon)
            binding.edNama.text = "${lagus[position].nama_presiden} (${lagus[position].nim_presiden})"
            binding.edWakil.text = "${lagus[position].nama_wakil} (${lagus[position].nim_wakil})"
            if ((position + 1) % 2 != 0)
                binding.llImage.layoutParams.height = 600

            binding.card.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("nama_presiden", lagus[position].nama_presiden)
                bundle.putString("nim_presiden", lagus[position].nim_presiden)
                bundle.putString("nama_wakil", lagus[position].nama_wakil)
                bundle.putString("nim_wakil", lagus[position].nim_wakil)
                bundle.putString("stts", lagus[position].stts)
                bundle.putString("urutan", lagus[position].urutan)
                bundle.putString("image", BASE_URL + "assets/image/" + lagus[position].image)
                bundle.putString("visi", lagus[position].visi)
                bundle.putString("misi", lagus[position].misi)

                val fragmen = CalonFragment.newInstance()
                fragmen.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragmen).commit()
            }
        }
    }

    fun setLagu(r: List<PaslonRespon>) {
        lagus.clear()
        lagus.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(LayoutInflater.from(context).inflate(R.layout.item_calon, parent, false))
    }

    override fun getItemCount() = lagus.size
}