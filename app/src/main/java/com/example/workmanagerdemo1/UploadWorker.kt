package com.example.workmanagerdemo1

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        try{
            for(i: Int in 0..600000) {
                Log.i("MyTag", "Uploading $i")
            }
        } catch (e:Exception) {
            return  Result.failure()
        }
        return Result.success()
    }
}