package com.bumba.cingami.app.core.platform

expect class ShareHelper {
    suspend fun share(text: String)
}