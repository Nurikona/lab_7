package com.example.lab_07

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class FileReadWriteActivity : AppCompatActivity() {

    private lateinit var tvData: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_read_write)

        tvData = findViewById(R.id.tvData)
        val btnWriteFile: Button = findViewById(R.id.btnWriteFile)
        val btnReadFile: Button = findViewById(R.id.btnReadFile)
        btnLogout = findViewById(R.id.btnLogout)

        btnWriteFile.setOnClickListener {
            showWriteFileDialog() // Диалог перед записью
        }

        btnReadFile.setOnClickListener {
            showReadFileDialog() // Диалог перед чтением
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun showWriteFileDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Введите имя файла и содержимое")

        val layout = layoutInflater.inflate(R.layout.dialog_file_input, null)
        val edFileName = layout.findViewById<EditText>(R.id.edFileName)
        val edFileContent = layout.findViewById<EditText>(R.id.edFileContent)

        builder.setView(layout)

        builder.setPositiveButton("Сохранить") { _, _ ->
            val fileName = edFileName.text.toString().trim()
            val fileContent = edFileContent.text.toString()

            if (fileName.isNotEmpty()) {
                writeFile(fileName, fileContent)
            } else {
                Toast.makeText(this, "Имя файла не может быть пустым", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }

        builder.show()
    }

    private fun showReadFileDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Введите имя файла для чтения")

        val edFileName = EditText(this)
        edFileName.hint = "Имя файла"

        builder.setView(edFileName)

        builder.setPositiveButton("Прочитать") { _, _ ->
            val fileName = edFileName.text.toString().trim()
            if (fileName.isNotEmpty()) {
                val data = readFile(fileName)
                tvData.text = data
            } else {
                Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }

        builder.show()
    }

    private fun writeFile(fileName: String, data: String) {
        val file = File(getExternalFilesDir(null), "$fileName.txt")
        file.writeText(data)
        Toast.makeText(this, "Файл \"$fileName.txt\" записан", Toast.LENGTH_SHORT).show()
    }

    private fun readFile(fileName: String): String {
        val file = File(getExternalFilesDir(null), "$fileName.txt")
        return if (file.exists()) {
            file.readText()
        } else {
            "Файл \"$fileName.txt\" не найден"
        }
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        finish()
    }
}
