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

class CompressingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        return try{
            for(i: Int in 0..300) {
                Log.i("MyTag", "Compressing $i")
            }
            Result.success()
        } catch (e:Exception) {
            Result.failure()
        }

    }
}