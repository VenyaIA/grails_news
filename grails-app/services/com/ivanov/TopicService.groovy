package com.ivanov

import grails.gorm.transactions.Transactional

@Transactional
class TopicService {

    Topic save(Topic topic) {
        topic.save()
    }

    Topic get(Long id) {
        Topic.get(id)
    }

    List<Topic> findAll() {
        Topic.findAll()
    }

    List<Topic> findAllTitle(String title) {
        Topic.findAllByTitle(title)
    }

    void delete(id) {
        Topic topic = Topic.get(id)
        topic.delete()
    }
}
