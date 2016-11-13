<#ftl encoding="utf-8"/>
<#macro title>
<title>Логин</title>
</#macro>
<#macro custom_css>
<!-- Custom CSS -->
<link href="css/feed.css" rel="stylesheet">
</#macro>
<#macro scripts></#macro>
<#macro content>
<div class="row">
    <div class="col-lg-2 col-lg-offset-5" align="center">
        <h1 class="page-header">Login
        </h1>
    </div>
</div>
<div class="row">
    <div class="col-lg-offset-5 col-lg-2" align="center">
        <#if error?has_content><p>Incorrect username or password</p></#if>
        <form role="form" action="/login" method="POST">
            <div class="form-group">
                <input type="email" class="form-control" name="email"
                       <#if last_email?has_content>value="${last_email}"</#if>/>
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="password"/>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" name="remember_request">Запомнить меня</label>
            </div>
            <div class="form-group">
                <button class="btn-success" type="submit">Log In</button>
            </div>
        </form>
        <a href="/guest">Guest page</a>
    </div>
</div>
</#macro>
<#include "base.ftl">
