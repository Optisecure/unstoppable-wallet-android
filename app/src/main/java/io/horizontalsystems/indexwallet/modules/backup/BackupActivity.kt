package io.horizontalsystems.indexwallet.modules.backup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.indexwallet.R
import io.horizontalsystems.indexwallet.core.BaseActivity
import io.horizontalsystems.indexwallet.core.setOnSingleClickListener
import io.horizontalsystems.indexwallet.core.utils.ModuleCode
import io.horizontalsystems.indexwallet.core.utils.ModuleField
import io.horizontalsystems.indexwallet.entities.Account
import io.horizontalsystems.indexwallet.modules.backup.eos.BackupEosModule
import io.horizontalsystems.indexwallet.modules.backup.words.BackupWordsModule
import io.horizontalsystems.core.helpers.HudHelper
import io.horizontalsystems.pin.PinModule
import kotlinx.android.synthetic.main.activity_backup.*

class BackupActivity : BaseActivity() {

    private lateinit var viewModel: BackupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val account = intent.getParcelableExtra<Account>(ModuleField.ACCOUNT) ?: run { finish(); return }
        val accountCoins = intent.getStringExtra(ModuleField.ACCOUNT_COINS)

        viewModel = ViewModelProvider(this).get(BackupViewModel::class.java)
        viewModel.init(account)

        buttonNext.setOnSingleClickListener { viewModel.delegate.onClickBackup() }

        viewModel.startPinModule.observe(this, Observer {
            PinModule.startForUnlock(this, ModuleCode.UNLOCK_PIN)
        })

        viewModel.startBackupWordsModule.observe(this, Observer {
            BackupWordsModule.start(this, it.first, account.isBackedUp, it.second)
        })

        viewModel.startBackupEosModule.observe(this, Observer {
            BackupEosModule.start(this, it.first, it.second)
        })

        viewModel.closeLiveEvent.observe(this, Observer {
            finish()
        })

        viewModel.showSuccessAndFinishEvent.observe(this, Observer {
            HudHelper.showSuccessMessage(findViewById(android.R.id.content), R.string.Hud_Text_Done, HudHelper.SnackbarDuration.LONG)
            finish()
        })

        backupIntro.text = getString(R.string.Backup_Intro_Subtitle, accountCoins)

        if (account.isBackedUp) {
            collapsingToolbar.title = getString(R.string.Backup_Intro_TitleShow)
            buttonNext.text = getString(R.string.Backup_Button_ShowKey)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ModuleCode.BACKUP_WORDS -> {
                when (resultCode) {
                    BackupWordsModule.RESULT_BACKUP -> {
                        viewModel.delegate.didBackup()
                    }
                    BackupWordsModule.RESULT_SHOW -> {
                        finish()
                    }
                }
            }
            ModuleCode.BACKUP_EOS -> {
                finish()
            }
            ModuleCode.UNLOCK_PIN -> {
                when (resultCode) {
                    PinModule.RESULT_OK -> viewModel.delegate.didUnlock()
                    PinModule.RESULT_CANCELLED -> viewModel.delegate.didCancelUnlock()
                }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.delegate.onClickCancel()
    }

    companion object {

        fun start(context: Context, account: Account, coinCodes: String) {
            val intent = Intent(context, BackupActivity::class.java).apply {
                putExtra(ModuleField.ACCOUNT, account)
                putExtra(ModuleField.ACCOUNT_COINS, coinCodes)
            }

            context.startActivity(intent)
        }
    }
}