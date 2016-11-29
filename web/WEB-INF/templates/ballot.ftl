<#ftl encoding="utf-8"/>
<#macro title>
<title>Бюллетень</title>
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
        <h1 class="page-header">Избирательный бюллетень<br/>
            <small><#if election_type.name() == 'PRESIDENT'>Президентские выборы</#if><br/>Выборы
                закончатся: ${election_finish.getDayOfMonth()}.${election_finish.getMonth().getValue()}.${election_finish.getYear()?c}
                <#if election_finish.getHour() <10>
                    0${election_finish.getHour()}<#else>${election_finish.getHour()}</#if>:<#if election_finish.getMinute() <10>
                    0${election_finish.getMinute()}<#else>${election_finish.getMinute()}
                </#if></small>
        </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
    </div>
</div>
<form action="/ballot" method="post">
    <#list candidates as candidate>
        <div class="row"  <#if candidate.getId() == 1>style="display: none;" </#if>>
            <div class="col-xs-8 col-xs-offset-2">
                <div class="row">
                    <div class="col-xs-5">

                        <img class="img-responsive img_big img-thumbnail" src="${candidate.getImageSrc()}"
                             alt="">

                    </div>
                    <div class="col-xs-7">
                        <h3>${candidate.getName()}</h3>

                        <p>${candidate.getInfo()}</p>
                        <p>${candidate.getAchievements()}</p>
                        <p>${candidate.getElectionProgram()}</p>
                        <label>
                            <input type="radio" name="voted_candidate" value="${candidate.getId()}"
                                   <#if candidate.getId() == 1>checked</#if>>
                        ${candidate.getName()}
                        </label>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </#list>
    <div class="row">
        <div class="col-xs-offset-8 col-xs-2">
            <input type="submit" class="input-lg btn btn-block btn-primary">
        </div>
    </div>
</form>


</div>
</#macro>

<#macro scripts>
<script src="js/jquery.js"></script>
<script src="js/ajax-rating-comment.js"></script>
</#macro>
<#include "base.ftl">