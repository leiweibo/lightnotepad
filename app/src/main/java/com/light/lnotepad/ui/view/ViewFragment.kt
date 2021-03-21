package com.light.lnotepad.ui.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mummyding.colorpickerdialog.ColorPickerDialog
import com.light.lnotepad.R
import com.light.lnotepad.data.Note
import com.light.lnotepad.databinding.FragmentViewBinding
import com.light.lnotepad.helper.ColorPool
import com.light.lnotepad.ui.viewmodel.ViewAndEditViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ViewFragment : Fragment() {

    lateinit var binding: FragmentViewBinding
    private val args: ViewFragmentArgs by navArgs()
    private val viewModel: ViewAndEditViewModel by viewModels()
    private lateinit var color: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel

        binding.apply {
            args.note?.let {
                binding.note = it
                binding.contentContainer.setBackgroundColor(it.color)
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.bg_settings -> {
                    ColorPickerDialog(requireContext(), ColorPool.LIGHT_COLOUR_INT)
                        .setDismissAfterClick(false)
                        .setTitle("Choose Color")
                        .setCheckedColor(Color.parseColor("#FFFFCC"))
                        .setOnColorChangedListener { color ->
                            binding.contentContainer.setBackgroundColor(color)
                            binding.note?.let {
                                it.color = color
                                viewModel.updateNote(it, null)
                            }
                            this.color = color.toString()

                        }
                        .build()
                        .show()
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            if (binding.note == null) {
                binding.title.text.let { it ->
                    if (it.toString().isNotBlank()) {
                        val title = binding.title.text.toString()
                        val content = binding.content.text.toString()
                        val datetime = Calendar.getInstance().time
                        var note = Note(
                            id = null,
                            tag = "lightnote",
                            title = title,
                            content = content,
                            color = Color.parseColor("#FFEC8B"),
                            createTime = datetime,
                            startTime = datetime,
                            endTime = datetime,
                            order = System.nanoTime()
                        )
                        viewModel.insertNote(note)
                    }
                }
            } else {
                viewModel.updateNote(binding.note) {
                    it.findNavController().navigateUp()
                    Log.e("wbleiiii", "navigation up")
                }
            }

        }


        return binding.root
    }

}