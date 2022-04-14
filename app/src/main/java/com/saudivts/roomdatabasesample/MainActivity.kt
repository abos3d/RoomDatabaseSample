package com.saudivts.roomdatabasesample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val dbDao = db.userDao()

        val numbersEditText = findViewById<EditText>(R.id.numberOfUsersEditText)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        findViewById<Button>(R.id.runButton).setOnClickListener {
            val users: MutableList<User> = mutableListOf()
            lifecycleScope.launch {
                for (i in 1..numbersEditText.text.toString().toInt()) {
                    users.add(User(System.currentTimeMillis().toInt(), "$i", "last name"))
                    dbDao.insertAll(users)
                }

                resultTextView.text = "done"

            }
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            val users: MutableList<User> = mutableListOf()
            lifecycleScope.launch {
                dbDao.deleteAll()

                resultTextView.text = "done"

            }
        }

        findViewById<Button>(R.id.showButton).setOnClickListener {
            lifecycleScope.launch {
                dbDao.getAll().let {
                    val users = ""
                    resultTextView.text = it.toString()
                }
            }

        }
    }
}