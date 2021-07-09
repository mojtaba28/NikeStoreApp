package com.example.nikestore.model

data class Comment(
        val author: Author,
        val content: String,
        val date: String,
        val id: Int,
        val title: String
)

class Author(val email:String)


