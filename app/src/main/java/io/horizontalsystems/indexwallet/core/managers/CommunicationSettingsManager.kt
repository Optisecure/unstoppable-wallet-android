package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IAppConfigProvider
import io.horizontalsystems.indexwallet.core.ICommunicationSettingsManager
import io.horizontalsystems.indexwallet.core.storage.AppDatabase
import io.horizontalsystems.indexwallet.entities.BlockchainSetting
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.CommunicationMode
import io.horizontalsystems.indexwallet.entities.CommunicationSetting

class CommunicationSettingsManager(
        private val appConfigProvider: IAppConfigProvider,
        private val appDatabase: AppDatabase
) : ICommunicationSettingsManager {

    companion object {
        const val communicationSettingKey: String = "communication"
    }

    private fun getBaseCoinType(coinType: CoinType): CoinType {
        return when (coinType) {
            is CoinType.Erc20 -> CoinType.Ethereum
            else -> coinType
        }
    }

    override fun defaultCommunicationSetting(coinType: CoinType): CommunicationSetting? {
        return appConfigProvider.communicationSettings.firstOrNull { it.coinType.javaClass == getBaseCoinType(coinType).javaClass }
    }

    override fun communicationSetting(coinType: CoinType): CommunicationSetting? {
        val blockchainSetting = appDatabase.blockchainSettingDao().getSetting(getBaseCoinType(coinType), communicationSettingKey)
        return blockchainSetting?.let { CommunicationSetting(getBaseCoinType(coinType), CommunicationMode.valueOf(it.value)) }
    }

    override fun updateSetting(communicationSetting: CommunicationSetting) {
        appDatabase.blockchainSettingDao().insert(BlockchainSetting(getBaseCoinType(communicationSetting.coinType), communicationSettingKey, communicationSetting.communicationMode.value))
    }

}
