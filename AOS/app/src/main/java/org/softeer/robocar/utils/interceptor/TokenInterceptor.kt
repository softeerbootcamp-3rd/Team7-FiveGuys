package org.softeer.robocar.utils.interceptor

import okhttp3.*
import okio.IOException
import org.softeer.robocar.di.RoboCarApplication


class TokenAuthenticator : Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        val newToken = RoboCarApplication.token

        return if (newToken != null) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null
        }
    }
}
