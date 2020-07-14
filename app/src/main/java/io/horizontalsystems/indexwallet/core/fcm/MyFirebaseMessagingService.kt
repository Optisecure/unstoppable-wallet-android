package io.horizontalsystems.indexwallet.core.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import io.horizontalsystems.indexwallet.core.App

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        App.priceAlertManager.enablePriceAlerts()
    }
}
