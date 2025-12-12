package com.bignerdranch.android.ind7_v1_karamov

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

data class SavedUser(
    var login: String,
    var password: String,
)

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var buttonEnter : Button
    private lateinit var registerButton : TextView
    private lateinit var loginText : EditText
    private lateinit var passwordText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginText = findViewById(R.id.loginText)
        passwordText = findViewById(R.id.loginPassword)
        buttonEnter = findViewById(R.id.buttonEnter)
        registerButton = findViewById(R.id.registerButton)

        sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE)

        val jsonString = sharedPreferences.getString("KEY_USER", "")

        var saveduser = Gson().fromJson(jsonString, SavedUser::class.java)

        if (saveduser != null)
        {
            loginText.setText(saveduser.login)
            passwordText.setText(saveduser.password)
        }
        else
        {
            saveduser = SavedUser("", "")
        }

        val db = MainDB.getDb(this)
        Thread {
            if (!db.getUserDao().UserExists("admin", "1")) {
                val user: User = User(null, "admin", "1", true)
                db.getUserDao().insertItem(user)
            }
        }.start()

        buttonEnter.setOnClickListener {
            if (loginText.text.toString().isNotEmpty() && passwordText.text.toString().isNotEmpty())
            {
                val user = User(null, loginText.text.toString(), passwordText.text.toString(), false)
                val db = MainDB.getDb(this)
                Thread {
                    if (db.getUserDao().UserExists(user.login, user.password)) {

                        saveduser.login = loginText.text.toString()
                        saveduser.password = passwordText.text.toString()
                        var jsonUser = Gson().toJson(saveduser)
                        sharedPreferences.edit {
                            putString("KEY_USER", jsonUser)
                            apply()
                        }

                        if (db.getUserDao().UserIsAdmin(user.login))
                        {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                putExtra("USER", user)
                                putExtra("USER_ADMIN", true)
                            }
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                putExtra("USER", user)
                                putExtra("USER_ADMIN", false)
                            }
                            startActivity(intent)
                            finish()
                        }

                    }
                    else
                    {
                        this.runOnUiThread{
                            Snackbar.make(buttonEnter, "Неверный логин или пароль", Snackbar.LENGTH_LONG).show()
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