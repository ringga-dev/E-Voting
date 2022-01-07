package com.ecampus.bem.data.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
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
import com.ecampus.bem.data.model.GetImagePaslon
import com.ecampus.bem.data.model.PaslonRespon
import com.ecampus.bem.databinding.FragmentCalonBinding
import com.ecampus.bem.databinding.ItemCalonBinding
import com.ecampus.bem.databinding.ItemImageBinding
import com.ecampus.bem.databinding.ItemPemilihanBinding
import com.ecampus.bem.ui.home.HomeActivity
import com.ecampus.bem.ui.home.fm.CalonFragment
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImagePaslonAdapter(
    private var lagus: MutableList<GetImagePaslon>,
    private var context: Context,
) : RecyclerView.Adapter<ImagePaslonAdapter.PageHolder>() {

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemImageBinding.bind(view)

    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        with(holder) {
            Glide.with(context).load(BASE_URL + "assets/file/" + lagus[position].file_name)
                .into(binding.ivImage)


        }
    }

    fun setLagu(r: List<GetImagePaslon>) {
        lagus.clear()
        lagus.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount() = lagus.size
}