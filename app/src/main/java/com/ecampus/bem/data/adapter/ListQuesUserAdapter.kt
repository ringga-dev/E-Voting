package com.ecampus.bem.data.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient.BASE_URL
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.data.model.Ques
import com.ecampus.bem.data.model.QuesStts
import com.ecampus.bem.databinding.ItemCalonBinding
import com.ecampus.bem.databinding.ItemQuesBinding
import com.ecampus.bem.databinding.ItemQuesUserBinding
import com.ecampus.bem.ui.home.fm.CalonFragment
import com.ecampus.bem.ui.home.fm.ListQuesFragment

class ListQuesUserAdapter(
    private var lagus: MutableList<Ques>,
    private var context: Context,
    private var supportFragmentManager: FragmentManager,
) : RecyclerView.Adapter<ListQuesUserAdapter.PageHolder>() {

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemQuesUserBinding.bind(view)

    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        with(holder) {
            binding.edNama.text = "Nama : ${lagus[position].name} "
            binding.nim.text = "Nim : ${lagus[position].nim} "
            binding.prodi.text = "Prodi : ${lagus[position].prodi}"
            binding.ques.text = lagus[position].soal
            val data = SharedPrefManager.getInstance(context)!!.user
            if (data.id == lagus[position].id_user.toInt()){
                binding.card.setCardBackgroundColor(Color.parseColor("#03fc20"))
            }
        }
    }

    fun setLagu(r: List<Ques>) {
        lagus.clear()
        lagus.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(LayoutInflater.from(context).inflate(R.layout.item_ques_user, parent, false))
    }

    override fun getItemCount() = lagus.size
}