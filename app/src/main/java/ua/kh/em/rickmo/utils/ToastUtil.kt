package ua.kh.em.rickmo.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {

    fun toastSimple(context: Context?, textRes: Int) {
        Toast.makeText(context, textRes, Toast.LENGTH_SHORT).show()
    }
}