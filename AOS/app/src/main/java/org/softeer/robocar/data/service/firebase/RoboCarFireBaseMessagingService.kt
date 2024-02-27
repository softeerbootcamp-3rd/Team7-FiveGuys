package org.softeer.robocar.data.service.firebase

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class RoboCarFireBaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val type = message.data["type"]
        if(type == null)
            return

        sendRequestCarPoolMessage(message)
    }

    private fun sendRequestCarPoolMessage(message: RemoteMessage){
        val intent = Intent("org.softeer.robocar.NEW_MESSAGE")

        intent.putExtra("guestId", message.data["guestId"])
        intent.putExtra("maleCount", message.data["maleCount"])
        intent.putExtra("femaleCount", message.data["femaleCount"])
        intent.putExtra("guestAddress", message.data["guestDestAddress"])
        intent.putExtra("nickname", message.data["guestNickname"])
        intent.putExtra("type", message.data["type"])
        intent.putExtra("carPoolId", message.data["in_operation_id"])
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object{
        const val CAR_POOL_REJECT = "CAR_POOL_REJECT"
        const val CAR_POOL_REQUEST = "CAR_POOL_REQUEST"
        const val CAR_POOL_ACCEPT = "CAR_POOL_ACCEPT"
    }
}