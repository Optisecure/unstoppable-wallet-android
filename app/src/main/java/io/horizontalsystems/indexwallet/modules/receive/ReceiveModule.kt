package io.horizontalsystems.indexwallet.modules.receive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.indexwallet.core.App
import io.horizontalsystems.indexwallet.entities.Wallet
import io.horizontalsystems.indexwallet.modules.receive.viewitems.AddressItem
import io.horizontalsystems.indexwallet.ui.helpers.TextHelper

object ReceiveModule {

    interface IView {
        fun showAddress(address: AddressItem)
        fun showError(error: Int)
        fun showCopied()
        fun setHint(hint: Int, hintDetails: String?)
    }

    interface IViewDelegate {
        fun viewDidLoad()
        fun onShareClick()
        fun onAddressClick()
    }

    interface IInteractor {
        fun getReceiveAddress()
        fun copyToClipboard(coinAddress: String)
    }

    interface IInteractorDelegate {
        fun didReceiveAddress(address: AddressItem)
        fun didFailToReceiveAddress(exception: Exception)
        fun didCopyToClipboard()
    }

    interface IRouter{
        fun shareAddress(address: String)
    }

    class Factory(private val wallet: Wallet) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val view = ReceiveView()
            val router = ReceiveRouter()
            val interactor = ReceiveInteractor(wallet, App.adapterManager, TextHelper)
            val presenter = ReceivePresenter(view, router, interactor)

            interactor.delegate = presenter

            return presenter as T
        }
    }

}