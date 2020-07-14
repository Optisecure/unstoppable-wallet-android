package io.horizontalsystems.indexwallet.modules.send.eos

import io.horizontalsystems.indexwallet.core.ISendEosAdapter
import io.horizontalsystems.indexwallet.modules.send.SendModule
import io.reactivex.Single
import java.math.BigDecimal

class SendEosInteractor(private val adapter: ISendEosAdapter) : SendModule.ISendEosInteractor {

    override val availableBalance: BigDecimal
        get() = adapter.availableBalance

    override fun validate(account: String) {
        adapter.validate(account)
    }

    override fun send(amount: BigDecimal, account: String, memo: String?): Single<Unit> {
        return adapter.send(amount, account, memo)
    }

}
