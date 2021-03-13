package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.light.lnotepad.data.HomeViewShareNote
import com.light.lnotepad.data.Note

/**
 * home and edit page shared viewmodel.
 */
class HomeViewShareViewModel : ViewModel() {
    val selected = MutableLiveData<HomeViewShareNote>()

    fun updateNote(pos: Int, note: Note) {
        val tmp = HomeViewShareNote(pos, note)
        selected.value = tmp!!
    }
}