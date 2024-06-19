package com.bumba.cingami.app.core.platform

enum class Platform {
    ANDROID,
    IOS
}

expect fun getPlatform(): Platform