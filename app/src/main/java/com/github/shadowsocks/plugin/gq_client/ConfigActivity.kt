package com.github.shadowsocks.plugin.gq_client

import android.os.Bundle
import com.github.shadowsocks.plugin.PluginOptions
import com.github.shadowsocks.plugin.ConfigurationActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.util.Log


class ConfigActivity : ConfigurationActivity(), Toolbar.OnMenuItemClickListener {
    private var oldOptions = PluginOptions()

    fun getChild(): ConfigFragment {
        return getFragmentManager().findFragmentById(R.id.content) as ConfigFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        toolbar.setTitle(getTitle())
        toolbar.setNavigationIcon(R.drawable.ic_navigation_close)
        toolbar.setNavigationOnClickListener(
                fun(_) {
                    onBackPressed()
                })
        toolbar.inflateMenu(R.menu.menu_config)
        toolbar.setOnMenuItemClickListener(this)
    }

    override fun onInitializePluginOptions(options: PluginOptions) {
        oldOptions = options
        getChild().onInitializePluginOptions(options)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_apply -> {
                Log.d("options", getChild().options.toString())
                saveChanges(getChild().options)
                finish()
                return true
            }
            else -> return false
        }
    }
}
