package com.ivanov

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Transactional
class NewsAPIService {

    List<Article> listArticle(searchTerm) {
        String searchTermReplace = searchTerm.replace(' ', '+')

        String baseUrl = "https://newsapi.org/v2/"
        String apiKey = "6723cea65b394fbdaa9ffdaa887e205b"
        String url = "${baseUrl}everything?q=${searchTermReplace}&apiKey=${apiKey}"

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build()

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build()

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString())
        client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200) {
            def jsonSlurper = new JsonSlurper()
            def json = jsonSlurper.parseText(response.body())
            json.articles.collect { article ->
                new Article(
                        name: article.source.name,
                        title: article.title,
                        author: article.author,
                        description: article.description,
                        url: article.url,
                        urlToImage: article.urlToImage,
                        publishedAt: article.publishedAt
                )
            }

        } else {
            log.error("API request failed with status code: ${response.statusCode()}")
            return []
        }
    }
}
