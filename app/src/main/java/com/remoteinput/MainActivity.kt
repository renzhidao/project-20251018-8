// header
package com.remoteinput

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.Inet4Address
import java.net.NetworkInterface

class MainActivity : AppCompatActivity() {

    private val TAG = "RIH-Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 常驻启动服务，避免必须等 IME 弹起
        startService(Intent(this, SocketHubService::class.java))
        Log.i(TAG, "start service")

        val tvIpAddress: TextView = findViewById(R.id.tvIpAddress)
        val btnReceiver: Button = findViewById(R.id.btnReceiver)
        val btnSender: Button = findViewById(R.id.btnSender)

        val ip = getLocalIpAddress()
        tvIpAddress.text = "本机IP: $ip"
        Log.i(TAG, "local ip: $ip")

        btnReceiver.setOnClickListener {
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            startActivity(intent)
            Toast.makeText(this, "请启用并选择'远程输入法'", Toast.LENGTH_LONG).show()
        }

        btnSender.setOnClickListener {
            startActivity(Intent(this, InputSenderActivity::class.java))
        }
    }

    private fun getLocalIpAddress(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val iface = interfaces.nextElement()
                val addresses = iface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val addr = addresses.nextElement()
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.hostAddress ?: "未知"
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getLocalIpAddress error: ${e.message}", e)
        }
        return "获取失败"
    }
}