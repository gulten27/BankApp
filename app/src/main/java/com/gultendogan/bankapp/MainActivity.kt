package com.gultendogan.bankapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.gultendogan.bankapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,ShareActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signIn(view:View){

        val email = binding.emailText.text.toString()
        val parola = binding.passwordText.text.toString()

        if (parola != "" && email != ""){
            auth.signInWithEmailAndPassword(email,parola).addOnCompleteListener { task->

                if (task.isSuccessful){
                    val currentUser = auth.currentUser?.email.toString()
                    Toast.makeText(applicationContext,"Hoşgeldin : ${currentUser}",Toast.LENGTH_LONG).show()

                    val intent = Intent(this,ShareActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }.addOnFailureListener { exception->
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }

    fun signUp(view:View){

        val email = binding.emailText.text.toString()
        val parola = binding.passwordText.text.toString()
        var cardNo = ""

        for (i in 0..16){
            cardNo += Random.nextInt(0,9)
            println(cardNo)
        }

        auth.createUserWithEmailAndPassword(email,parola).addOnCompleteListener { task->
            if(task.isSuccessful){

                val currentUser = auth.currentUser

                val profileUpdateRequest = userProfileChangeRequest {
                    displayName = cardNo
                }

                if (currentUser != null){
                    currentUser.updateProfile(profileUpdateRequest).addOnCompleteListener {
                        if (task.isSuccessful){
                            Toast.makeText(applicationContext,"Created Card No",Toast.LENGTH_LONG).show()
                        }
                    }
                }

                Toast.makeText(applicationContext,"Kullanıcı Oluşturuldu!",Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener { exception->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
}