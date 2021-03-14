package com.light.lnotepad.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.light.lnotepad.data.HomeViewShareNote
import com.light.lnotepad.databinding.FragmentHomeBinding
import com.light.lnotepad.ui.viewmodel.HomeViewModel
import com.light.lnotepad.ui.viewmodel.HomeViewShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val homeViewShareModel: HomeViewShareViewModel by activityViewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = NoteAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        lifecycleScope.launch {
            fetchValue(adapter)
        }

        binding.fab.setOnClickListener {
            // -1的时候表示不传递值
            val direction = HomeFragmentDirections.actionHomeFragmentToViewFragment(null, -1)
            it.findNavController().navigate(direction)
        }

        subscribeUI(adapter)
        // Inflate the layout for this fragment
        return binding.root
    }


    private suspend fun fetchValue(adapter: NoteAdapter) {
        viewModel.noteList.asFlow().collectLatest {
            adapter.submitList(it)
            homeViewShareModel.setupNote(it)
        }

    }

    private fun subscribeUI(adapter: NoteAdapter) {

    }
}