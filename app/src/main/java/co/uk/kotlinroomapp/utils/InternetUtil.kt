package co.uk.kotlinroomapp.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object InternetUtil : LiveData<Boolean>() {

    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    override fun onActive() {
        startNetworkCallback()
    }

    override fun onInactive() {
       stopNetworkCallback()
    }

    private fun startNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        /**Check if version code is greater than API 24*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(
                builder.build(), networkCallback
            )
        }
    }

    private fun stopNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    value = true
                }
            }
        }

        override fun onLost(network: Network) {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    value = false
                }
            }
        }
    }
}