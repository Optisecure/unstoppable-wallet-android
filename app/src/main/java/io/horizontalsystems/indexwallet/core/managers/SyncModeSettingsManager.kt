package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IAppConfigProvider
import io.horizontalsystems.indexwallet.core.ISyncModeSettingsManager
import io.horizontalsystems.indexwallet.core.storage.AppDatabase
import io.horizontalsystems.indexwallet.entities.BlockchainSetting
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.SyncMode
import io.horizontalsystems.indexwallet.entities.SyncModeSetting

class SyncModeSettingsManager(
        private val appConfigProvider: IAppConfigProvider,
        private val appDatabase: AppDatabase
) : ISyncModeSettingsManager {

    companion object {
        const val syncModeSettingKey: String = "sync_mode"
    }

    override fun defaultSyncModeSetting(coinType: CoinType): SyncModeSetting? {
        return appConfigProvider.syncModeSettings.firstOrNull { it.coinType == coinType }
    }

    override fun syncModeSetting(coinType: CoinType): SyncModeSetting? {
        val blockchainSetting = appDatabase.blockchainSettingDao().getSetting(coinType, syncModeSettingKey)
        return blockchainSetting?.let { SyncModeSetting(coinType, SyncMode.valueOf(it.value)) }
    }

    override fun updateSetting(syncModeSetting: SyncModeSetting) {
        appDatabase.blockchainSettingDao().insert(BlockchainSetting(syncModeSetting.coinType, syncModeSettingKey, syncModeSetting.syncMode.value))
    }

}
