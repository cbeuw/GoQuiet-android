package com.github.shadowsocks.plugin.gq_client

import android.os.Bundle
import android.preference.*
import android.view.View
import com.github.shadowsocks.plugin.PluginContract
import com.github.shadowsocks.plugin.PluginOptions


class ConfigFragment : PreferenceFragment() {
    var options = PluginOptions()

    fun onInitializePluginOptions(options: PluginOptions) {
        this.options = options
        val ary = arrayOf(Pair("Key", ""), Pair("ServerName", "bing.com"),
                Pair("TicketTimeHint", "3600"), Pair("Browser", "chrome"),
                Pair("FastOpen", "true"))
        for (element in ary) {
            val key = element.first
            val defaultValue = element.second
            val pref = findPreference(key)
            val current: String? = options.get(key)
            val value = current ?: defaultValue
            when (pref) {
                is ListPreference -> {
                    pref.setValue(value)
                }
                is EditTextPreference -> {
                    pref.setText(value)
                }
                is CheckBoxPreference -> {
                    pref.setChecked(value.toBoolean())
                }
            }
            // we want all preferences to be put into the options, not only the changed ones
            options.put(key, value)
            pref.setOnPreferenceChangeListener(
                    fun(_: Preference, value: Any): Boolean {
                        options.put(key, value.toString())
                        return true
                    }
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PluginContract.EXTRA_OPTIONS, options.toString())
    }

    override fun onViewCreated(vidw: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vidw, savedInstanceState)
        if (savedInstanceState != null) {
            options = PluginOptions(savedInstanceState.getString(PluginContract.EXTRA_OPTIONS))
            onInitializePluginOptions(options)
        }
        addPreferencesFromResource(R.xml.config)
    }
}