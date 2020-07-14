package io.horizontalsystems.indexwallet.modules.addressformat

import io.horizontalsystems.indexwallet.core.*
import io.horizontalsystems.indexwallet.entities.AccountType.Derivation
import io.horizontalsystems.indexwallet.entities.Coin
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.DerivationSetting
import io.horizontalsystems.indexwallet.entities.Wallet

class AddressFormatSettingsInteractor(
        private val derivationSettingsManager: IDerivationSettingsManager,
        private val coinManager: ICoinManager,
        private val walletManager: IWalletManager,
        private val adapterManager: IAdapterManager
) : AddressFormatSettingsModule.IInteractor {

    override fun derivation(coinType: CoinType): Derivation {
        return derivationSettingsManager.derivationSetting(coinType)?.derivation
                ?: derivationSettingsManager.defaultDerivationSetting(coinType)?.derivation
                ?: throw Exception("No derivation found for ${coinType.javaClass.simpleName}")
    }

    override fun getCoin(coinType: CoinType): Coin {
        return coinManager.coins.first { it.type == coinType }
    }

    override fun getWalletForUpdate(coinType: CoinType): Wallet? {
        return walletManager.wallets.firstOrNull { it.coin.type == coinType }
    }

    override fun saveDerivation(derivationSetting: DerivationSetting) {
        derivationSettingsManager.updateSetting(derivationSetting)
    }

    override fun reSyncWallet(wallet: Wallet) {
        adapterManager.refreshAdapters(listOf(wallet))
    }
}
