// header
package com.remoteinput

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class RemoteInputApp : Application() {
override fun onCreate() {
super.onCreate()
CrashLogger.init(this)
Thread.setDefaultUncaughtExceptionHandler { t, e ->
CrashLogger.e("RIH-CRASH", "FATAL on ${t.name}", e)
Handler(Looper.getMainLooper()).post {
Toast.makeText(this, "崩溃: ${e.javaClass.simpleName}", Toast.LENGTH_LONG).show()
}
}
}
}