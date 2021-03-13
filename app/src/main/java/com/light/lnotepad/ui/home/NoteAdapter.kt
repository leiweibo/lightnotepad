package com.light.lnotepad.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.light.lnotepad.data.Note
import com.light.lnotepad.databinding.ItemNoteHomeBinding


class NoteAdapter : ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            ItemNoteHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)
        (holder as NoteViewHolder).bind(note)
    }

    class NoteViewHolder(private val binding: ItemNoteHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.note?.let {note ->
                    val direction = HomeFragmentDirections.actionHomeFragmentToViewFragment(note)
                    it.findNavController().navigate(direction)
                }

            }
        }

        fun bind(item: Note) {
            binding.apply {
                note = item
                executePendingBindings()
            }
        }
    }
}


private class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

}