package io.horizontalsystems.indexwallet.modules.settings.experimental.bitcoinhodling

import io.horizontalsystems.indexwallet.core.ILocalStorage

class BitcoinHodlingInteractor(
        private val storage: ILocalStorage
) : BitcoinHodlingModule.IInteractor {

    override var isLockTimeEnabled: Boolean
        get() = storage.isLockTimeEnabled
        set(enabled) {
            storage.isLockTimeEnabled = enabled
        }

}
