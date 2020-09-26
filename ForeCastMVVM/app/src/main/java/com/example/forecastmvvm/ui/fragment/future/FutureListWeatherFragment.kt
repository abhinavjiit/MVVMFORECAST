package com.example.forecastmvvm.ui.fragment.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.forecastmvvm.R
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.response.Daily
import com.example.forecastmvvm.ui.adapter.FutureListWeatherAdapter
import com.example.forecastmvvm.ui.fragment.BaseFragment

class FutureListWeatherFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            FutureListWeatherFragment()
    }

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: FutureListWeatherViewModel
    private var futListWet: List<Daily>? = null

    private val adapter: FutureListWeatherAdapter by lazy {
        FutureListWeatherAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.future_list_fragment, container, false)
        ButterKnife.bind(this, view)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter
        adapter.setListData(futListWet)
        adapter.notifyDataSetChanged()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FutureListWeatherViewModel::class.java)

        viewModel.setMoviesIntoUi()?.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    //  showProgressDialog("please wait")
                }
                Resource.Status.SUCCESS -> {
                    adapter.setListData(it.data)
                    adapter.notifyDataSetChanged()
                    // removeProgressDialog()
                }
                Resource.Status.ERROR -> {
                    //   removeProgressDialog()
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }


            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
