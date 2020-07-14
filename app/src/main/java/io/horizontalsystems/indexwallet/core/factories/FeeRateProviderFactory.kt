package io.horizontalsystems.indexwallet.core.factories

import io.horizontalsystems.indexwallet.core.App
import io.horizontalsystems.indexwallet.core.IFeeRateProvider
import io.horizontalsystems.indexwallet.core.providers.*
import io.horizontalsystems.indexwallet.entities.Coin
import io.horizontalsystems.indexwallet.entities.CoinType

object FeeRateProviderFactory {
    private val feeRateProvider = App.feeRateProvider

    fun provider(coin: Coin): IFeeRateProvider? {
        return when (coin.type) {
            is CoinType.Bitcoin -> BitcoinFeeRateProvider(feeRateProvider)
            is CoinType.Litecoin -> LitecoinFeeRateProvider(feeRateProvider)
            is CoinType.BitcoinCash -> BitcoinCashFeeRateProvider(feeRateProvider)
            is CoinType.Dash -> DashFeeRateProvider(feeRateProvider)
            is CoinType.IndexChain -> IndexChainFeeRateProvider(feeRateProvider)
            is CoinType.Ethereum, is CoinType.Erc20 -> EthereumFeeRateProvider(feeRateProvider)
            else -> null
        }
    }

}
