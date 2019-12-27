package com.example.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        init()

        saveBtn.setOnClickListener {

            val n: String = inputFullName.text.toString()
            val p: String = inputPhone.text.toString()
            val a: String = inputAddress.text.toString()


            if (TextUtils.isEmpty(n)) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ სახელი", Toast.LENGTH_LONG).show()

            } else {
                contactInfo(n, p, a)
            }
        }

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("მომხმარებლის ინფორმაცია")

        addUserInfoChangeListener()

    }

    private fun contactInfo(name: String, phone: String?, address: String) {

        val userInfo = phone?.let { UserInfo(name, it, address) }
        db.child(auth.currentUser?.uid!!).setValue(userInfo)
    }

    private fun addUserInfoChangeListener() {

        db.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snap: DataSnapshot) {

                    val userInfo: UserInfo = snap.getValue(UserInfo::class.java) ?: return

                    showFullName.text = userInfo.name
                    showAddress.text = userInfo.address
                    showPhone.text = userInfo.mobile ?: ""



                    inputFullName.setText("")
                    inputPhone.setText("")
                    inputAddress.setText("")




                }


            })
    }
}


