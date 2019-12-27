package com.example.realtimedatabase
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfo(
    val name: String = "",
    val mobile: String = "",
    val address: String = ""
)
