package com.bignerdranch.android.ind7_v1_karamov

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var buttonRegEnter : Button
    private lateinit var loginButton : TextView

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
                if (passwordText.text.toString() == repeatPasswordText.text.toString())
                {
                    val user = User(null, loginText.text.toString(), passwordText.text.toString(), false)
                    val db = MainDB.getDb(this)
                    Thread {
                        if (!db.getUserDao().UserRegExists(user.login)) {
                            db.getUserDao().insertItem(user)

                            val intent = Intent(this@RegisterActivity, MainActivity::class.java).apply {
                                putExtra("USER", user)
                                putExtra("USER_ADMIN", false)
                            }
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            this.runOnUiThread{
                                Snackbar.make(buttonRegEnter, "Этот пользователь уже существует", Snackbar.LENGTH_LONG).show()
                            }
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