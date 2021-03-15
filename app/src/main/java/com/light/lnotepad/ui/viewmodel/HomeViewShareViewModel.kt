package com.light.lnotepad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.light.lnotepad.data.Note
import com.light.lnotepad.ui.home.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
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
        val id = repository.insertNote(note)
        note.id = id
        _notelist.value?.add(0, note)
    }

    fun setupNote(notes: MutableList<Note>) {
        _notelist.value = notes
    }

    suspend fun deleteNote(pos: Int) {
        val note = _notelist.value?.get(pos)
        _notelist.value?.remove(note)
        if (note != null) repository.deleteNote(note)
    }

    fun swipeMemoryData(startPos: Int, targetPos: Int) {
        Collections.swap(
            _notelist.value,
            startPos,
            targetPos
        )
    }

    suspend fun swipeDBData(startPos: Int, targetPos: Int) {
        var  tmp1 = _notelist.value?.get(startPos)?.order
        var tmp2 = _notelist.value?.get(targetPos)?.order
        _notelist.value?.get(startPos)?.order = tmp2
        _notelist.value?.get(targetPos)?.order = tmp1

        repository.updateNote(_notelist.value?.get(startPos) as Note)
        repository.updateNote(_notelist.value?.get(targetPos) as Note)
    }

}