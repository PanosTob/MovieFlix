package com.panostob.movieflix.util.ext

import android.app.Activity
import android.content.Intent
import com.panostob.movieflix.AppActivity

fun Activity.restartApp() {
    finishAffinity()
    val intent = Intent(this, AppActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}