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
            <h1 class="page-header"> ${opponent.getName()}: ваши обращения
            </h1>
        <#else>
            <h1 class="page-header"> Обращение от пользователя ${opponent.getName()} ${opponent.getSurname()}
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

        <!-- message form -->
        <div class="row">
            <div class="col-xs-12">
                <div class="panel-body">
                    <form method="post"
                          action="/conversations?id=${opponent.getId()}" onsubmit="return checkMessage()">
                        <div class="form-group">
                                <textarea class="form-control" rows="3" name="message_text" id="message_text"
                                          placeholder="Ваше сообщение" oninput="removeHasError()"
                                          maxlength="500"></textarea>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Ответить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <#if messages??>
            <#list messages as message>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <#if message.getSenderId() != user.getId()>
                            <#--this message from opponent-->
                                <#if user_role.name() == 'USER'>
                                    <div class="col-xs-10">
                                        <div>
                                            <label class="small">${opponent.getName()}</label>
                                        </div>
                                        <small>${message.getSendingTime().getDayOfMonth()}.${message.getSendingTime().getMonth().getValue()}.${message.getSendingTime().getYear()?c}
                                            <#if message.getSendingTime().getHour() <10>0${message.getSendingTime().getHour()}<#else>${message.getSendingTime().getHour()}</#if>:<#if message.getSendingTime().getMinute() <10>0${message.getSendingTime().getMinute()}<#else>${message.getSendingTime().getMinute()}</#if></small>
                                    </div>
                                    <div class="col-xs-2" align="right">
                                        <img class="img-responsive round_icon_extrasmall"
                                             src="${opponent.getImageSrc()}"
                                             alt="">
                                    </div>
                                    <div class="col-xs-10">
                                        <p>${message.getMessageText()}</p>
                                    </div>
                                <#else>
                                <div class="col-xs-10">
                                    <div>
                                        <label class="small">${opponent.getName()} ${opponent.getSurname()}</label>
                                    </div>
                                <small>${message.getSendingTime().getDayOfMonth()}.${message.getSendingTime().getMonth().getValue()}.${message.getSendingTime().getYear()?c}
                                    <#if message.getSendingTime().getHour() <10>0${message.getSendingTime().getHour()}<#else>${message.getSendingTime().getHour()}</#if>:<#if message.getSendingTime().getMinute() <10>0${message.getSendingTime().getMinute()}<#else>${message.getSendingTime().getMinute()}</#if></small>
                                </div>
                                <div class="col-xs-10">
                                    <p>${message.getMessageText()}</p>
                                </div>
                                </#if>
                            <#else>
                            <#--this message from current user-->
                                <div class="col-xs-10">
                                    <div>
                                        <label class="small">${user.getName()} ${user.getSurname()}</label>
                                        <small><em> (это вы)</em></small>
                                    </div>
                                    <small>${message.getSendingTime().getDayOfMonth()}.${message.getSendingTime().getMonth().getValue()}.${message.getSendingTime().getYear()?c}
                                        <#if message.getSendingTime().getHour() <10>0${message.getSendingTime().getHour()}<#else>${message.getSendingTime().getHour()}</#if>:<#if message.getSendingTime().getMinute() <10>0${message.getSendingTime().getMinute()}<#else>${message.getSendingTime().getMinute()}</#if></small>
                                </div>
                                <div class="col-xs-10">
                                    <p>${message.getMessageText()}</p>
                                </div>
                            </#if>
                    </div>

                </div>
            </div>
            <#else>
                <div class="panel panel-default">
                    <div class="panel-body">
                        Еще не было обращений
                    </div>
                </div>
            </#list>
        <#else>
            <div class="panel panel-default">
                <div class="panel-body">
                    Еще не было обращений
                </div>
            </div>
        </#if>

        <hr>


        <!-- /.row -->
    </div>
</div>

<hr>
</#macro>
<#macro scripts>
<script src="js/checkMessage.js"></script>
<#-- /*var f = function(){
     $.ajax({
         'url':'/ajax-search',
         'data' : {
             'message_text': $("#message_form").val(),
             'recepient_id': $.getUrlVar('name')
         },
         'method':'get',
         'success': function(message) {
             var top = $("#top");
             top.append('');
         }
     });
 };
 $.extend({
     getUrlVars: function(){
         var vars = [], hash;
         var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
         for(var i = 0; i < hashes.length; i++)
         {
             hash = hashes[i].split('=');
             vars.push(hash[0]);
             vars[hash[0]] = hash[1];
         }
         return vars;
     },
     getUrlVar: function(name){
         return $.getUrlVars()[name];
     }
     // Получит параметр URL по его имени
     //var byName = $.getUrlVar('name');
 });*/-->
</#macro>
<#include "base.ftl">