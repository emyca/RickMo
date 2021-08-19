package ua.kh.em.rickmo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// based on
// https://gist.github.com/thewirelessguy/0634bf94e2935baf8ad00ba4c6e3e7cb
fun Context?.isNetConn(): Boolean {
    val cm = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = cm.activeNetwork ?: return false
    val actNw = cm.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}