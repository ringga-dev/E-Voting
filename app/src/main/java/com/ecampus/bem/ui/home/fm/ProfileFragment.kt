package com.ecampus.bem.ui.home.fm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.db_lokal.SharedPrefManager
import com.ecampus.bem.databinding.FragmentProfileBinding
import com.ecampus.bem.ui.auth.AuthActivity
import com.ecampus.bem.ui.auth.AuthViewModel


class ProfileFragment : Fragment() {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
        val data = SharedPrefManager.getInstance(requireContext())!!.user
        binding.email.text = data.email
        binding.prodi.text = data.prodi
        binding.tvPhone.text = data.no_phone
        binding.nim.text = data.nim
        binding.tvName.text = data.name
        Glide.with(requireContext()).load(RetrofitClient.BASE_URL + "assets/" + data.image)
            .into(binding.image)
        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }

        binding.logOut.setOnClickListener {
            SharedPrefManager.getInstance(requireContext())!!.clear()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            activity?.finish()

        }

    }

}