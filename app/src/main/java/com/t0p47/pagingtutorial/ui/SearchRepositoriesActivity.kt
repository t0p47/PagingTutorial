package com.t0p47.pagingtutorial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.pagingtutorial.databinding.ActivitySearchRepositoriesBinding
import com.t0p47.pagingtutorial.di.DiActivity
import com.t0p47.pagingtutorial.model.RepoSearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepositoriesActivity : DiActivity() {

    private lateinit var binding: ActivitySearchRepositoriesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter = ReposAdapter()

    private lateinit var viewModel: SearchRepositoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[SearchRepositoriesViewModel::class.java]

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        setupScrollListener()

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        if(viewModel.repoResult.value == null){
            viewModel.searchRepo(query)
        }
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }

    private fun initAdapter(){
        binding.list.adapter = adapter
        viewModel.repoResult.observe(this){ result ->
            when(result){
                is RepoSearchResult.Success -> {
                    showEmptyList(result.data.isEmpty())
                    adapter.submitList(result.data)
                }
                is RepoSearchResult.Error -> {
                    Toast.makeText(
                        this,
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("LOG_TAG","SearchRepositoriesActivity: initAdapter: error: $result")
                }
            }
        }
    }

    private fun initSearch(query: String){
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                updateRepoListFromInput()
                true
            }else{
                false
            }
        }
        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                updateRepoListFromInput()
                true
            }else{
                false
            }
        }
    }

    private fun updateRepoListFromInput(){
        binding.searchRepo.text.trim().let {
            if(it.isNotEmpty()){
                binding.list.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
            }
        }
    }

    private fun showEmptyList(show: Boolean){
        if(show){
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        }else{
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener(){
        val layoutManager = binding.list.layoutManager as LinearLayoutManager
        binding.list.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}