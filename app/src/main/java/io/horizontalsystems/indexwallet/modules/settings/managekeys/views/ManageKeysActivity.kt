package io.horizontalsystems.indexwallet.modules.settings.managekeys.views

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.indexwallet.core.BaseActivity
import io.horizontalsystems.indexwallet.R
import io.horizontalsystems.indexwallet.modules.backup.BackupModule
import io.horizontalsystems.indexwallet.modules.addressformat.AddressFormatSettingsModule
import io.horizontalsystems.indexwallet.modules.createwallet.CreateWalletModule
import io.horizontalsystems.indexwallet.modules.restore.RestoreMode
import io.horizontalsystems.indexwallet.modules.restore.RestoreModule
import io.horizontalsystems.indexwallet.modules.settings.managekeys.*
import io.horizontalsystems.indexwallet.modules.settings.managekeys.views.ManageKeysDialog.ManageAction
import kotlinx.android.synthetic.main.activity_manage_keys.*

class ManageKeysActivity : BaseActivity(), ManageKeysDialog.Listener, ManageKeysAdapter.Listener {

    private lateinit var presenter: ManageKeysPresenter
    private lateinit var adapter: ManageKeysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ViewModelProvider(this, ManageKeysModule.Factory()).get(ManageKeysPresenter::class.java)

        setContentView(R.layout.activity_manage_keys)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ManageKeysAdapter(this)
        recyclerView.adapter = adapter

        observeView(presenter.view as ManageKeysView)
        observeRouter(presenter.router as ManageKeysRouter)

        (presenter.view as ManageKeysView).confirmUnlinkEvent.observe(this, Observer { item ->
            item.account?.let { account ->

                val confirmationList = listOf(
                        getString(R.string.ManageKeys_Delete_ConfirmationRemove, getString(item.predefinedAccountType.title)),
                        getString(R.string.ManageKeys_Delete_ConfirmationDisable, getString(item.predefinedAccountType.coinCodes)),
                        getString(R.string.ManageKeys_Delete_ConfirmationLose)
                )

                val confirmListener = object : ManageKeysDeleteAlert.Listener {
                    override fun onConfirmationSuccess() {
                        presenter.onConfirmUnlink(account.id)
                    }
                }

                ManageKeysDeleteAlert.show(this, getString(item.predefinedAccountType.title), confirmationList, confirmListener)
            }
        })

        (presenter.view as ManageKeysView).confirmBackupEvent.observe(this, Observer {
            val title = getString(R.string.ManageKeys_Delete_Alert_Title)
            val subtitle = getString(it.predefinedAccountType.title)
            val description = getString(R.string.ManageKeys_Delete_Alert)
            ManageKeysDialog.show(title, subtitle, description, this, this, ManageAction.BACKUP)
        })

        presenter.onLoad()
    }

    private fun observeView(view: ManageKeysView) {
        view.showItemsEvent.observe(this, Observer { list ->
            adapter.items = list
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeRouter(router: ManageKeysRouter) {
        router.showRestore.observe(this, Observer { predefinedAccountType ->
            RestoreModule.startForResult(this, predefinedAccountType, RestoreMode.FromManageKeys)
        })

        router.showCreateWalletLiveEvent.observe(this, Observer { predefinedAccountType ->
            CreateWalletModule.startInApp(this, predefinedAccountType)
        })

        router.showBackupModule.observe(this, Observer { (account, predefinedAccountType) ->
            BackupModule.start(this, account, getString(predefinedAccountType.coinCodes))
        })

        router.showBlockchainSettings.observe(this, Observer { enabledCoinTypes ->
            AddressFormatSettingsModule.startForResult(this, enabledCoinTypes, false)
        })

        router.closeEvent.observe(this, Observer {
            finish()
        })
    }

    //  ManageKeysAdapter Listener

    override fun onClickAdvancedSettings(item: ManageAccountItem) {
        presenter.onClickAdvancedSettings(item)
    }

    override fun onClickCreate(item: ManageAccountItem) {
        presenter.onClickCreate(item)
    }

    override fun onClickRestore(item: ManageAccountItem) {
        presenter.onClickRestore(item)
    }

    override fun onClickBackup(item: ManageAccountItem) {
        presenter.onClickBackup(item)
    }

    override fun onClickUnlink(item: ManageAccountItem) {
        presenter.onClickUnlink(item)
    }

    //  ManageKeysDialog Listener

    override fun onClickBackupKey() {
        presenter.onConfirmBackup()
    }
}