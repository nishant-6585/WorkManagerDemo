package com.example.workmanagerdemo1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_COUNT_VALUE = "key_count"
    }

    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            //setOneTimeWorkRequest()
            setPeriodicWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest() {
        val workManager: WorkManager = WorkManager.getInstance(applicationContext)
        val data: Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 125)
            .build()
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val filteringRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java)
            .build()
        val compressingRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java)
            .build()
        val downloadRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
            .build()

        val parallelWorks = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(downloadRequest)
        parallelWorks.add(filteringRequest)

        workManager
            .beginWith(parallelWorks)
            .then(compressingRequest)
            .then(uploadRequest)
            .enqueue()
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                textView.text = it.state.name
                if (it.state.isFinished) {
                    val data1 = it.outputData
                    val message = data1.getString(UploadWorker.KEY_WORKER)
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun setPeriodicWorkRequest() {
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(DownloadingWorker::class.java, 16, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(periodicWorkRequest)
    }
}