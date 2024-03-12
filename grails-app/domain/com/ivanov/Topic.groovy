package com.ivanov

class Topic {

    String title

    static hasMany = [article: Article]

    static mapping = {
        version false
    }

    static constraints = {
        title size: 1..1000, blank: false, unique: true
    }
}
