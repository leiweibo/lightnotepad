package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.light.lnotepad.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * home and edit page shared viewmodel.
 */
@HiltViewModel
class HomeViewShareViewModel @Inject constructor() : ViewModel() {

    private val _notelist = MutableLiveData<MutableList<Note>>()


    fun insertNote(note: Note) {
        _notelist.value?.add(0, note)
    }

    fun setupNote(notes: MutableList<Note>) {
        _notelist.value = notes
    }
}