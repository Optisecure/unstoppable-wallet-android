package io.horizontalsystems.indexwallet.modules.restore.restorecoins

import io.horizontalsystems.indexwallet.entities.Coin
import io.horizontalsystems.core.SingleLiveEvent

class RestoreCoinsRouter : RestoreCoinsModule.IRouter {
    val close = SingleLiveEvent<Unit>()
    val closeWithSelectedCoins = SingleLiveEvent<List<Coin>>()

    override fun close() {
        close.call()
    }

    override fun closeWithSelectedCoins(enabledCoins: MutableList<Coin>) {
        closeWithSelectedCoins.postValue(enabledCoins)
    }
}
