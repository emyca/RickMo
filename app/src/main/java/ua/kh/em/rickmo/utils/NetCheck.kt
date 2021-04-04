package ua.kh.em.rickmo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

object NetCheck {
    fun isNetExists(context: Context?): Boolean {
        if (context == null) return false
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null) {
                return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    true
                } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        } else {
            try {
                val activeNetworkInfo = cm.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
        return false
    }
}