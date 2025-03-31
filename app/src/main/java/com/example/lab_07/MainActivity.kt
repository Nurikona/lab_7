package com.example.lab_07

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false) // Проверяем, залогинен ли пользователь

        if (isLoggedIn) {
            // Если пользователь уже вошёл, переходим в FileReadWriteActivity
            startActivity(Intent(this, FileReadWriteActivity::class.java))
        } else {
            // Если пользователь не вошёл, переходим в LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish() // Закрываем MainActivity, чтобы не возвращаться сюда при нажатии "Назад"
    }
}
