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
    <div class="col-lg-12">
        <h1 class="page-header"> Последние новости
            <small>(что-то если вдруг надо)</small>
        </h1>
        <a class="btn btn-block btn-success <#if user_role.name() == "AGENT" || user_role.name() == "ADMIN">disabled</#if>"
           href="<#if user_role.name() == "GUEST">/login<#else>/ballot</#if>" role=button">Проголосовать</a>
    </div>
</div>
<!-- /.row -->

    <#list articles as article>
    <hr>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
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
<hr>
</#macro>
<#macro scripts></#macro>
<#include "base.ftl">