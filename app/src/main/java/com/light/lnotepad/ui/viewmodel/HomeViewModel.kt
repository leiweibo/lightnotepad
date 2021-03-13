package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.light.lnotepad.data.Note
import com.light.lnotepad.ui.home.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    var storedNoteList: LiveData<List<Note>>? = null
    val noteList: LiveData<List<Note>> =
        if (storedNoteList != null) storedNoteList!! else {
            storedNoteList = repository.getNoteList().asLiveData()
            storedNoteList!!
        }

}