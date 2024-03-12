package com.ivanov

import groovy.json.JsonSlurper

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

//@CompileStatic
class TopicController {

    TopicService topicService
    ArticleService articleService

    def index() {
        List<Topic> topics = topicService.findAll()
        [topicList: topics]
    }

    def addTopic() {
        String searchTerm = null
        String noticeSuccess = null
        String noticeError = null
        if (params.searchTerm != null) {
            searchTerm = params.searchTerm.trim()
        }
        println searchTerm

        List<Article> articles = new ArrayList<>()

        if (searchTerm != null) {

            Topic newTopic = null

            if (topicService.findAllTitle(searchTerm).size() == 0) {
                newTopic = new Topic()
                newTopic.setTitle(searchTerm)
                topicService.save(newTopic)
                articles = list(searchTerm)
                noticeSuccess = "Тема и её новости были добавлены в базу"
            } else {
                noticeError = "Тема с таким именем уже присутствует"
            }

            if (!articles.isEmpty()) {
                for (Article article : articles) {
                    article.setTopic(newTopic)
                    articleService.save(article)
                }
            }
        }
        [articleList: articles, noticeSuccess: noticeSuccess, noticeError: noticeError]
    }
    def edit() {
        [topic: topicService.get(Long.parseLong(params.id))]
    }

    def delete() {
        topicService.delete(params.id)
        redirect action: 'index'
    }

    List<Article> list(searchTerm) {
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

    def update() {
        String newTitle = null
        Topic topic = null
        Topic topicUpdate = null
        String notice = null
        List<Article> articles = new ArrayList<>()

        if (params.title != null) {
            newTitle = params.title.trim()
            topic = topicService.get(Long.parseLong(params.id))

            if (topic.title == newTitle) { // если тема не изменена то просто обновляем данные
                articleService.deleteByTopic(topic)
                topicUpdate = topicService.save(topic)
            } else if (topicService.findAllTitle(newTitle).size() == 0) { // если такой темы нет в базе,
                // то удаляем все новости прошлой темы, и меняем тему на новую
                articleService.deleteByTopic(topic)
                topic.setTitle(newTitle)
                topicUpdate = topicService.save(topic)
            }
        }
        // если новая тема успешно обновлена то сохраняем новости по данной теме
        if (topicUpdate != null) {
            notice = "тема обновлена"
            articles = list(newTitle)

            if (!articles.isEmpty()) {
                for (Article article : articles) {
                    article.setTopic(topicUpdate)
                    articleService.save(article)
                }
            }
        }
        def map = [topic: topicUpdate, articleList: articles, notice: notice]
        render view: 'edit', model: map
    }
}
