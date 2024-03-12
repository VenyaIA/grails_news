package com.ivanov

import grails.gorm.transactions.Transactional

@Transactional
class ArticleService {
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

}
