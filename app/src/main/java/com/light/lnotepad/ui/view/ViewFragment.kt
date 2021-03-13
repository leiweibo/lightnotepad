package com.light.lnotepad.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.mummyding.colorpickerdialog.ColorPickerDialog
import com.light.lnotepad.R
import com.light.lnotepad.databinding.FragmentViewBinding
import com.light.lnotepad.helper.ColorPool
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewFragment : Fragment() {

    lateinit var binding: FragmentViewBinding
    private val args: ViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewBinding.inflate(layoutInflater, container, false)
        Log.e("Test...", args.note.title)

        binding.apply {
            args.note?.let {
                binding.note = it
                binding.contentContainer.setBackgroundColor(it.color)
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.bg_settings -> {
                    ColorPickerDialog(requireContext(), ColorPool.LIGHT_COLOUR_INT)
                        .setDismissAfterClick(false)
                        .setTitle("Choose Color")
                        .setCheckedColor(Color.parseColor("#FFFFCC"))
                        .setOnColorChangedListener { color ->
                            val drawable = ColorDrawable(color)
                            binding.contentContainer.setBackgroundColor(color)
                        }
                        .build()
                        .show()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

}