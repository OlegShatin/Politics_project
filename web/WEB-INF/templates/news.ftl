<#ftl encoding="utf-8"/>
<#macro title>
<title>Новости</title>
</#macro>
<#macro in_out>
    <#if user_role.name() == "GUEST">
    <a href="/login">Войти</a>
    <#else>
    <a href="/logout">Выйти</a>
    </#if>
</#macro>
<#macro custom_css>
<!-- Custom CSS -->
<link href="css/feed.css" rel="stylesheet">
</#macro>
<#macro content>
<!-- Page Heading -->
<div class="row">
    <div class="col-md-offset-2 col-md-8">
        <h1 class="page-header"> Последние новости
        </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
        <#if alert?has_content && alert = "thanks">
            <div class="alert alert-success" role="alert">
                <strong>Спасибо!</strong> Ваш голос учтен.
            </div>
        </#if>
    </div>
</div>
<div class="row">
    <div class="col-md-offset-2 col-md-3">
        <a class="btn btn-block btn-success <#if user_cannot_vote>disabled</#if>"
           href="/ballot" role=button">Проголосовать</a>
        <#if user_role.name() == "GUEST">
            <div class="row" align="center">
                <div class="col-md-offset-2 col-md-8">
                    <label>Чтобы голосовать на выборах, нужно <a href="/login">войти на сайт</a></label>
                </div>
            </div>
        </#if>
    </div>
    <!--search-->
    <form method="get" action="/news" onsubmit="return checkField('search')">
        <div class="col-md-3">
            <div class="input-group stylish-input-group">
                <input type="text" <#if search?has_content>value="${search}"</#if> id="search" name="search" class="form-control" placeholder="Поиск по новостям" oninput="removeHasError('search')">
                    <span class="input-group-addon">
                        <button type="submit"><span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label><input type="checkbox" name="headlines_only"><small>Только в заголовках</small></label>
            </div>
        </div>
    </form>
</div>


<!-- /.row -->

    <#list articles as article>

    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <hr>
            <h3>${article.headline}</h3>
            <h4>${article.publicationDateTime.getDayOfMonth()}
                .${article.publicationDateTime.month.getValue()}.${article.publicationDateTime.year?c}
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
            <a class="btn btn-primary" href="/news?a=${article.getId()}">Подробнее <span
                    class="glyphicon glyphicon-chevron-right"></span></a>
        </div>
    </div>
    <#else>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <h1>Кажется, новостей нет...</h1>
        </div>
    </div>
    </#list>
<hr>
</#macro>
<#macro scripts>
<script src="/js/checkField.js"></script>
</#macro>
<#include "base.ftl">