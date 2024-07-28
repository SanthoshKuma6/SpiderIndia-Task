package com.example.firebase.preference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.firebase.R
/**
 * Santhosh 28/7/24
 */
class DataPreference(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), android.content.Context.MODE_PRIVATE)
    companion object {
        const val USER_ID="user_id"
        const val CLASS_ID="class_id"
        const val EXAM_DATA="Exam_data"
    }

    @SuppressLint("SuspiciousIndentation")
    fun saveUserData(studentId: String) {
        val editor = prefs.edit()
        editor.putString(USER_ID, studentId)
//        editor.putString(CLASS_ID,classId)
        editor.apply()

    }

    fun getUserDetails(): HashMap<String, String> {
        val user = HashMap<String, String>()
        user[USER_ID] = prefs.getString(USER_ID, null).toString()
//        user[CLASS_ID] = prefs.getString(CLASS_ID, null).toString()
        return user
    }



}