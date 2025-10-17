fun init(application: Application) {
    app = application
}

fun e(tag: String, msg: String, tr: Throwable? = null) {
    Log.e(tag, msg, tr)
    try {
        val a = app ?: return
        val f = File(a.filesDir, "remoteinput-crash.log")
        FileWriter(f, true).use { w ->
            w.append(sdf.format(Date()))
                .append(" [").append(tag).append("] ")
                .append(msg)
                .append('\n')
            if (tr != null) {
                w.append(Log.getStackTraceString(tr)).append('\n')
            }
        }
    } catch (_: Throwable) {
        // ignore
    }
}