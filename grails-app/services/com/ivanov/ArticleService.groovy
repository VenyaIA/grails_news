package com.ivanov

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Transactional
class ArticleService {

    TopicService topicService

    Article get(id) {
        Article.get(id)
    }

    Article save(Article article) {
        article.save()
    }

    List<Article> findAll() {
        Article.findAll()
    }

    void delete(id) {
        Article article = Article.get(id)
        article.delete()
    }

    void deleteByTopic(Topic topic) {
        for (Article article : Article.findAllByTopic(topic)) {
            article.delete()
        }
    }

    LinkedHashMap<String, Object> ArticleList(GrailsParameterMap params) {
        String searchTerm = params.searchTerm

        String topicSel = params.topicSelect
        Long topicId = null
        if (topicSel != '' && topicSel != null) {
            topicId = Long.parseLong(topicSel)
            topicSel = topicService.get(topicId).title
        }

        List<Topic> topics = topicService.findAll()
        List<Article> articles = new ArrayList<>()
        Map<String, List<Article>> mapArticles = new LinkedHashMap<>()

        def a = Article.createCriteria()
        if (searchTerm != null) {

            if (topicId != null) {
                articles = a.list {
                    eq("topic.id", topicId)
                    ilike("title", searchTerm + "%")
                    order("publishedAt", "desc")
                }
            } else {
                articles = a.list {
                    ilike("title", searchTerm + "%")
                    order("publishedAt", "desc")
                }
            }

            mapArticles = articles.groupBy(({
                it.publishedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            } as Closure<Date>))

        } else {

            if (topicId != null) {
                articles = a.list {
                    eq("topic.id", topicId)
                    order("publishedAt", "desc")
                }
            } else {
                articles = a.list {
                    order("publishedAt", "desc")
                }
            }

            mapArticles = articles.groupBy {
                it.publishedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

            }
        }
        [articleList: articles, searchTerm: searchTerm, mapArticles: mapArticles, topics: topics, topicSel: topicSel]

    }

}
