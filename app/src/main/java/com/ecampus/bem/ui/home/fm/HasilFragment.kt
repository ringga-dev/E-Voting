package com.ecampus.bem.ui.home.fm

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ecampus.bem.R
import com.ecampus.bem.data.api.RetrofitClient
import com.ecampus.bem.data.model.BaseRespon
import com.ecampus.bem.data.model.Voting
import com.ecampus.bem.databinding.FragmentHasilBinding
import com.ecampus.bem.ui.auth.AuthViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

class HasilFragment : Fragment() {

    private val dataEntries = ArrayList<PieEntry>()
    private var colors: ArrayList<Int> = ArrayList()
    private val mdColors = arrayOf(
        "#F44336",
        "#FFCDD2",
        "#EF9A9A",
        "#E57373",
        "#EF5350",
        "#F44336",
        "#E53935",
        "#D32F2F",
        "#C62828",
        "#B71C1C",
        "#FF8A80",
        "#FF5252",
        "#FF1744",
        "#D50000",
        "#E91E63",
        "#FCE4EC",
        "#F8BBD0",
        "#F48FB1",
        "#F06292",
        "#EC407A",
        "#E91E63",
        "#D81B60",
        "#C2185B",
        "#AD1457",
        "#880E4F",
        "#FF80AB",
        "#FF4081",
    )

    companion object {
        fun newInstance() = HasilFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentHasilBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHasilBinding.inflate(inflater, container, false)
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.lineChart.setUsePercentValues(true)
        binding.lineChart.description.text = ""
        //hollow pie chart
        binding.lineChart.isDrawHoleEnabled = false
        binding.lineChart.setTouchEnabled(false)
        binding.lineChart.setDrawEntryLabels(false)
        //adding padding
        binding.lineChart.setExtraOffsets(20f, 0f, 20f, 20f)
        binding.lineChart.setUsePercentValues(true)
        binding.lineChart.isRotationEnabled = false
        binding.lineChart.setDrawEntryLabels(false)
        binding.lineChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        binding.lineChart.legend.isWordWrapEnabled = true


        RetrofitClient.instance.getVoting().enqueue(
            object : Callback<BaseRespon<List<Voting>>> {
                override fun onResponse(
                    call: Call<BaseRespon<List<Voting>>>,
                    response: Response<BaseRespon<List<Voting>>>,
                ) {
                    response.body()?.data?.forEachIndexed { index, element ->
                        dataEntries.add(PieEntry(element.suara.toFloat(), "${element.nama_presiden}:${element.nama_wakil} "))
                        colors.add(Color.parseColor(mdColors[index]))
                    }

                    set_view()
                }

                override fun onFailure(call: Call<BaseRespon<List<Voting>>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            }
        )


    }

    private fun set_view() {
        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        binding.lineChart.data = data
        data.setValueTextSize(25f)
        binding.lineChart.setExtraOffsets(5f, 10f, 5f, 5f)
        binding.lineChart.animateY(2000, Easing.EaseInOutQuad)
        binding.lineChart.setCenterTextSize(25f)

        //create hole in center
        binding.lineChart.holeRadius = 58f
        binding.lineChart.transparentCircleRadius = 61f
        binding.lineChart.isDrawHoleEnabled = true
        binding.lineChart.setHoleColor(Color.WHITE)


        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance()).commit()
        }
    }


}