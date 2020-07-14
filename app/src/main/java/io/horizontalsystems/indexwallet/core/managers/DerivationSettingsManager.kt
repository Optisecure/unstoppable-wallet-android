package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IAppConfigProvider
import io.horizontalsystems.indexwallet.core.IDerivationSettingsManager
import io.horizontalsystems.indexwallet.core.storage.AppDatabase
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.BlockchainSetting
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.DerivationSetting

class DerivationSettingsManager(
        private val appConfigProvider: IAppConfigProvider,
        private val appDatabase: AppDatabase
) : IDerivationSettingsManager {

    companion object {
        const val derivationSettingKey: String = "derivation"
    }

    override fun defaultDerivationSetting(coinType: CoinType): DerivationSetting? {
        return appConfigProvider.derivationSettings.firstOrNull{ it.coinType == coinType }
    }

    override fun derivationSetting(coinType: CoinType): DerivationSetting? {
        val blockchainSetting = appDatabase.blockchainSettingDao().getSetting(coinType, derivationSettingKey)
        return blockchainSetting?.let { DerivationSetting(coinType, AccountType.Derivation.valueOf(it.value)) }
    }

    override fun updateSetting(derivationSetting: DerivationSetting) {
        appDatabase.blockchainSettingDao().insert(BlockchainSetting(derivationSetting.coinType, derivationSettingKey, derivationSetting.derivation.value))
    }

}
