<#ftl encoding="utf-8"/>
<#macro title>
<title>Кандидаты</title>
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
        <h1 class="page-header">
            Время следующих выборов еще не определено центральным избирательным комитетом.
        </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
    </div>
</div>


</#macro>

<#macro scripts>
<script src="js/jquery.js"></script>
<script src="js/ajax-rating-comment.js"></script>
</#macro>
<#include "base.ftl">