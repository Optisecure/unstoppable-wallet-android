package io.horizontalsystems.indexwallet.modules.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.horizontalsystems.indexwallet.entities.Wallet
import io.horizontalsystems.core.SingleLiveEvent

class TransactionsViewModel : ViewModel(), TransactionsModule.IView, TransactionsModule.IRouter {

    lateinit var delegate: TransactionsModule.IViewDelegate

    val filterItems = MutableLiveData<List<Wallet?>>()
    val transactionViewItemLiveEvent = SingleLiveEvent<TransactionViewItem>()
    val items = MutableLiveData<List<TransactionViewItem>>()
    val reloadTransactions = SingleLiveEvent<Unit>()
    val showSyncing = MutableLiveData<Boolean>()

    override fun showFilters(filters: List<Wallet?>) {
        filterItems.postValue(filters)
    }

    override fun showTransactions(items: List<TransactionViewItem>) {
        this.items.postValue(items)
    }

    override fun reloadTransactions() {
        reloadTransactions.postValue(Unit)
    }

    override fun showNoTransactions() {
        items.postValue(listOf())
    }

    override fun showSyncing() {
        showSyncing.postValue(true)
    }

    override fun hideSyncing() {
        showSyncing.postValue(false)
    }

    override fun openTransactionInfo(transactionViewItem: TransactionViewItem) {
        transactionViewItemLiveEvent.postValue(transactionViewItem)
    }

    override fun onCleared() {
        delegate.onClear()
    }
}