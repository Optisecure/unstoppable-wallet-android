package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IAccountManager
import io.horizontalsystems.indexwallet.core.ILocalStorage
import io.horizontalsystems.indexwallet.core.IWalletManager
import io.horizontalsystems.core.IKeyStoreCleaner

class KeyStoreCleaner(
        private val localStorage: ILocalStorage,
        private val accountManager: IAccountManager,
        private val walletManager: IWalletManager)
    : IKeyStoreCleaner {

    override var encryptedSampleText: String?
        get() = localStorage.encryptedSampleText
        set(value) {
            localStorage.encryptedSampleText = value
        }

    override fun cleanApp() {
        accountManager.clear()
        walletManager.enable(listOf())
        localStorage.clear()
    }
}
