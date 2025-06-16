package org.example.aplicaciongestion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform