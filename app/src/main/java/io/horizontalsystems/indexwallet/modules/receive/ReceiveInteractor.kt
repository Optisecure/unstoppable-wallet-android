package io.horizontalsystems.indexwallet.modules.receive

import io.horizontalsystems.indexwallet.core.IAdapterManager
import io.horizontalsystems.indexwallet.core.IClipboardManager
import io.horizontalsystems.indexwallet.entities.Wallet
import io.horizontalsystems.indexwallet.modules.receive.viewitems.AddressItem

class ReceiveInteractor(
        private var wallet: Wallet,
        private var adapterManager: IAdapterManager,
        private var clipboardManager: IClipboardManager
) : ReceiveModule.IInteractor {

    var delegate: ReceiveModule.IInteractorDelegate? = null

    override fun getReceiveAddress() {
        adapterManager.getReceiveAdapterForWallet(wallet)?.let { adapter ->
            val addressItem = AddressItem(adapter.receiveAddress,
                                          adapter.getReceiveAddressType(wallet), wallet.coin)
            delegate?.didReceiveAddress(addressItem)
        }
    }

    override fun copyToClipboard(coinAddress: String) {
        clipboardManager.copyText(coinAddress)
        delegate?.didCopyToClipboard()
    }
}
