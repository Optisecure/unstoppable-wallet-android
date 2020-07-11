package io.horizontalsystems.bankwallet.modules.fulltransactioninfo.providers

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.horizontalsystems.bankwallet.modules.fulltransactioninfo.FullTransactionInfoModule
import io.horizontalsystems.bankwallet.modules.fulltransactioninfo.FullTransactionInfoModule.Request.GetRequest

class InsightIndexChainProvider : FullTransactionInfoModule.BitcoinForksProvider {
    private val baseApiUrl = "https://insight.indexchain.org/insight-api-zcoin"

    override val name = "insight.indexchain.org"
    override val pingUrl = "$baseApiUrl/blocks/"
    override val isTrusted: Boolean = true

    override fun url(hash: String): String {
        return "https://insight.indexchain.org/tx/$hash"
    }

    override fun apiRequest(hash: String): FullTransactionInfoModule.Request {
        return GetRequest("$baseApiUrl/tx/$hash")
    }

    override fun convert(json: JsonObject): BitcoinResponse {
        return Gson().fromJson(json, InsightResponse::class.java)
    }
}
