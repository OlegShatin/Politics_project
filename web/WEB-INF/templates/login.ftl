<#ftl encoding="utf-8"/>
<#macro title>
<title>Вход</title>
</#macro>
<#macro in_out>
<a href="/login">Войти</a>
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
    <div class="col-md-offset-5 col-md-2" align="center">
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Неправильный Email или пароль</strong>
            </div>

        </#if>
            <form role="form" action="/login" method="POST">
            <div class="form-group">
                <input placeholder="Email" type="email" class="form-control" name="email"
                       <#if last_email?has_content>value="${last_email}"</#if>/>
            </div>
            <div class="form-group">
                <input type="password" placeholder="Password" class="form-control" name="password"/>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" name="remember_request">Запомнить меня</label>
            </div>
            <div class="form-group">
                <button class="btn-success btn btn-block" type="submit">Вход</button>
            </div>
        </form>
    </div>
</div>
<div class="row">
    <form class="form-horizontal" action="/signup" method="get">
        <div class="form-group">
            <label for="signup_button" class="col-md-offset-3 col-md-2 control-label" id="signup_label">Впервые у
                нас?</label>

            <div class="col-md-2">
                <button class="btn btn-info btn-block" id="signup_button" type="submit">Регистрация</button>
            </div>
        </div>
    </form>
</div>
</#macro>
<#include "base.ftl">
