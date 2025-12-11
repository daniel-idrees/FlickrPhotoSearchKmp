package org.example.flikrphotosearch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform