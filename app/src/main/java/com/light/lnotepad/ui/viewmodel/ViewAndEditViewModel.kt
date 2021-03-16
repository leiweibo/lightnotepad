package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.*
import com.light.lnotepad.data.Note
import com.light.lnotepad.ui.home.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAndEditViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }

    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun setValue(newNote: Note) {
        _note.value = newNote
    }

}