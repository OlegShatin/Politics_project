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
            <#if election.getType().name() == 'PRESIDENT'>Кандидаты
                на выборах президента
                РФ ${election.getStartTime().getDayOfMonth()}.${election.getStartTime().getMonth().getValue()}.${election.getStartTime().getYear()?c}
            <#else>
                <#if election.getType().name() == 'PARLIAMENT'>Партии, учавствующие в выборах в Государственную Думу
                    РФ ${election.getStartTime().getDayOfMonth()}.${election.getStartTime().getMonth().getValue()}.${election.getStartTime().getYear()?c}
                </#if>
            </#if>
        </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
        <#if success_message_candidate?has_content>
            <div class="alert alert-success" role="alert">
                <strong>Ваше сообщение отправлено!</strong><#if user_role.name()=='USER'> <a href="conversations?id=${success_message_candidate.getId()}">перейти к диалогу</a></#if>
            </div>
        </#if>
    </div>
</div>

    <#list candidates as candidate>
    <div class="row"  <#if candidate.getId() == 1>style="display: none;" </#if>>
        <div class="col-xs-8 col-xs-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading${candidate.getId()}">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapse${candidate.getId()}" aria-expanded="false"
                           aria-controls="collapse${candidate.getId()}">
                        ${candidate.getName()}
                        </a>
                    </h4>
                </div>
                <div id="collapse${candidate.getId()}" class="panel-collapse collapse" role="tabpanel"
                     aria-labelledby="heading${candidate.getId()}">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-xs-8">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label>Основная информация</label>

                                        <p>${candidate.getInfo()}</p>
                                        <label>Достижения</label>

                                        <p>${candidate.getAchievements()}</p>
                                        <label>Предвыборная программа</label>

                                        <p>${candidate.getElectionProgram()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-4">
                                <img class="img-responsive img-thumbnail" src="${candidate.getImageSrc()}"
                                     alt="">
                                <a class="btn btn-primary btn-block" <#if user_role.name() != 'GUEST'> data-toggle="modal" data-target="#send_message_to${candidate.getId()}"<#else> href="/login" </#if>> Написать обращение <span class="glyphicon glyphicon-pencil"
                                                                                                                                                                                aria-hidden="true"></span>
                                </a>
                                <div class="modal fade" id="send_message_to${candidate.getId()}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <form method="post" class="form-horizontal" onsubmit="return checkMessage('message_to${candidate.getId()}')"
                                          action="/candidates?candidate_id=${candidate.getId()}">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">${candidate.getName()}: новое обращение</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-xs-offset-2 col-xs-8">
                                                                <textarea class="form-control" rows="3" onkeyup="removeHasError('message_to${candidate.getId()}')" name="message_text" id="message_to${candidate.getId()}"
                                                                          placeholder="Ваше обращение к кандидату"></textarea>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-primary">Отправить</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <#if election.getType().name() == 'PARLIAMENT' &&  candidate.getParty().getSupporters()?has_content>
                            <div class="row">
                                <div class="col-xs-12 center-block" align="center">
                                    <h5>Сторонники партии</h5>
                                    <ul class="list-inline">
                                        <#list candidate.getParty().getSupporters() as supporter>
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
            <hr>
        </div>
    </div>
    <#else>
    Ни одного кандидата не заявлено.
    </#list>
</#macro>

<#macro scripts>
<script src="js/jquery.js"></script>
<script src="/js/checkMessage.js"></script>
</#macro>
<#include "base.ftl">