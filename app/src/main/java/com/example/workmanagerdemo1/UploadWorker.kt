package com.example.workmanagerdemo1

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val KEY_WORKER = "key_worker"
    }
    override fun doWork(): Result {
        return try{
            val count = inputData.getInt(MainActivity.KEY_COUNT_VALUE, 0)
            for(i: Int in 0 until count) {
                Log.i("MyTag", "Uploading $i")
            }
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.US)
            val currentDate: String = time.format(Date())

            val outputData = Data.Builder()
                .putString(KEY_WORKER, currentDate)
                .build()

            Result.success(outputData)
        } catch (e:Exception) {
            Result.failure()
        }

    }
}