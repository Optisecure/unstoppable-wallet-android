package io.horizontalsystems.indexwallet.modules.settings.managekeys

import io.horizontalsystems.core.SingleLiveEvent
import io.horizontalsystems.indexwallet.entities.Account
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.PredefinedAccountType

class ManageKeysRouter : ManageKeysModule.IRouter {

    val showRestore = SingleLiveEvent<PredefinedAccountType>()
    val showCreateWalletLiveEvent = SingleLiveEvent<PredefinedAccountType>()
    val showBackupModule = SingleLiveEvent<Pair<Account, PredefinedAccountType>>()
    val closeEvent = SingleLiveEvent<Void>()
    val showBlockchainSettings = SingleLiveEvent<List<CoinType>>()


    override fun showRestore(predefinedAccountType: PredefinedAccountType) {
        showRestore.postValue(predefinedAccountType)
    }

    override fun showCreateWallet(predefinedAccountType: PredefinedAccountType) {
        showCreateWalletLiveEvent.postValue(predefinedAccountType)
    }

    override fun showBackup(account: Account, predefinedAccountType: PredefinedAccountType) {
        showBackupModule.postValue(Pair(account, predefinedAccountType))
    }

    override fun showBlockchainSettings(enabledCoinTypes: List<CoinType>) {
        showBlockchainSettings.postValue(enabledCoinTypes)
    }

    override fun close() {
        closeEvent.call()
    }
}
