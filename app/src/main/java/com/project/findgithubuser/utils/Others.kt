package com.project.findgithubuser.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.project.findgithubuser.utils.Constants.TAG

fun log(msg: String) {
    Log.d(TAG, msg)
}

fun toast(context: Context?, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}