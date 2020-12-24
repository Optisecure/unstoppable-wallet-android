package io.horizontalsystems.indexwallet.modules.fulltransactioninfo.dataprovider

import io.horizontalsystems.indexwallet.core.INetworkManager
import io.horizontalsystems.indexwallet.core.ITransactionDataProviderManager
import io.horizontalsystems.indexwallet.entities.Coin
import io.horizontalsystems.indexwallet.modules.fulltransactioninfo.FullTransactionInfoModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.URL

class DataProviderSettingsInteractor(private val dataProviderManager: ITransactionDataProviderManager, val networkManager: INetworkManager)
    : DataProviderSettingsModule.Interactor {

    val disposables = CompositeDisposable()
    var delegate: DataProviderSettingsModule.InteractorDelegate? = null

    override fun pingProvider(name: String, url: String, isTrusted: Boolean) {
        val uri = URL(url)
        val host = "${uri.protocol}://${uri.host}"

        disposables.add(networkManager.ping(host, url, isSafeCall = isTrusted)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    delegate?.onPingSuccess(name)
                }, {
                    delegate?.onPingFailure(name)
                }))
    }

    override fun providers(coin: Coin): List<FullTransactionInfoModule.Provider> {
        return dataProviderManager.providers(coin)
    }

    override fun baseProvider(coin: Coin): FullTransactionInfoModule.Provider {
        return dataProviderManager.baseProvider(coin)
    }

    override fun setBaseProvider(name: String, coin: Coin) {
        dataProviderManager.setBaseProvider(name, coin)

        delegate?.onSetDataProvider()
    }
}