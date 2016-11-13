<#ftl encoding="utf-8"/>
<#macro title>
<title>Новости</title>
</#macro>
<#macro custom_css>
<!-- Custom CSS -->
<link href="css/feed.css" rel="stylesheet">
</#macro>
<#macro content>
<!-- Page Heading -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"> Последние новости
            <small>(что-то если вдруг надо)</small>
        </h1>
    </div>
</div>
<!-- /.row -->

<!-- Project One -->
    <#list articles as article>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <h3>${article.headline}</h3>
            <h4>${article.publicationDateTime.getDayOfMonth()}.${article.publicationDateTime.month.getValue()}.${article.publicationDateTime.year?c}
                <#if article.publicationDateTime.getHour() <10>
                    0${article.publicationDateTime.getHour()}
                <#else>
                ${article.publicationDateTime.getHour()}
                </#if>:
                <#if article.publicationDateTime.getMinute() <10>
                    0${article.publicationDateTime.getMinute()}
                <#else>
                ${article.publicationDateTime.getMinute()}
                </#if>
            </h4>

            <p>${article.content}</p>
            <a class="btn btn-primary" href="#">Подробнее <span class="glyphicon glyphicon-chevron-right"></span></a>
        </div>
    </div>
    <#else>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <h1>Кажется, новостей нет...</h1>
        </div>
    </div>
    </#list>

<!-- /.row -->

<hr>

<!-- Project Two -->
<div class="row">
    <div class="col-md-7">
        <a href="#">
            <img class="img-responsive" src="http://placehold.it/700x300" alt="">
        </a>
    </div>
    <div class="col-md-5">
        <h3>Выиграет Едрос, заявила Единая Россия</h3>
        <h4>(КПРФ)</h4>

        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, odit velit cumque vero doloremque repellendus
            distinctio maiores rem expedita a nam vitae modi quidem similique ducimus! Velit, esse totam tempore.</p>
        <a class="btn btn-primary" href="#">Подробнее <span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>
</div>
<!-- /.row -->

<hr>

<!-- Project Three -->
<div class="row">
    <div class="col-md-7">
        <a href="#">
            <img class="img-responsive" src="http://placehold.it/700x300" alt="">
        </a>
    </div>
    <div class="col-md-5">
        <h3>Еще что то</h3>
        <h4>Еще что то</h4>

        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Omnis, temporibus, dolores, at, praesentium ut unde
            repudiandae voluptatum sit ab debitis suscipit fugiat natus velit excepturi amet commodi deleniti alias
            possimus!</p>
        <a class="btn btn-primary" href="#">Подробнее <span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>
</div>
<!-- /.row -->

<hr>

<!-- Project Four -->
<div class="row">

    <div class="col-md-7">
        <a href="#">
            <img class="img-responsive" src="http://placehold.it/700x300" alt="">
        </a>
    </div>
    <div class="col-md-5">
        <h3>Еще что то</h3>
        <h4>Еще что то</h4>

        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Explicabo, quidem, consectetur, officia rem
            officiis illum aliquam perspiciatis aspernatur quod modi hic nemo qui soluta aut eius fugit quam in
            suscipit?</p>
        <a class="btn btn-primary" href="#">Подробнее <span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>
</div>
<!-- /.row -->

<hr>

<!-- Project Five -->
<div class="row">
    <div class="col-md-7">
        <a href="#">
            <img class="img-responsive" src="http://placehold.it/700x300" alt="">
        </a>
    </div>
    <div class="col-md-5">
        <h3>Еще что то</h3>
        <h4>Еще что то</h4>

        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid, quo, minima, inventore voluptatum saepe
            quos nostrum provident ex quisquam hic odio repellendus atque porro distinctio quae id laboriosam facilis
            dolorum.</p>
        <a class="btn btn-primary" href="#">Подробнее <span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>
</div>
<!-- /.row -->

<hr>

<!-- Pagination -->
<div class="row text-center">
    <div class="col-lg-12">
        <ul class="pagination">
            <li>
                <a href="#">&laquo;</a>
            </li>
            <li class="active">
                <a href="#">1</a>
            </li>
            <li>
                <a href="#">2</a>
            </li>
            <li>
                <a href="#">3</a>
            </li>
            <li>
                <a href="#">4</a>
            </li>
            <li>
                <a href="#">5</a>
            </li>
            <li>
                <a href="#">&raquo;</a>
            </li>
        </ul>
    </div>
</div>
<!-- /.row -->

<hr>
</#macro>
<#macro scripts></#macro>
<#include "base.ftl">