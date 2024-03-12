package com.ivanov

class Article {

    String name
    String title
    String author
    String description
    String url
    String urlToImage
    Date publishedAt

    static belongsTo = [topic: Topic]

    static mapping = {
        version false
    }

    static constraints = {
        name size: 1..255, blank: false
        title size: 1..1000, blank: false
        author size: 0..250, nullable: true
        description size: 0..1000, nullable: true
        url size: 1..1000, blank: false
        urlToImage size: 0..1000, nullable: true
    }
}
