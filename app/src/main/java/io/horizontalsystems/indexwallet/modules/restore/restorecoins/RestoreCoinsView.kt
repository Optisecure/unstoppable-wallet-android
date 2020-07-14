package io.horizontalsystems.indexwallet.modules.restore.restorecoins

import androidx.lifecycle.MutableLiveData
import io.horizontalsystems.indexwallet.entities.AccountType
import io.horizontalsystems.indexwallet.entities.Coin
import io.horizontalsystems.indexwallet.modules.createwallet.view.CoinManageViewItem
import io.horizontalsystems.core.SingleLiveEvent

class RestoreCoinsView : RestoreCoinsModule.IView {
    val coinsLiveData = MutableLiveData<List<CoinManageViewItem>>()
    val proceedButtonEnabled = MutableLiveData<Boolean>()
    val showDerivationSelectorDialog = SingleLiveEvent<Triple<List<AccountType.Derivation>, AccountType.Derivation, Coin>>()

    override fun setItems(coinViewItems: List<CoinManageViewItem>) {
        coinsLiveData.postValue(coinViewItems)
    }

    override fun setProceedButton(enabled: Boolean) {
        proceedButtonEnabled.postValue(enabled)
    }

    override fun showDerivationSelectorDialog(derivationOptions: List<AccountType.Derivation>, selected: AccountType.Derivation, coin: Coin) {
        showDerivationSelectorDialog.postValue(Triple(derivationOptions, selected, coin))
    }

}
