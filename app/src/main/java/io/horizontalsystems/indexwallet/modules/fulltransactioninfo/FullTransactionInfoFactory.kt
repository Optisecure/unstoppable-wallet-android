package io.horizontalsystems.indexwallet.modules.fulltransactioninfo

import io.horizontalsystems.indexwallet.core.App
import io.horizontalsystems.indexwallet.core.INetworkManager
import io.horizontalsystems.indexwallet.core.ITransactionDataProviderManager
import io.horizontalsystems.indexwallet.entities.CoinType
import io.horizontalsystems.indexwallet.entities.Wallet
import io.horizontalsystems.indexwallet.modules.fulltransactioninfo.adapters.FullTransactionBinanceAdapter
import io.horizontalsystems.indexwallet.modules.fulltransactioninfo.adapters.FullTransactionBitcoinAdapter
import io.horizontalsystems.indexwallet.modules.fulltransactioninfo.adapters.FullTransactionEosAdapter
import io.horizontalsystems.indexwallet.modules.fulltransactioninfo.adapters.FullTransactionEthereumAdapter

class FullTransactionInfoFactory(private val networkManager: INetworkManager, private val dataProviderManager: ITransactionDataProviderManager)
    : FullTransactionInfoModule.ProviderFactory {

    override fun providerFor(wallet: Wallet): FullTransactionInfoModule.FullProvider {
        val coin = wallet.coin
        val baseProvider = dataProviderManager.baseProvider(coin)

        val provider: FullTransactionInfoModule.Provider
        val adapter: FullTransactionInfoModule.Adapter

        when {
            // BTC, BTCt
            coin.type is CoinType.Bitcoin -> {
                val providerBTC = dataProviderManager.bitcoin(baseProvider.name)

                provider = providerBTC
                adapter = FullTransactionBitcoinAdapter(providerBTC, coin, "satoshi")
            }
            coin.type is CoinType.Litecoin -> {
                val providerLTC = dataProviderManager.litecoin(baseProvider.name)

                provider = providerLTC
                adapter = FullTransactionBitcoinAdapter(providerLTC, coin, "satoshi")
            }
            // BCH, BCHt
            coin.type is CoinType.BitcoinCash -> {
                val providerBCH = dataProviderManager.bitcoinCash(baseProvider.name)

                provider = providerBCH
                adapter = FullTransactionBitcoinAdapter(providerBCH, coin, "satoshi")
            }
            // DASH, DASHt
            coin.type is CoinType.Dash -> {
                val providerDASH = dataProviderManager.dash(baseProvider.name)

                provider = providerDASH
                adapter = FullTransactionBitcoinAdapter(providerDASH, coin, "duff")
            }
            // IDX,IDXt
            coin.type is CoinType.IndexChain -> {
                val providerIDX = dataProviderManager.indexchain(baseProvider.name)
                provider = providerIDX
                adapter = FullTransactionBitcoinAdapter(providerIDX, coin, "satoshi")
            }
            // BNB
            coin.type is CoinType.Binance -> {
                val providerBinance = dataProviderManager.binance(baseProvider.name)

                provider = providerBinance
                adapter = FullTransactionBinanceAdapter(providerBinance, App.feeCoinProvider, coin)
            }
            //EOS
            coin.type is CoinType.Eos -> {
                val providerEos = dataProviderManager.eos(baseProvider.name)

                provider = providerEos
                adapter = FullTransactionEosAdapter(providerEos, wallet)
            }
            // ETH, ETHt
            else -> {
                val providerETH = dataProviderManager.ethereum(baseProvider.name)

                provider = providerETH
                adapter = FullTransactionEthereumAdapter(providerETH, App.feeCoinProvider, coin)
            }
        }

        return FullTransactionInfoProvider(networkManager, adapter, provider)
    }
}
