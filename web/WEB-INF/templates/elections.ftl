<#ftl encoding="utf-8"/>
<#macro title>
<title>Статистика</title>
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
            <h1 class="page-header"> Результаты выборов
            </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
    </div>

</div>
<!-- /.row -->


<div class="row">
    <div class="col-md-offset-2 col-md-8">
        <!-- Filter button -->
        <div class="row">
            <div class="col-xs-3">
                <div class="btn-group btn-block">
                    <button type="button" class="btn-block btn btn-default dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        <#if !filter??>Тип выборов<#elseif filter=="parl">В госдуму<#elseif filter=="pres">Президентские<#elseif filter=="all">Все</#if>
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/results?filter=all">Все</a>
                        </li>
                        <li>
                            <a href="/results?filter=parl">В Госдуму</a>
                        </li>
                        <li>
                            <a href="/results?filter=pres">Президентские</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <hr/>

        <div class="list-group">
            <#if elections??>
                <#list elections as election>
                    <a href="/results?el=${election.getId()}" class="list-group-item">
                        <div class="row">
                            <div class="col-xs-3">
                                <div>
                                    <label>Дата проведения</label>
                                </div>
                                <small>${election.getFinishTime().getDayOfMonth()}.${election.getFinishTime().getMonth().getValue()}.${election.getFinishTime().getYear()?c}</small>
                            </div>
                            <div class="col-xs-9">
                                <#if election.getType().name()=="PARLIAMENT">
                                    <label>Выборы в Государственную думу РФ</label>
                                </#if>
                                <#if election.getType().name()=="PRESIDENT">
                                    <label>Выборы президента РФ</label>
                                </#if>
                            </div>
                        </div>
                    </a>
                <#else>
                    <div class="list-group-item">Выборы еще не проводились или результаты не подведены</div>
                </#list>
            <#else>
                <div class="list-group-item">Выборы еще не проводились или результаты не подведены</div>
            </#if>
        </div>


    </div>
</div>

<hr>
</#macro>
<#macro scripts></#macro>
<#include "base.ftl">