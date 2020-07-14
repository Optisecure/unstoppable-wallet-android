package io.horizontalsystems.indexwallet.modules.restore.words

import io.horizontalsystems.indexwallet.core.managers.WordsManager
import io.horizontalsystems.hdwalletkit.Mnemonic

class RestoreWordsInteractor(private val wordsManager: WordsManager) : RestoreWordsModule.Interactor {

    var delegate: RestoreWordsModule.InteractorDelegate? = null

    override fun validate(words: List<String>) {
        try {
            wordsManager.validate(words)
            delegate?.didValidate()
        } catch (e: Mnemonic.MnemonicException) {
            delegate?.didFailToValidate(e)
        }
    }
}
