package com.ivanov

class ArticleController {

    ArticleService articleService


    def index() {
        def map = articleService.ArticleList(params)
        render view: 'index', model: map
    }

    def delete() {
        articleService.delete(params.id)
        redirect action: 'index', method: 'GET'
    }
}
