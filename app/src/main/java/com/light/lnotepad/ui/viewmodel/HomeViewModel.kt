package com.light.lnotepad.ui.viewmodel

import androidx.lifecycle.*
import com.light.lnotepad.data.Note
import com.light.lnotepad.ui.home.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    val noteList: LiveData<MutableList<Note>> = repository.getNoteList().asLiveData()
    private val _notelist = MutableLiveData<MutableList<Note>>()

    fun deleteNote(pos: Int) {
        val note = _notelist.value?.get(pos)
        _notelist.value?.remove(note)
        viewModelScope.launch {
            if (note != null) repository.deleteNote(note)
        }
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


    fun setupNote(notes: MutableList<Note>) {
        _notelist.value = notes
    }
}