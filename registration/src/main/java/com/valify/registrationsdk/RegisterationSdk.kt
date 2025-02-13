package com.valify.registrationsdk

import android.content.Context
import android.content.Intent
import com.valify.registrationsdk.presentation.MainActivity

object RegistrationSDK {

    fun startRegistration(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}