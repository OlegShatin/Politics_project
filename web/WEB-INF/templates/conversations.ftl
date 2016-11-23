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
    <div class="col-md-offset-2 col-md-8">
        <#if user_role.name() == 'USER'>
            <h1 class="page-header"> Мои обращения к кандидатам
            </h1>
        <#else>
            <h1 class="page-header"> Обращения пользователей
            </h1>
        </#if>
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
        <#if unmessaged_candidates?has_content && user_role=="USER" || user_role=="ADMIN">
            <!-- Single button -->
            <div class="row">
                <div class="col-xs-4">
                    <div class="btn-group btn-block">
                        <button type="button" class="btn-block btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            Написать другим кандидатам <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <#list unmessaged_candidates as candidate>
                                <li><a data-toggle="modal"
                                       data-target="#send_massage_to${candidate.getId()}">${candidate.getName()}</a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>
            <#list unmessaged_candidates as candidate>
                <div class="modal fade" id="send_massage_to${candidate.getId()}" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <form method="post" class="form-horizontal"
                          action="/conversations?id=${candidate.getId()}">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">${candidate.getName()}: новое обращение</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-offset-2 col-xs-8">
                                                                <textarea class="form-control" rows="3"
                                                                          name="message_text"
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
            </#list>
            <hr/>
        </#if>
        <div class="list-group">
            <#if conversations??>
                <#list conversations as conversation>
                    <a href="conversations?id=${conversation.getPerson().getId()}" class="list-group-item">
                        <div class="row">
                            <#if user_role.name() == 'USER'>
                                <div class="col-xs-3" align="center">
                                    <img class="img-responsive round_icon_small"
                                         src="${conversation.getPerson().getImageSrc()}"
                                         alt="">
                                    <label class="small">${conversation.getPerson().getName()}</label>
                                </div>
                            <#else>
                                <div class="col-xs-3">
                                    <label class="small">${conversation.getPerson().getName()} ${conversation.getPerson().getSurname()}</label>
                                </div>
                            </#if>
                            <div class="col-xs-6">
                                <#if conversation.getMessage().getSenderId() == user.getId()><strong>Вы:</strong></#if>
                                <p class="small">${conversation.getMessage().getMessageText()}</p>
                            </div>
                            <div class="col-xs-3" align="right">
                            ${conversation.getMessage().getSendingTime().getDayOfMonth()}
                                .${conversation.getMessage().getSendingTime().getMonth().getValue()}.${conversation.getMessage().getSendingTime().getYear()?c}
                                <#if conversation.getMessage().getSendingTime().getHour() <10>
                                    0${conversation.getMessage().getSendingTime().getHour()}<#else>${conversation.getMessage().getSendingTime().getHour()}</#if>:<#if conversation.getMessage().getSendingTime().getMinute() <10>
                                0${conversation.getMessage().getSendingTime().getMinute()}<#else>${conversation.getMessage().getSendingTime().getMinute()}
                            </#if>
                            </div>
                        </div>
                    </a>
                <#else>
                    <div class="list-group-item">Еще нет обращений к кандидатам</div>
                </#list>
            <#else>
                <div class="list-group-item">Еще нет обращений к кандидатам</div>
            </#if>
        </div>
        <hr>

        <!-- Pagination -->
        <div class="row text-center">
            <div class="col-xs-12">
                <ul class="pagination">

                    <li>
                        <a <#if (page > 1)>href="/conversations?p=${page-1}"<#else > hidden="true" </#if>>&laquo;</a>
                    </li>
                    <#assign x = max_page>
                    <#list 1..x as i>
                        <li>
                            <a href="/conversations?p=${i}" <#if (i == page)>class="active"</#if>>${i}</a>
                        </li>
                    </#list>
                    <li>
                        <a <#if (page < max_page)>href="/conversations?p=${page+1}"<#else >
                           hidden="true"</#if>>&raquo;</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /.row -->
    </div>
</div>

<hr>
</#macro>
<#macro scripts></#macro>
<#include "base.ftl">