package org.softeer.robocar.data.service.firebase

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.json.Json

class RoboCarFireBaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // TODO 푸시알람 타입에 따라 다르게 보내기
        sendRequestCarPoolMessage(message)
    }

    private fun sendRequestCarPoolMessage(message: RemoteMessage){
        val intent = Intent("org.softeer.robocar.NEW_MESSAGE")

        intent.putExtra("guestId", message.data["guestId"])
        intent.putExtra("maleCount", message.data["maleCount"])
        intent.putExtra("femaleCount", message.data["femaleCount"])
        intent.putExtra("guestAddress", message.data["guestAddress"])
        intent.putExtra("nickname", message.data["nickname"])
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}