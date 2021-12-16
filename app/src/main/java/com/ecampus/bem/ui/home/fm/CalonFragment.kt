package com.ecampus.bem.ui.home.fm

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.databinding.FragmentCalonBinding
import com.ecampus.bem.ui.auth.AuthViewModel


class CalonFragment : Fragment() {
    companion object {
        fun newInstance() = CalonFragment()
    }
    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentCalonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalonBinding.inflate(inflater, container, false)
        return   binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel

        val bundle =this.arguments
        val nama_presiden = bundle?.getString("nama_presiden")
        val nim_presiden = bundle?.getString("nim_presiden")
        val nama_wakil = bundle?.getString("nama_wakil")
        val nim_wakil = bundle?.getString("nim_wakil")
        val stts = bundle?.getString("stts")
        val urutan = bundle?.getString("urutan")
        val image = bundle?.getString("image")
        val visi = Html.fromHtml(bundle?.getString("visi"))
        val misi = Html.fromHtml(bundle?.getString("misi"))
        binding.misi.text =misi
        binding.tvPresiden.text ="${nama_presiden} (${nim_presiden})"
        binding.tvWakil.text ="${nama_wakil} (${nim_wakil})"
        Glide.with(requireContext()).load(image)
            .into( binding.image)
        binding.visi.text =visi
        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }
    }

}