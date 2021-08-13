package ua.kh.em.rickmo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


fun Context?.isNetExists(): Boolean {
    if (this == null) return false

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

    return capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
}