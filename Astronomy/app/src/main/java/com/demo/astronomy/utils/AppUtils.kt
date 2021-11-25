package com.demo.astronomy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.demo.astronomy.App
import java.text.SimpleDateFormat
import java.util.*

/**
 * Class to perform app ralated operation
 */
object AppUtils {

    /**
     * Checks if User visiting first time in the day
     *
     * @return true if not visiting first time, else false
     */
    fun isUserNotVisitingFirstTime(): Boolean {
        val sharedPref = App.appContext.getSharedPreferences(
            CommonConstants.PACKAGE,
            AppCompatActivity.MODE_PRIVATE
        )
        if (sharedPref.getString(CommonConstants.DATE, "").equals(getCurrentDate()))
            return true
        return false
    }

    /**
     * Check the Internet connection
     *
     * @return true if connected, else false
     */
    fun isInternetConnected(): Boolean {
        val connectivityManager =
            App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    /**
     * Gets the current date
     *
     * @return String: current date
     */
    fun getCurrentDate(): String? {
        val date: Date = Calendar.getInstance().time
        val df = SimpleDateFormat(CommonConstants.DATE_FORMAT, Locale.getDefault())
        return df.format(date)
    }

    /**
     * Stores the APOD details in shared preferences
     *
     * @param date: current date
     * @param title: image tile
     * @param explanation: image explanation
     */
    fun storeAPODDetails(date: String?, title: String?, explanation: String?) {
        val sharedPreferences = App.appContext.getSharedPreferences(
            CommonConstants.PACKAGE,
            AppCompatActivity.MODE_PRIVATE
        )
        sharedPreferences.edit().putString(CommonConstants.DATE, date).apply()
        sharedPreferences.edit().putString(CommonConstants.TITLE, title).apply()
        sharedPreferences.edit().putString(CommonConstants.EXPLANATION, explanation).apply()

    }
}