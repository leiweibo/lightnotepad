package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.light.lnotepad.data.Note
import com.light.lnotepad.ui.home.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * home and edit page shared viewmodel.
 */
@HiltViewModel
class HomeViewShareViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notelist = MutableLiveData<MutableList<Note>>()


    suspend fun insertNote(note: Note) {
        val id  = repository.insertNote(note)
        note.id = id
        _notelist.value?.add(0, note)
    }

    fun setupNote(notes: MutableList<Note>) {
        _notelist.value = notes
    }
}