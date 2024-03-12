<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>List of Topic</title>
</head>

<body>
<div>
    <div class="content-news-list">
        <g:if test="${!topicList.isEmpty()}">
            <h2>Темы новостей</h2>
        </g:if>
        <g:else>
            <h2>Тем новостей пока нет</h2>
        </g:else>
        <div class="item">
            <g:link class="btn btn-secondary btn-lg" action="addTopic">Добавить новую тему новостей</g:link>

        </div>

        <g:each in="${topicList}" var="topic">
            <ul class="list-group">
                <li class="list-group-item item">

                    <div class="d-flex">

                        <div class="p-2 flex-grow-1 bd-highlight">
                            <div class="url-news">
                                <g:link action="edit" id="${topic.id}"><h3>${topic.title}</h3></g:link>
                            </div>
                        </div>

                        <div class="p-2 bd-highlight">
                            <g:link class="btn btn-outline-primary" action="edit" id="${topic.id}">Edit</g:link>
                            <g:link class="btn btn-outline-primary" action="delete" id="${topic.id}"
                                    onclick="return confirm('Вы уверены?');">Delete</g:link>
                        </div>

                    </div>
                </li>
            </ul>
        </g:each>
    </div>
</div>
</body>
</html>