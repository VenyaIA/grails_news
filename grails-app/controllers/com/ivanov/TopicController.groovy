package com.ivanov

class TopicController {

    TopicService topicService

    def index() {
        List<Topic> topics = topicService.findAll()
        [topicList: topics]
    }

    def addTopic() {
        def map = topicService.addTopic(params)
        render view: 'addTopic', model: map
    }
    def edit() {
        Topic topic = topicService.get(Long.parseLong(params.id))
        [topic: topic]
    }

    def delete() {
        topicService.delete(params.id)
        redirect action: 'index'
    }

    def update() {
        def map = topicService.update(params)
        render view: 'edit', model: map
    }
}
