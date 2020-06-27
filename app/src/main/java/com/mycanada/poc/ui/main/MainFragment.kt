package com.mycanada.poc.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.github.binding.FragmentDataBindingComponent
import com.mycanada.poc.R
import com.mycanada.poc.databinding.MainFragmentBinding
import com.mycanada.poc.di.ViewModelProviderFactory
import com.mycanada.poc.model.InformationChildModel
import com.mycanada.poc.repository.repoutils.Status
import com.mycanada.poc.utils.autoCleared
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var viewModel: MainViewModel

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<MainFragmentBinding>()

    lateinit var infoListAdapter: InfoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate( inflater,
                    R.layout.main_fragment,
                    container,
                    false,
                    dataBindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.let { ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java) }!!
        binding.lifecycleOwner = viewLifecycleOwner
        initView()
    }

    private fun initView() {
        // init  Pull To Refresh View
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary))
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE)
        swipeRefreshLayout.setOnRefreshListener(this)

        initRecyclerView()
    }

    override fun onRefresh() {
        clearAdapter()
        fetchInfoData()
    }

    private fun initRecyclerView() {
        infoListAdapter = InfoListAdapter()
        binding.rcvInformation.adapter = infoListAdapter
        binding.status = viewModel.informationModel.value!!.status
        fetchInfoData()
    }

    private fun fetchInfoData() {
        viewModel.informationModel.observe(viewLifecycleOwner, Observer {
            if (it.status != Status.LOADING) {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.status = it.status
                        if (it.data != null && !it.data.rows.isNullOrEmpty()) {
                            // Remove the object with null title
                            infoListAdapter.submitList(it.data!!.rows!!.filter { it.title != null })
                            setTitle(it.data!!.title)
                            handleSwipeRefresh()
                        } else {
                            showEmptyView()
                        }
                    }
                    Status.ERROR -> {
                        binding.status = it.status
                        showEmptyView()
                    }
                }
            }
        })
    }

    private fun showEmptyView() {
        binding.rcvInformation.visibility = View.GONE
        binding.txtNoData.visibility = View.VISIBLE
    }

    private fun clearAdapter() {
        infoListAdapter.submitList(listOf<InformationChildModel>())
    }

    private fun setTitle(title: String) {
        (requireActivity() as MainActivity).supportActionBar!!.title = title
    }

    fun handleSwipeRefresh() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }
}