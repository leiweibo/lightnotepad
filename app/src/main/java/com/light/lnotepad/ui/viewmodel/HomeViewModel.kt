package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.light.lnotepad.data.Note
import com.light.lnotepad.ui.home.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    var storedNoteList: LiveData<MutableList<Note>>? = null
    val noteList: LiveData<MutableList<Note>> =
        if (storedNoteList != null) storedNoteList!! else {
            storedNoteList = repository.getNoteList().asLiveData()
            storedNoteList!!
        }


    suspend fun insertNote(note: Note):Long {
        return repository.insertNote(note)
    }

}