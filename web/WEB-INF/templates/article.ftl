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
    <div class="col-xs-8 col-xs-offset-2">
        <h1 class="page-header">${article.getHeadline()}</h1>
    </div>
</div>
<!-- /.row -->
<div class="row">
    <div class="col-xs-8 col-xs-offset-2">
        <#list article.getContent()?split("\n") as paragraph>
            <p>${paragraph}</p>
        </#list>
        <hr/>
    </div>
</div>
<div class="row">
    <div class="col-xs-8 col-xs-offset-2">
        <#list comments as comment>
            <label>${comment.getAuthorName()} ${comment.getAuthorSurname()}</label>
            <p>${comment.getComment().getText()}</p>
            <@comment_node parent=comment/>
        </#list>
    </div>
</div>
<hr>
</#macro>
////todo: make somehow comments tree
<#macro comment_node parent>
<div class="row">
    <div class="col-xs-8 col-xs-offset-2">
        <#list parent.getChildren() as comment>
            <label>${comment.getAuthorName()} ${comment.getAuthorSurname()}</label>

            <p>${comment.getComment().getText()}</p>
            <@comment_node parent=comment/>
        </#list>
    </div>
</div>
</#macro>

<#macro scripts></#macro>
<#include "base.ftl">