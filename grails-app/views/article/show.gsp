<!DOCTYPE html>
<html>
<head>
    <title>Show News</title>
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="index">Main News</g:link></li>
    </ul>
</div>
<div>
    ${article.author} <br>
    ${article.description} <br>
    <a href="${article.url}" target="_blank">${article.url}</a> <br>
    <img src="${article.urlToImage}" alt="" style="height:92px; width:92px"> <br> <br>
    ${article.publishedAt} <br>
</div>
</body>
</html>