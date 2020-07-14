package io.horizontalsystems.indexwallet.modules.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import io.horizontalsystems.indexwallet.R
import io.horizontalsystems.indexwallet.core.BaseActivity
import kotlinx.android.synthetic.main.activity_about_settings.*

class AboutSettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun start(context: Activity) {
            val intent = Intent(context, AboutSettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
