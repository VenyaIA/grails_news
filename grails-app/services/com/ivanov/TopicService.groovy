package com.ivanov

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class TopicService {

    ArticleService articleService
    NewsAPIService newsAPIService

    Topic get(Long id) {
        Topic.get(id)
    }

    List<Topic> findAll() {
        Topic.findAll()
    }

    void delete(id) {
        Topic topic = Topic.get(id)
        topic.delete()
    }

    LinkedHashMap<String, Object> addTopic(GrailsParameterMap params) {

        String searchTerm = null
        Boolean noticeSuccess = null
        Topic newTopic = null
        List<Article> articles = new ArrayList<>()

        if (params.searchTerm != null) {
            searchTerm = params.searchTerm.trim()
        }

        if (searchTerm != null) {
            newTopic = new Topic()
            newTopic.setTitle(searchTerm)

            if (newTopic.validate()) {
                newTopic.save()
                articles = newsAPIService.listArticle(searchTerm)
                noticeSuccess = true
            } else {
                newTopic.errors.allErrors.each {
                    println it
                }
            }

            if (!articles.isEmpty()) {
                for (Article article : articles) {
                    article.setTopic(newTopic)
                    articleService.save(article)
                }
            }
        }
        [articleList: articles, topic: newTopic, noticeSuccess: noticeSuccess]
    }


    LinkedHashMap<String, Object> update(GrailsParameterMap params) {

        String newTitle = null
        Topic topicUpdate = get(Long.parseLong(params.id))
        Boolean noticeSuccess = false
        List<Article> articles = new ArrayList<>()

        if (params.title != null) {
            newTitle = params.title.trim()
        }


        if (newTitle != null) {

            if (topicUpdate.title != newTitle) {
                topicUpdate.setTitle(newTitle)
            }

            if (topicUpdate.validate()) {
                topicUpdate = topicUpdate.save()
                articleService.deleteByTopic(topicUpdate)
                noticeSuccess = true
                articles = newsAPIService.listArticle(newTitle)
            }


            if (!articles.isEmpty()) {
                for (Article article : articles) {
                    article.setTopic(topicUpdate)
                    articleService.save(article)
                }
            }
        }

        [topic: topicUpdate, articleList: articles, noticeSuccess: noticeSuccess]
    }
}
