package com.bignerdranch.android.ind7_v1_karamov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class EditUserFragment(var user : User) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_user, container, false)

        var loginText = view.findViewById<EditText>(R.id.editUserLogin)
        var passwordText = view.findViewById<EditText>(R.id.editUserPassword)
        var adminCheckBox = view.findViewById<CheckBox>(R.id.editUserCheckbox)
        var editButton = view.findViewById<Button>(R.id.editUserButton)

        loginText.setText(user.login)
        passwordText.setText(user.password)
        adminCheckBox.isChecked = user.admin
        editButton.setOnClickListener {

            user.login = loginText.text.toString()
            user.password = passwordText.text.toString()
            user.admin = adminCheckBox.isChecked

            val db = MainDB.getDb(this.requireContext())
            Thread{
                db.getUserDao().updateUser(user)

                val mainAdminFragment = MainAdminFragment()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, mainAdminFragment)
                    .commit()

            }.start()

        }

        return view
    }

}