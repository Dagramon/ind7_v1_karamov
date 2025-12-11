package com.bignerdranch.android.ind7_v1_karamov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import okhttp3.internal.cache.DiskLruCache
class RegisterActivity : AppCompatActivity() {

    private lateinit var buttonRegEnter : Button
    private lateinit var loginButton : Button

    private lateinit var loginText : EditText
    private lateinit var passwordText : EditText
    private lateinit var repeatPasswordText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonRegEnter = findViewById(R.id.buttonRegEnter)
        loginButton = findViewById(R.id.loginButton)

        loginText = findViewById(R.id.loginRegText)
        passwordText = findViewById(R.id.passwordRegText)
        repeatPasswordText = findViewById(R.id.repeatPasswordRegText)

        loginButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonRegEnter.setOnClickListener {
            if (loginText.text.toString().isNotEmpty() && passwordText.text.toString().isNotEmpty())
            {
                if (passwordText.text == repeatPasswordText.text)
                {
                    val user = User(0, loginText.text.toString(), passwordText.text.toString(), false)
                    val db = MainDB.getDb(this)
                    Thread {
                        if (!db.getUserDao().UserExists(user.login)) {
                            db.getUserDao().insertItem(user)

                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }.start()
                }
                else
                {
                    Snackbar.make(buttonRegEnter, "Пароли не совпадают", Snackbar.LENGTH_SHORT).show()
                }
            }
            else
            {
                Snackbar.make(buttonRegEnter, "Все поля должны быть заполнены", Snackbar.LENGTH_SHORT).show()
            }

        }

    }
}