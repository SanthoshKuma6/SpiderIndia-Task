package com.example.firebase.maps

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.firebase.R

/**
 * Santhosh 28/7/24
 */
class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            if (!NetworkUtil.getConnectivityStatusString(context!!)) {
                val alertDialog = AlertDialog.Builder(context).create()
                val layout: View = LayoutInflater.from(context).inflate(R.layout.layout_no_internet, null)
                val retry = layout.findViewById<Button>(R.id.retry)
                alertDialog.setView(layout)
                alertDialog.setCancelable(false)
                alertDialog.show()
                retry.setOnClickListener(View.OnClickListener {
                    alertDialog.dismiss()
                    onReceive(context, intent)
                })
            }

        }catch (e:RuntimeException){
            e.printStackTrace()
        }
    }
}

object NetworkUtil {
    fun getConnectivityStatusString(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (i in networkInfos.indices) {
            if (networkInfos[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }
}