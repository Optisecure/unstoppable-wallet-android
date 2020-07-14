package io.horizontalsystems.indexwallet.modules.receive.viewitems

import io.horizontalsystems.indexwallet.entities.Coin

data class AddressItem(var address: String, var addressType: String?,  var coin: Coin)
