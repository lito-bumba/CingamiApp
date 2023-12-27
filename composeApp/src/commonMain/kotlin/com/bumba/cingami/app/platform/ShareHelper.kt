package com.bumba.cingami.app.platform

expect class ShareHelper {
    suspend fun share(text: String)
}