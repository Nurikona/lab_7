package com.example.lab_07

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var btnCreateUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edUsername = findViewById(R.id.ed_username)
        edPassword = findViewById(R.id.ed_password)
        edConfirmPassword = findViewById(R.id.ed_confirm_pwd)
        btnCreateUser = findViewById(R.id.btn_create_user)

        btnCreateUser.setOnClickListener {
            val username = edUsername.text.toString()
            val password = edPassword.text.toString()
            val confirmPassword = edConfirmPassword.text.toString()

            if (password == confirmPassword && username.isNotEmpty() && password.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // Сохранение логина и пароля
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putBoolean("isLoggedIn", true) // Устанавливаем, что пользователь вошёл
                editor.apply()

                Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()

                // Переход на главную страницу
                startActivity(Intent(this, FileReadWriteActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Пароли не совпадают или поля пустые", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
