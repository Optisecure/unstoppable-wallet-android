package io.horizontalsystems.indexwallet.modules.tor

import io.horizontalsystems.indexwallet.core.managers.TorStatus
import io.horizontalsystems.core.SingleLiveEvent

class TorStatusView: TorStatusModule.View {

    val torConnectionStatus = SingleLiveEvent<TorStatus>()

    override fun updateConnectionStatus(connectionStatus: TorStatus) {
        torConnectionStatus.postValue(connectionStatus)
    }
}