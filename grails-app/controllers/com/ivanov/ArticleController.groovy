package com.ivanov


import java.time.ZoneId
import java.time.format.DateTimeFormatter


//@CompileStatic
class ArticleController {

    ArticleService articleService
    TopicService topicService

    def index() {
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

            mapArticles = articles.groupBy{
                it.publishedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            }
        }

        def map = [articleList: articles, searchTerm: searchTerm, mapArticles: mapArticles, topics: topics, topicSel: topicSel]
        render view: 'index', model: map
    }

    def show() {
        [article: articleService.get(params.id)]
    }

    def edit() {
        [article: articleService.get(params.id)]
    }

    def delete() {
        articleService.delete(params.id)
        redirect action: 'index', method: 'GET'
    }
}
