package com.example.firstpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firstpractice.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnsubmit.setOnClickListener {


            val name = binding.edtname.text.toString()
            val lastname = binding.edtlastname.text.toString()
            val email = binding.edtemail.text.toString()
            val country = binding.edtcountry.text.toString()
            val state = binding.edtstate.text.toString()
            val address = binding.edtaddress.text.toString()
            val birthdate = binding.edtbirthdate.text.toString()
            val phonenumber = binding.edtphone.text.toString()
            val rgbuttonid = binding.rbg.checkedRadioButtonId
            val rbuttongender = findViewById<RadioButton>(rgbuttonid)
            val gender = rbuttongender.text.toString()


            val emailauth = findViewById<EditText>(R.id.edtemail)
            val passwordauth = findViewById<EditText>(R.id.edtpassword)
            if (emailauth.text.isNotEmpty() && passwordauth.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        emailauth.text.toString(),
                        passwordauth.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val register = Intent(this, RegisterActivity::class.java)
                            startActivity(register)
                        } else {
                            showAlert()
                        }
                    }

                database = FirebaseDatabase.getInstance().getReference("Users")
                val User = User(
                    email,
                    name,
                    lastname,
                    country,
                    state,
                    address,
                    birthdate,
                    phonenumber,
                    gender
                )



                FirebaseAuth.getInstance().currentUser?.let { it1 ->
                    database.child(it1.uid).setValue(User).addOnSuccessListener {

                        binding.edtname.text.clear()
                        binding.edtlastname.text.clear()
                        binding.edtemail.text.clear()
                        binding.edtcountry.text.clear()
                        binding.edtstate.text.clear()
                        binding.edtaddress.text.clear()
                        binding.edtbirthdate.text.clear()
                        binding.edtphone.text.clear()



                        Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()

                        val profile = Intent(this, ProfileActivity::class.java)
                        startActivity(profile)

                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
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
}