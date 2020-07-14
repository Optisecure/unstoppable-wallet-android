package io.horizontalsystems.indexwallet.core.managers

import io.horizontalsystems.indexwallet.core.IBlockchainSettingsManager
import io.horizontalsystems.indexwallet.core.ICommunicationSettingsManager
import io.horizontalsystems.indexwallet.core.IDerivationSettingsManager
import io.horizontalsystems.indexwallet.core.ISyncModeSettingsManager
import io.horizontalsystems.indexwallet.entities.*

class BlockchainSettingsManager(
        private val derivationSettingsManager: IDerivationSettingsManager,
        private val syncModeSettingsManager: ISyncModeSettingsManager,
        private val communicationSettingsManager: ICommunicationSettingsManager
) : IBlockchainSettingsManager {

    override fun derivationSetting(coinType: CoinType): DerivationSetting? {
        return derivationSettingsManager.derivationSetting(coinType)
                ?: derivationSettingsManager.defaultDerivationSetting(coinType)
    }

    override fun syncModeSetting(coinType: CoinType): SyncModeSetting? {
        return syncModeSettingsManager.syncModeSetting(coinType)
                ?: syncModeSettingsManager.defaultSyncModeSetting(coinType)
    }

    override fun communicationSetting(coinType: CoinType): CommunicationSetting? {
        return communicationSettingsManager.communicationSetting(coinType)
                ?: communicationSettingsManager.defaultCommunicationSetting(coinType)
    }

    override fun saveSetting(derivationSetting: DerivationSetting) {
        derivationSettingsManager.updateSetting(derivationSetting)
    }

    override fun saveSetting(syncModeSetting: SyncModeSetting) {
        syncModeSettingsManager.updateSetting(syncModeSetting)
    }

    override fun saveSetting(communicationSetting: CommunicationSetting) {
        communicationSettingsManager.updateSetting(communicationSetting)
    }

    override fun initializeSettings(coinType: CoinType) {
        if (derivationSettingsManager.derivationSetting(coinType) == null) {
            derivationSettingsManager.defaultDerivationSetting(coinType)?.let {
                derivationSettingsManager.updateSetting(it)
            }
        }
        if (syncModeSettingsManager.syncModeSetting(coinType) == null) {
            syncModeSettingsManager.defaultSyncModeSetting(coinType)?.let {
                syncModeSettingsManager.updateSetting(it)
            }
        }
        if (communicationSettingsManager.communicationSetting(coinType) == null) {
            communicationSettingsManager.defaultCommunicationSetting(coinType)?.let {
                communicationSettingsManager.updateSetting(it)
            }
        }
    }

    override fun initializeSettingsWithDefault(coinType: CoinType) {
        derivationSettingsManager.defaultDerivationSetting(coinType)?.let {
            derivationSettingsManager.updateSetting(it)
        }
        syncModeSettingsManager.defaultSyncModeSetting(coinType)?.let {
            it.syncMode = SyncMode.New
            syncModeSettingsManager.updateSetting(it)
        }
        communicationSettingsManager.defaultCommunicationSetting(coinType)?.let {
            communicationSettingsManager.updateSetting(it)
        }
    }

}
