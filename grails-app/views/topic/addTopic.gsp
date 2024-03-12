<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>List of Topic</title>
</head>
<body>
<div class="search-news">
    <h1>Добавление тем новостей</h1>
    <g:if test="${noticeSuccess != null}">
        <div class="alert alert-success" role="alert">
            ${noticeSuccess}
        </div>
    </g:if>
    <g:if test="${noticeError != null}">
        <div class="alert alert-danger" role="alert">
            ${noticeError}
        </div>
    </g:if>
    <g:form name="index" action="addTopic" method="POST">
        <div class="d-flex">
            <g:textField type="search" class="form-control me-2" style="background: #303134; color: #bdc1c6; max-width: 752px;"  name="searchTerm"/>
            <g:actionSubmit class="btn btn-outline-primary" value="Поиск" action="addTopic">Submit</g:actionSubmit>
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
                            <img class="img-urlToImage" src="${article.urlToImage}" alt="">
                        </div>
                    </g:if>

                    <div class="flex-grow-1 ms-3">
                        <div class="name-news">
                            ${article.name}
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