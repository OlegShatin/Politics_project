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
    <div class="col-xs-8 col-xs-offset-2">
        <h1 class="page-header">
            <#if result.getElection().getType().name() == 'PRESIDENT'>Результаты выборов президента
                РФ ${result.getElection().getStartTime().getDayOfMonth()}.${result.getElection().getStartTime().getMonth().getValue()}.${result.getElection().getStartTime().getYear()?c}
            <#else>
                <#if result.getElection().getType().name() == 'PARLIAMENT'>Результаты выборов в Государственную Думу
                    РФ ${result.getElection().getStartTime().getDayOfMonth()}
                    .${result.getElection().getStartTime().getMonth().getValue()}
                    .${result.getElection().getStartTime().getYear()?c}
                </#if>
            </#if>
        </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
    </div>
</div>
<div class="row">
    <div class="col-xs-8 col-xs-offset-2">

        <h3><#if result.getElection().getType().name() == 'PRESIDENT'>Список кандидатов:<#else>
            <#if result.getElection().getType().name() == 'PARLIAMENT'>Партии:</#if>
        </#if></h3>
        <#list result.getBallotItems() as item>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading${item.getCandidate().getId()}">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapse${item.getCandidate().getId()}" aria-expanded="false"
                           aria-controls="collapse${item.getCandidate().getId()}">
                        ${item.getCandidate().getName()}
                        </a>
                    </h4>
                </div>
                <div id="collapse${item.getCandidate().getId()}" class="panel-collapse collapse" role="tabpanel"
                     aria-labelledby="heading${item.getCandidate().getId()}">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-xs-8">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label>Основная информация</label>

                                        <p>${item.getCandidate().getInfo()}</p>
                                        <label>Достижения</label>

                                        <p>${item.getCandidate().getAchievements()}</p>
                                        <label>Предвыборная программа</label>

                                        <p>${item.getCandidate().getElectionProgram()}</p>
                                        <#if item.getCandidate().getParty()??>
                                            <label>Текущее количество мест в Государственной
                                                Думе: ${item.getCandidate().getParty().getSeatsInParliament()}</label>

                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-4">
                                <img class="img-responsive" src="${item.getCandidate().getImageSrc()}"
                                     alt="">
                            </div>
                        </div>
                        <#if item.getCandidate().getParty()?? &&  item.getCandidate().getParty().getSupporters()?has_content>
                            <div class="row">
                                <div class="col-xs-12 center-block" align="center">
                                    <h5>Сторонники партии</h5>
                                    <ul class="list-inline">
                                        <#list item.getCandidate().getParty().getSupporters() as supporter>
                                            <li>
                                                <div class="thumbnail" align="center">
                                                    <img class="img-responsive round_icon_small"
                                                         src="${supporter.getImageSrc()}"
                                                         alt="">
                                                    <small>${supporter.getName()} ${supporter.getSurname()}</small>
                                                </div>
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        <#else>
            Ни одного кандидата не было заявлено.
        </#list>
        <#if result.getBallotItems()?has_content>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3>Результаты выборов:</h3>

                    <div class="row">
                        <div class="col-xs-4" align="right">
                            <label>Всего бюллетеней:</label>
                        </div>
                        <div class="col-xs-4">
                            <label>${result.getTotalBallots()}</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-4" align="right">
                            <label>Из них пустые или испорчены:</label>
                        </div>
                        <div class="col-xs-4">
                            <label>${result.getSpoiledBallots()}</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-4" align="right">
                            <label>За кандидатов отдано:</label>
                        </div>
                        <div class="col-xs-4">
                            <label>${result.getTotalVotes()}</label>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <#list result.getBallotItems() as item>
                        <div class="row">
                            <div class="col-xs-4" align="right">
                                <label>${item.getCandidate().getName()}</label>
                            </div>
                            <div class="col-xs-5">
                                <div class="progress">
                                    <#if (result.getTotalVotes() > 0)>
                                        <div class="progress-bar" role="progressbar"
                                             aria-valuenow="${(item.getVotes() * 100/ result.getTotalVotes())?round}"
                                             aria-valuemin="0"
                                             aria-valuemax="100"
                                             style="min-width: 2em; width: ${(item.getVotes() * 100/ result.getTotalVotes())?round}%;">
                                        ${(item.getVotes() * 100/ result.getTotalVotes())?string["0.##"]}%
                                        </div>
                                    <#else>
                                        <div class="progress-bar" role="progressbar"
                                             aria-valuenow="${(item.getVotes() * 100/ 1)?round}"
                                             aria-valuemin="0"
                                             aria-valuemax="100"
                                             style="min-width: 2em; width: ${(item.getVotes() * 100/ 1)?round}%;">
                                        ${(item.getVotes() * 100/ 1)?string["0.##"]}%
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
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