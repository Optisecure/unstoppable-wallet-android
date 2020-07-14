package io.horizontalsystems.indexwallet.core.factories

import io.horizontalsystems.indexwallet.core.IAddressParser
import io.horizontalsystems.indexwallet.core.utils.AddressParser
import io.horizontalsystems.indexwallet.entities.Coin
import io.horizontalsystems.indexwallet.entities.CoinType

class AddressParserFactory {
    fun parser(coin: Coin): IAddressParser {
        return when (coin.type) {
            is CoinType.Bitcoin -> AddressParser("bitcoin", true)
            is CoinType.Litecoin -> AddressParser("litecoin", true)
            is CoinType.BitcoinCash -> AddressParser("bitcoincash", false)
            is CoinType.Dash -> AddressParser("dash", true)
            is CoinType.IndexChain -> AddressParser("index", true)
            is CoinType.Ethereum, is CoinType.Erc20 -> AddressParser("ethereum", true)
            is CoinType.Eos -> AddressParser("eos", true)
            is CoinType.Binance -> AddressParser("binance", true)
        }
    }

}
