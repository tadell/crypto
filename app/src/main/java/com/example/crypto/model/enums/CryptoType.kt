package com.example.crypto.model.enums


//enum for filtering items in api by cryptocurrency

enum class CryptoType (val crypto: String) {
    ALL("all (default)"),
    COINS("coins"),
    TOKENS("tokens")

}