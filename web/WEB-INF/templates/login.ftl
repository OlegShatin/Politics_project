<#macro scripts></#macro>
<#macro content>
<div class="col-lg-offset-5 col-lg-2" align="center">
    <div class="h1">Log In</div>
    <#if error?has_content><p>Incorrect username or password</p></#if>
    <form role="form" action="/login" method="POST">
        <div class="form-group">
            <input type="text" class="form-control" name="username"
                   <#if lastLogin?has_content>value="${lastLogin}"</#if>/>
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="password"/>
        </div>
        <div class="checkbox">
            <label><input type="checkbox" name="rememberRequest">Remember me</label>
        </div>
        <div class="form-group">
            <button class="btn-success" type="submit">Log In</button>
        </div>
    </form>
    <a href="/guest">Guest page</a>
</div>
</#macro>
<#include "base.ftl">
