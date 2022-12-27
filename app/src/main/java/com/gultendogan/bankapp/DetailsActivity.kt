package com.gultendogan.bankapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gultendogan.bankapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private lateinit var binding : ActivityDetailsBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
    }

    fun shareButton(view:View){
        val miktar = binding.miktarText.text.toString()
        val alici = binding.aliciText.text.toString()
        val kullanici = auth.currentUser!!.email.toString()
        val zaman = com.google.firebase.Timestamp.now()

        val shareMap = hashMapOf<String,Any>()
        shareMap.put("miktar",miktar)
        shareMap.put("alici",alici)
        shareMap.put("kullanici",kullanici)
        shareMap.put("zaman",zaman)

        db.collection("Shares").add(shareMap).addOnCompleteListener { task->
            if (task.isSuccessful){
                finish()
            }
        }.addOnFailureListener { exception->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }


}