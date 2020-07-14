package io.horizontalsystems.indexwallet.modules.info

import io.horizontalsystems.indexwallet.core.IClipboardManager

class InfoInteractor(private var clipboardManager: IClipboardManager) : InfoModule.IInteractor {

    override fun onCopy(value: String) {
        clipboardManager.copyText(value)
    }

}
