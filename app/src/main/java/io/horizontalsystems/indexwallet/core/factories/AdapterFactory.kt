package io.horizontalsystems.indexwallet.core.factories

import android.content.Context
import io.horizontalsystems.indexwallet.core.IAdapter
import io.horizontalsystems.indexwallet.core.IBlockchainSettingsManager
import io.horizontalsystems.indexwallet.core.IEosKitManager
import io.horizontalsystems.indexwallet.core.IEthereumKitManager
import io.horizontalsystems.indexwallet.core.adapters.*
import io.horizontalsystems.indexwallet.core.managers.BinanceKitManager
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.Wallet

class AdapterFactory(
        private val context: Context,
        private val testMode: Boolean,
        private val ethereumKitManager: IEthereumKitManager,
        private val eosKitManager: IEosKitManager,
        private val binanceKitManager: BinanceKitManager,
        private val blockchainSettingsManager: IBlockchainSettingsManager) {

    fun adapter(wallet: Wallet): IAdapter? {
        val derivation = blockchainSettingsManager.derivationSetting(wallet.coin.type)?.derivation
        val syncMode = blockchainSettingsManager.syncModeSetting(wallet.coin.type)?.syncMode
        val communicationMode = blockchainSettingsManager.communicationSetting(wallet.coin.type)?.communicationMode

        return when (val coinType = wallet.coin.type) {
            is CoinType.Bitcoin -> BitcoinAdapter(wallet, derivation, syncMode, testMode)
            is CoinType.Litecoin -> LitecoinAdapter(wallet, derivation, syncMode, testMode)
            is CoinType.BitcoinCash -> BitcoinCashAdapter(wallet, syncMode, testMode)
            is CoinType.Dash -> DashAdapter(wallet, syncMode, testMode)
            is CoinType.IndexChain -> IndexchainAdapter(wallet, derivation, syncMode, testMode)
            is CoinType.Eos -> EosAdapter(coinType, eosKitManager.eosKit(wallet), wallet.coin.decimal)
            is CoinType.Binance -> BinanceAdapter(binanceKitManager.binanceKit(wallet), coinType.symbol)
            is CoinType.Ethereum -> EthereumAdapter(ethereumKitManager.ethereumKit(wallet, communicationMode))
            is CoinType.Erc20 -> Erc20Adapter(context, ethereumKitManager.ethereumKit(wallet, communicationMode), wallet.coin.decimal, coinType.fee, coinType.address, coinType.minimumRequiredBalance, coinType.minimumSendAmount)
        }
    }

    fun unlinkAdapter(adapter: IAdapter) {
        when (adapter) {
            is EthereumBaseAdapter -> {
                ethereumKitManager.unlink()
            }
            is EosAdapter -> {
                eosKitManager.unlink()
            }
            is BinanceAdapter -> {
                binanceKitManager.unlink()
            }
        }
    }
}
