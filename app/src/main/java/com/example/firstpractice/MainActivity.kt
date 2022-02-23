package com.example.firstpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mates()
    }

    private fun mates(){
        title = "Login"

        val registerButton = findViewById<Button>(R.id.btnregister)
        val loginButton = findViewById<Button>(R.id.btnlogin)

        registerButton.setOnClickListener {

            val register = Intent(this, RegisterActivity::class.java)
            startActivity(register)

        }

//        registerButton.setOnClickListener{
//            val email = findViewById<EditText>(R.id.emaillogin)
//            val password = findViewById<EditText>(R.id.passwordlogin)
//            if(email.text.isNotEmpty() && password.text.isNotEmpty()){
//                FirebaseAuth.getInstance()
//                    .createUserWithEmailAndPassword(email.text.toString(),
//                        password.text.toString()).addOnCompleteListener {
//                        if (it.isSuccessful){
//                            val register = Intent(this, RegisterActivity::class.java)
//                            startActivity(register)
//                        }else{
//                            showAlert()
//                        }
//                    }
//            }
//        }

        loginButton.setOnClickListener{
            val email = findViewById<EditText>(R.id.emaillogin)
            val password = findViewById<EditText>(R.id.passwordlogin)
            if(email.text.isNotEmpty() && password.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.text.toString(),
                        password.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            val profile = Intent(this, ProfileActivity::class.java)
                            startActivity(profile)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showProfile(email: String, provider: ProviderType){
        val profile = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)

        }
        startActivity(profile)
    }
}