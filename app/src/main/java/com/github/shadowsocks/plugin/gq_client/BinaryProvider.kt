package com.github.shadowsocks.plugin.gq_client

import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileNotFoundException
import android.util.Log

import com.github.shadowsocks.plugin.NativePluginProvider
import com.github.shadowsocks.plugin.PathProvider

class BinaryProvider : NativePluginProvider() {
    override fun populateFiles(provider: PathProvider) {
        provider.addPath("gq-client", "755")
    }

    override fun getExecutable(): String {
        val exec = context.applicationInfo.nativeLibraryDir + "/libgq-client.so"
        Log.d("execPath", exec)
        Log.d("execExists", File(exec).exists().toString())
        return exec
    }

    override fun openFile(uri: Uri?): ParcelFileDescriptor {
        if (uri == null) {
            Log.d("URI", "null")
            throw FileNotFoundException()
        }
        when (uri.path) {
            "/gq-client" -> return ParcelFileDescriptor.open(File(getExecutable()), ParcelFileDescriptor.MODE_READ_ONLY)
            else -> throw FileNotFoundException()
        }

    }
}