package com.example.firstpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.firstpractice.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

enum class ProviderType{
    BASIC
}

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var databases : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_profile)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databases = FirebaseDatabase.getInstance()
        databaseReference = databases?.reference!!.child("Users")

        loadData()

    }

    private fun loadData(){

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        binding.tvemail.text =user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.tvname.text = snapshot.child("name").value.toString()
                binding.tvlastname.text = snapshot.child("lastname").value.toString()
                binding.tvemail.text = snapshot.child("email").value.toString()
                binding.tvphone.text = snapshot.child("phone").value.toString()
                binding.tvbdate.text = snapshot.child("birthdate").value.toString()
                binding.tvcountry.text = snapshot.child("country").value.toString()
                binding.tvstate.text = snapshot.child("state").value.toString()
                binding.tvaddress.text = snapshot.child("address").value.toString()
                binding.tvgender.text = snapshot.child("gender").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.btnlogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            finish()
        }

    }
}
