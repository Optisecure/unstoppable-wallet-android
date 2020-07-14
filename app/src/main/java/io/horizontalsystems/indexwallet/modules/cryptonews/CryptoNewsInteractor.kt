package io.horizontalsystems.indexwallet.modules.cryptonews

import io.horizontalsystems.indexwallet.core.IRateManager
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CryptoNewsInteractor(private val xRateManager: IRateManager) : CryptoNewsModule.Interactor {

    var delegate: CryptoNewsModule.InteractorDelegate? = null

    private var disposable: Disposable? = null

    override fun getPosts(coinCode: String) {
        disposable = xRateManager.getCryptoNews(coinCode)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    delegate?.onReceivePosts(it)
                }, {
                    delegate?.onError(it)
                })
    }

    override fun clear() {
        disposable?.dispose()
        disposable = null
    }
}
