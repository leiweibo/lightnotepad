package com.light.lnotepad.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
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

        bindTouchEvent()
        // Inflate the layout for this fragment
        return binding.root
    }


    private suspend fun fetchValue(adapter: NoteAdapter) {
        viewModel.noteList.asFlow().collectLatest {
            adapter.submitList(it)
            homeViewShareModel.setupNote(it)
        }

    }

    private fun bindTouchEvent() {
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                //首先回调的方法 返回int表示是否监听该方向
                val dragFlags =
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN //拖拽

                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT //侧滑删除

                return makeMovementFlags(
                    dragFlags,
                    swipeFlags
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition

                if (fromPosition < toPosition) {
                    for (i in fromPosition until toPosition) {
                        homeViewShareModel.swipeMemoryData(i, i+1)
                        lifecycleScope.launch {
                            homeViewShareModel.swipeDBData(i, i+1)
                        }

                    }
                } else {
                    for (i in fromPosition downTo toPosition + 1) {

                        homeViewShareModel.swipeMemoryData(i, i-1)
                        lifecycleScope.launch {
                            homeViewShareModel.swipeDBData(i, i-1)
                        }
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition)

                return true
            }



            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                //侧滑事件
                lifecycleScope.launch {
                    homeViewShareModel.deleteNote(viewHolder.bindingAdapterPosition)
                    adapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)
                    Snackbar.make(binding.recyclerview, "Delete success", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })
        touchHelper.attachToRecyclerView(binding.recyclerview)

    }
}