package com.example.crypto.model.enums


//enum for sorting direction in api by tag
enum class TagType (val tag: String){
    ALL("all (default)"),
    DEFI("defi"),
    FILESHARING("file sharing")
}