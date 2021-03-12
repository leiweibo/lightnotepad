package com.light.lnotepad.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.light.lnotepad.data.AppDatabase
import com.light.lnotepad.data.Note
import kotlinx.coroutines.coroutineScope
import java.util.*

class SeedDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("init_note.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Note>>() {}.type
                    val noteList: List<Note> = Gson().fromJson(jsonReader, plantType)

                    val calendar:Calendar = Calendar.getInstance()
                    noteList.map {
                        it.startTime = calendar.time
                        it.createTime = calendar.time
                        calendar.add(Calendar.DATE, 1)
                        it.endTime = calendar.time
                    }
                    val database = AppDatabase.getInstance(applicationContext)
                    database.getNoteDao().insertAll(noteList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}