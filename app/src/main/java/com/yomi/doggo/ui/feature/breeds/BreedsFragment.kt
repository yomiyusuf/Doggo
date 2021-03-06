package com.yomi.doggo.ui.feature.breeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.yomi.doggo.R
import com.yomi.doggo.ui.common.ItemOffsetDecoration
import com.yomi.doggo.ui.common.ProgressFragment
import com.yomi.doggo.util.show
import kotlinx.android.synthetic.main.fragment_breeds.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BreedsFragment : ProgressFragment() {

    private val viewModel by viewModel<BreedsViewModel>()
    private val listAdapter =
        BreedsAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_breeds, container, false)
        if (savedInstanceState == null) viewModel.getBreeds()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        registerObservers()
    }

    private fun initRecyclerView() {
        val decoration = ItemOffsetDecoration( requireContext(), R.dimen.base_dimen_2)

        rv_breeds.apply {
            addItemDecoration(decoration)
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
    }

    private fun registerObservers() {
        viewModel.breeds.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_breeds.show()
                listAdapter.updateData(it)
            }
        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer { handleErrorState(it) })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { handleLoadingState(it) })
    }

    override fun onRetry() {
        viewModel.getBreeds()
    }

    override fun onLoadingStateChange(isLoading: Boolean) { }
}