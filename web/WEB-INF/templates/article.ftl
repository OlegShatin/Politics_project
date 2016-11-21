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
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
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
        <h3>Комментарии:</h3>
        <#if user_role.name() == "USER" || user_role.name() == "ADMIN">
            <button class="btn btn-primary" role="button" data-toggle="collapse" data-parent="#accordion"
                    href="#collapse_main"
                    aria-expanded="true" aria-controls="collapse_main">
                Комментировать
            </button>
            <div id="collapse_main" class="panel-collapse collapse"
                 role="tabpanel"
                 aria-labelledby="heading_main">
                <div class="panel-body">
                    <form method="post"
                          action="/news?a=${article.getId()}">
                        <div class="form-group">
                                <textarea class="form-control" rows="3" name="comment_text"
                                          placeholder="Ваш комментарий"></textarea>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Комментировать</button>
                        </div>
                    </form>
                </div>
            </div>
        <#else >
            <h4>Чтобы комментировать статью вам нужно <a href="/login">войти</a></h4>
        </#if>
        <br/>
        <br/>
        <#list comments as comment>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div>
                        <label>${comment.getAuthorName()} ${comment.getAuthorSurname()}  </label>
                    ${comment.getComment().getPublicationDateTime().getDayOfMonth()}.${comment.getComment().getPublicationDateTime().getMonth().getValue()}.${comment.getComment().getPublicationDateTime().getYear()?c}
                        <#if comment.getComment().getPublicationDateTime().getHour() <10>0${comment.getComment().getPublicationDateTime().getHour()}<#else>${comment.getComment().getPublicationDateTime().getHour()}</#if>:<#if comment.getComment().getPublicationDateTime().getMinute() <10>0${comment.getComment().getPublicationDateTime().getMinute()}<#else>${comment.getComment().getPublicationDateTime().getMinute()}
                    </#if>
                    </div>
                    <p>${comment.getComment().getText()}</p>
                    <#if user_role.name() == "USER" || user_role.name() == "ADMIN">
                        <button type="button" class="btn btn-success btn-xs"
                                onclick="up(${comment.getComment().getId()})">
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                        </button>
                        <label id="counter${comment.getComment().getId()}">${comment.getComment().getRating()}</label>
                        <button type="button" class="btn btn-danger btn-xs"
                                onclick="down(${comment.getComment().getId()})">
                            <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                        </button>
                        <a class="small" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapse_${comment.getComment().getId()}"
                           aria-expanded="true" aria-controls="collapse_${comment.getComment().getId()}">
                            Ответить
                        </a>
                    <#else>
                        <button type="button" class="btn btn-success btn-xs disabled">
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                        </button>
                        <label id="counter${comment.getComment().getId()}">${comment.getComment().getRating()}</label>
                        <button type="button" class="btn btn-danger btn-xs disabled">
                            <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                        </button>
                        <a class="small" href="/login">Ответить</a>
                    </#if>
                </div>
            </div>
            <#if user_role.name() == "USER" || user_role.name() == "ADMIN">
                <div id="collapse_${comment.getComment().getId()}" class="panel-collapse collapse"
                     role="tabpanel"
                     aria-labelledby="heading_${comment.getComment().getId()}">
                    <div class="panel-body">
                        <form method="post"
                              action="/news?a=${article.getId()}&parent_comment_id=${comment.getComment().getId()}">
                            <div class="form-group">
                                <textarea class="form-control" rows="3" name="comment_text"
                                          placeholder="Ваш комментарий"></textarea>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Ответить</button>
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
            <#if comment.getChildren()?has_content>
                <@comment_node parent=comment/>
            </#if>
        <#else>
            <label>Комментариев пока нет... Будьте первым!</label>
        </#list>
        <hr>
    </div>
</div>


</div>
</#macro>

<#macro comment_node parent>
    <#list parent.getChildren() as comm>
    <div class="row">
        <div class="col-xs-11 col-xs-offset-1">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div>
                        <label>${comm.getAuthorName()} ${comm.getAuthorSurname()}  </label>
                    ${comm.getComment().getPublicationDateTime().getDayOfMonth()}.${comm.getComment().getPublicationDateTime().getMonth().getValue()}.${comm.getComment().getPublicationDateTime().getYear()?c}
                        <#if comm.getComment().getPublicationDateTime().getHour() <10>0${comm.getComment().getPublicationDateTime().getHour()}<#else>${comm.getComment().getPublicationDateTime().getHour()}</#if>:<#if comm.getComment().getPublicationDateTime().getMinute() <10>0${comm.getComment().getPublicationDateTime().getMinute()}<#else>${comm.getComment().getPublicationDateTime().getMinute()}
                        </#if>
                    </div>
                    <p>${comm.getComment().getText()}</p>
                    <#if user_role.name() == "USER" || user_role.name() == "ADMIN">
                        <button type="button" class="btn btn-success btn-xs" onclick="up(${comm.getComment().getId()})">
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                        </button>
                        <label id="counter${comm.getComment().getId()}">${comm.getComment().getRating()}</label>
                        <button type="button" class="btn btn-danger btn-xs"
                                onclick="down(${comm.getComment().getId()})">
                            <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                        </button>
                        <a class="small" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapse_${comm.getComment().getId()}"
                           aria-expanded="true" aria-controls="collapse_${comm.getComment().getId()}">
                            Ответить
                        </a>
                    <#else>
                        <button type="button" class="btn btn-success btn-xs disabled">
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                        </button>
                        <label id="counter${comm.getComment().getId()}">${comm.getComment().getRating()}</label>
                        <button type="button" class="btn btn-danger btn-xs disabled">
                            <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                        </button>
                        <a class="small" href="/login">Ответить</a>
                    </#if>
                </div>
            </div>
            <#if user_role.name() == "USER" || user_role.name() == "ADMIN">
                <div id="collapse_${comm.getComment().getId()}" class="panel-collapse collapse"
                     role="tabpanel"
                     aria-labelledby="heading_${comm.getComment().getId()}">
                    <div class="panel-body">
                        <form method="post"
                              action="/news?a=${article.getId()}&parent_comment_id=${comm.getComment().getId()}">
                            <div class="form-group">
                                <textarea class="form-control" rows="3" name="comment_text"
                                          placeholder="Ваш комментарий"></textarea>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Ответить</button>
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
            <#if comm.getChildren()?has_content>
                <@comment_node parent=comm/>
            </#if>
        </div>
    </div>
    <#else>
    </#list>

</#macro>

<#macro scripts>
<script src="js/jquery.js"></script>
<script src="js/ajax-rating-comment.js"></script>
</#macro>
<#include "base.ftl">