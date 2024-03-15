<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>News Searcher</title>
</head>
<body>
<div class="search-news">
    <g:form name="index" action="index" method="GET">
        <div class="item d-flex">
            <g:textField type="search" class="form-control" style="background: #303134; color: #bdc1c6; max-width: 752px;" name="searchTerm" value="${searchTerm}"/>
            <button class="input-group-text border-0" style="background-color: #202124; color: #8ab4f8;">
                <i class="bi bi-search"></i>
            </button>
        </div>
        <div class="item">
            <label class="form-label" for="topicSelect">Фильтр по теме</label>
            <g:select class="form-select" style="max-width: 400px" value="${params.topicSelect}"
                      optionKey="id" optionValue="title" name="topicSelect"
                      noSelection="${['': 'Без фильтров']}" from="${topics}"/>
        </div>
        <div class="item">
            <g:actionSubmit class="btn btn-outline-primary" value="Применить фильтр" action="index"/>
        </div>

    </g:form>

</div>

<div class="content-news-list">
    <g:if test="${articleList != null}">

        <g:each in="${mapArticles}" var="group">

            <div class="date-group">
                <div class="item-date-group">
                    <p>${group.key}</p>

                </div>
            </div>

            <g:each in="${group.value}" var="article">
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
        </g:each>
    </g:if>
    <g:if test="${mapArticles.size() == 0}">
        <g:if test="${topicSel != '' && topicSel != null}">
            <h1>По теме "${topicSel}" новостей не найдено</h1>
        </g:if>
        <g:else>
            <h1>Новостей не найдено</h1>
        </g:else>
    </g:if>
</div>
</body>
</html>