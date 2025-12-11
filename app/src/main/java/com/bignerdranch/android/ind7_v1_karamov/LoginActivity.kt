package com.bignerdranch.android.ind7_v1_karamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var buttonEnter : Button
    private lateinit var registerButton : Button
    private lateinit var loginText : EditText
    private lateinit var passwordText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonEnter = findViewById(R.id.buttonEnter)
        registerButton = findViewById(R.id.registerButton)

        buttonEnter.setOnClickListener {
            if (loginText.text.toString().isNotEmpty() && passwordText.text.toString().isNotEmpty())
            {
                val user = User(0, loginText.text.toString(), passwordText.text.toString(), false)
                val db = MainDB.getDb(this)
                Thread {
                    if (db.getUserDao().UserExists(user.login)) {

                        if (db.getUserDao().UserIsAdmin(user.login))
                        {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                putExtra("USER_LOGIN", loginText.text.toString())
                                putExtra("USER_ADMIN", true)
                            }
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                putExtra("USER_LOGIN", loginText.text.toString())
                                putExtra("USER_ADMIN", false)
                            }
                            startActivity(intent)
                            finish()
                        }

                    }
                }.start()
            }
            else
            {
                Snackbar.make(buttonEnter, "Все поля должны быть заполнены", Snackbar.LENGTH_SHORT).show()
            }

        }

        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}