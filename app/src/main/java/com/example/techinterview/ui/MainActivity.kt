package com.example.techinterview.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.techinterview.R
import com.example.techinterview.databinding.ActivityMainBinding
import com.example.techinterview.ui.adapter.TvShowsAdapter
import com.example.techinterview.util.observeEventLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    private val adapter by lazy {
        TvShowsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mainRecycler.adapter = adapter
        binding.mainSwipeRefresh.setOnRefreshListener {
            viewModel.load()
        }

        with(binding.mainSorting){
            val popupMenu = PopupMenu(this@MainActivity, this, Gravity.BOTTOM)
            popupMenu.menu.add(R.string.sorting_start_time).setOnMenuItemClickListener {
                adapter.setOrder(TvShowsAdapter.TVShowsComparator.Earliest)
                setText(R.string.sorting_start_time)
                true
            }
            popupMenu.menu.add(R.string.sorting_end_time).setOnMenuItemClickListener {
                adapter.setOrder(TvShowsAdapter.TVShowsComparator.Latest)
                setText(R.string.sorting_end_time)
                true
            }
            setOnClickListener {
                popupMenu.show()
            }
        }

        viewModel.load()
        binding.mainSwipeRefresh.isRefreshing = true

        observeEventLiveData(viewModel.uiLiveData, this::handleUiEvent)
    }

    private fun handleUiEvent(event: MainUiEvent){
        binding.mainSwipeRefresh.isRefreshing = false
        when(event){
            is MainUiEvent.ErrorEvent -> {
                Toast.makeText(this, event.errorMessage, Toast.LENGTH_SHORT).show()
            }
            is MainUiEvent.TvShowsListEvent -> {
                adapter.setList(event.tvShows)
            }
        }
    }
}