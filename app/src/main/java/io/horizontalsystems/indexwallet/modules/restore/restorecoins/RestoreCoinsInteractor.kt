package io.horizontalsystems.indexwallet.modules.restore.restorecoins

import io.horizontalsystems.indexwallet.core.*
import io.horizontalsystems.indexwallet.entities.*

class RestoreCoinsInteractor(
        private val coinManager: ICoinManager,
        private val blockChainSettingsManager: IBlockchainSettingsManager
) : RestoreCoinsModule.IInteractor {

    override val coins: List<Coin>
        get() = coinManager.coins

    override val featuredCoins: List<Coin>
        get() = coinManager.featuredCoins

    override fun derivationSettings(coin: Coin): DerivationSetting? {
        return blockChainSettingsManager.derivationSetting(coin.type)
    }

    override fun saveDerivationSetting(setting: DerivationSetting) {
        blockChainSettingsManager.saveSetting(setting)
    }

}
