<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>List of Topic</title>
</head>
<body>
<div class="search-news">
    <h1>Добавление тем новостей</h1>
    <g:if test="${noticeSuccess}">
        <div class="alert alert-success" role="alert">
            Тема и её новости были добавлены в базу
        </div>
    </g:if>
    <g:hasErrors bean="${topic}">
        <div class="alert alert-danger" role="alert">
            <g:if test="${topic.title == ''}">
                Тема не может быть пустой
            </g:if>
            <g:else>
                Тема с именем "${topic.title}" уже присутствует
            </g:else>
        </div>
    </g:hasErrors>

    <g:form name="index" action="addTopic" method="POST">
        <div class="d-flex">
            <g:textField type="search" class="form-control" style="background: #303134; color: #bdc1c6; max-width: 752px;"  name="searchTerm"/>
            <button class="input-group-text border-0" style="background-color: #202124; color: #8ab4f8;">
                <i class="bi bi-search"></i>
            </button>
        </div>
    </g:form>
</div>
<div class="content-news-list">
    <g:if test="${articleList != null}">
        <g:each in="${articleList}" var="article">
            <div class="item-news">
                <div class="d-flex">
                    <g:if test="${article.urlToImage != null}">
                        <div class="flex-shrink-0">
                            <a href="${article.url}" target="_blank">
                                <img class="img-urlToImage" src="${article.urlToImage}" alt="">
                            </a>
                        </div>
                    </g:if>

                    <div class="flex-grow-1 ms-3">
                        <div class="name-news">
                            <a href="${article.url}" target="_blank">
                                ${article.name}
                            </a>
                        </div>

                        <div class="url-news">
                            <a href="${article.url}" target="_blank">${article.title}</a>
                        </div>

                        <div>
                            <g:if test="${article.author != null}">
                                ${article.author}
                            </g:if>
                        </div>

                        <div>
                            <g:if test="${article.description != null}">
                                ${article.description}
                            </g:if>
                        </div>

                        <div class="date">
                            <g:dateFormat format="dd MMM yyyy" date="${article.publishedAt}"/>
                        </div>
                    </div>
                </div>
            </div>
        </g:each>
    </g:if>

</div>
</body>
</html>