<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Show News</title>
</head>
<body>
<div class="content-news-list">
    <g:if test="${notice != null}">
        <div class="alert alert-success" role="alert">
            ${notice}
        </div>

    </g:if>
    <g:form action="update" id="${topic.id}" method="PUT">
        <label for="title">Title</label>
        <div class="item d-flex">

            <input class="form-control me-1" type="text" name="title" value="${topic.title}" id="title"/>
            <input class="btn btn-outline-primary" type="submit" value="Update" />
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