package io.horizontalsystems.indexwallet.modules.settings.security.privacy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.horizontalsystems.indexwallet.core.BaseActivity
import io.horizontalsystems.indexwallet.R
import kotlinx.android.synthetic.main.activity_settings_privacy.*

class PrivacySettingsInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_settings_info)

        setSupportActionBar(toolbar)
    }

    companion object {
        fun start(context: Activity) {
            val intent = Intent(context, PrivacySettingsInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.info_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuClose -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
