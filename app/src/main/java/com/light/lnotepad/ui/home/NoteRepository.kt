package com.light.lnotepad.ui.home

import com.light.lnotepad.data.Note
import com.light.lnotepad.data.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    fun getNoteList() = noteDao.getNoteList()
}