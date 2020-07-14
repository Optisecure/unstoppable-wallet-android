package io.horizontalsystems.indexwallet.modules.restore

import io.horizontalsystems.core.SingleLiveEvent
import io.horizontalsystems.indexwallet.entities.PredefinedAccountType

class RestoreView : RestoreModule.IView {

    val reloadLiveEvent = SingleLiveEvent<List<PredefinedAccountType>>()
    val showErrorLiveEvent = SingleLiveEvent<Exception>()

    override fun reload(items: List<PredefinedAccountType>) {
        reloadLiveEvent.postValue(items)
    }

    override fun showError(ex: Exception) {
        showErrorLiveEvent.postValue(ex)
    }

}
