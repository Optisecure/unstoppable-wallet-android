package io.horizontalsystems.indexwallet.modules.contact.appstatus

import io.horizontalsystems.indexwallet.core.IAppStatusManager
import io.horizontalsystems.indexwallet.core.IClipboardManager

class AppStatusInteractor(
        private val appStatusManager: IAppStatusManager,
        private val clipboardManager: IClipboardManager
) : AppStatusModule.IInteractor {

    override val status: Map<String, Any>
        get() = appStatusManager.status

    override fun copyToClipboard(text: String) {
        clipboardManager.copyText(text)
    }

}
