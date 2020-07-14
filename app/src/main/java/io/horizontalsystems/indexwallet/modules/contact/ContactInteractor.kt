package io.horizontalsystems.indexwallet.modules.contact

import io.horizontalsystems.indexwallet.core.IAppConfigProvider
import io.horizontalsystems.indexwallet.core.IClipboardManager

class ContactInteractor(
        private val appConfigProvider: IAppConfigProvider,
        private var clipboardManager: IClipboardManager
) : ContactModule.IInteractor {

    override val email get() = appConfigProvider.reportEmail
    override val walletHelpTelegramGroup get() = appConfigProvider.walletHelpTelegramGroup

    override fun copyToClipboard(value: String) {
        clipboardManager.copyText(value)
    }

}
