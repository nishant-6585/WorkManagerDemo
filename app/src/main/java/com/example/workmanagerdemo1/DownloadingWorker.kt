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

class DownloadingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        return try{
            for(i: Int in 0..3000) {
                Log.i("MyTag", "Downloading $i")
            }
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.US)
            val currentDate: String = time.format(Date())
            Log.i("MyTag", "Completed $currentDate")
            Result.success()
        } catch (e:Exception) {
            Result.failure()
        }

    }
}