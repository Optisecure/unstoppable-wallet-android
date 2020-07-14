package io.horizontalsystems.indexwallet.modules.managecoins

import io.horizontalsystems.core.SingleLiveEvent
import io.horizontalsystems.indexwallet.entities.PredefinedAccountType

class ManageWalletsRouter: ManageWalletsModule.IRouter {

    val openRestoreModule = SingleLiveEvent<PredefinedAccountType>()
    val closeLiveDate = SingleLiveEvent<Void>()

    override fun openRestore(predefinedAccountType: PredefinedAccountType) {
        openRestoreModule.postValue(predefinedAccountType)
    }

    override fun close() {
        closeLiveDate.call()
    }
}
