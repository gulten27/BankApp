package com.gultendogan.bankapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gultendogan.bankapp.databinding.ActivityShareBinding
import kotlinx.android.synthetic.main.activity_share.*
import java.util.*
import kotlin.collections.ArrayList

class ShareActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityShareBinding
    val db = Firebase.firestore
    var shareList = ArrayList<Share>()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        binding.usernameText.text = currentUser!!.email.toString()
        binding.cardNoText.text = currentUser.displayName.toString()

        binding.logOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.transfer.setOnClickListener {
            val intent = Intent(this,DetailsActivity::class.java)
            startActivity(intent)
        }

        getData()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = RecyclerViewAdapter(shareList)
        recyclerView.adapter = recyclerViewAdapter

    }

    fun getData(){
        db.collection("Shares").orderBy("zaman",Query.Direction.DESCENDING).addSnapshotListener { snapshot, error ->
            if (error != null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()

            }else{
                if (snapshot != null){
                    if (!snapshot.isEmpty){
                        val documents = snapshot.documents
                        shareList.clear()
                        for (document in documents){
                            val kullanici = document.get("kullanici") as String
                            val alici = document.get("alici") as String
                            val miktar = document.get("miktar") as String

                            var getShare = Share(miktar,alici,kullanici)
                            shareList.add(getShare)

                            println(shareList)
                        }

                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}