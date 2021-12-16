package com.ecampus.bem.data.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient.BASE_URL
import com.ecampus.bem.data.model.QuesStts
import com.ecampus.bem.databinding.ItemCalonBinding
import com.ecampus.bem.databinding.ItemQuesBinding
import com.ecampus.bem.ui.home.fm.CalonFragment
import com.ecampus.bem.ui.home.fm.ListQuesFragment

class ListQuesAdapter(
    private var lagus: MutableList<QuesStts>,
    private var context: Context,
    private var supportFragmentManager: FragmentManager,
) : RecyclerView.Adapter<ListQuesAdapter.PageHolder>() {

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemQuesBinding.bind(view)

    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        with(holder) {
            Glide.with(context).load(BASE_URL + "assets/image/" + lagus[position].image)
                .into(binding.ivCalon)
            binding.edNama.text = "${lagus[position].nama_presiden} (${lagus[position].nim_presiden})"
            binding.edWakil.text = "${lagus[position].nama_wakil} (${lagus[position].nim_wakil})"
            binding.edUrutan.text = lagus[position].urutan
            if ((position + 1) % 2 != 0)
                binding.llImage.layoutParams.height = 600
            binding.card.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("id_paslon", lagus[position].id_paslon)
                bundle.putString("stts", "true")

                val fragmen = ListQuesFragment.newInstance()
                fragmen.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragmen).commit()
            }
        }
    }

    fun setLagu(r: List<QuesStts>) {
        lagus.clear()
        lagus.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(LayoutInflater.from(context).inflate(R.layout.item_ques, parent, false))
    }

    override fun getItemCount() = lagus.size
}