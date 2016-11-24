<#ftl encoding="utf-8"/>
<#macro title>
<title>Профиль</title>
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
    <div class="col-xs-offset-2 col-xs-8">
        <h1 class="page-header"> Профиль
        </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
        <#if pass_upd?has_content && pass_upd == "success">
            <div class="alert alert-success" role="alert">
                <strong>Пароль изменен</strong>
            </div>
        </#if>
        <#if pass_upd?has_content && pass_upd == "fail">
            <div class="alert alert-danger" role="alert">
                <strong>Не удалось изменить пароль. Попробуйте позже.</strong>
            </div>
        </#if>
        <#if pass_upd?has_content && pass_upd == "not_match">
            <div class="alert alert-warning" role="alert">
                <strong>Введенный вами пароль не совпадает со старым</strong>
            </div>
        </#if>
        <#if email_upd?has_content && email_upd == "used">
            <div class="alert alert-warning" role="alert">
                <strong>Не удалось изменить email: введенный email уже используется</strong>
            </div>
        </#if>
        <#if email_upd?has_content && email_upd == "fail">
            <div class="alert alert-danger" role="alert">
                <strong>Не удалось изменить email. Попробуйте позже.</strong>
            </div>
        </#if>
        <#if email_upd?has_content && email_upd == "success">
            <div class="alert alert-success" role="alert">
                <strong>Email изменен.</strong>
            </div>
        </#if>
    </div>
</div>
<!-- /.row -->


<div class="row">
    <div class="col-xs-offset-2 col-xs-8">
        <div class="row form-horizontal">
            <div class="form-group">
                <label class="col-xs-3 control-label">ФИО</label>

                <div class="col-xs-9" style="margin-top: 7px">
                 ${user.getSurname()} ${user.getName()} <#if user.getPatronymic()??>${user.getPatronymic()}</#if>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">Дата рождения</label>

                <div class="col-xs-9" style="margin-top: 7px">
                ${user.getBirthday().getDayOfMonth()}.${user.getBirthday().getMonth().getValue()}.${(user.getBirthday().getYear() + 1900)?c}
                </div>
            </div>
        <#--
        <label class="col-xs-2 control-label">Серия и номер паспорта</label>
        <div class="col-xs-6" style="margin-top: 7px">
            ${user.getPassportSeries()}  ${user.getPassportNumber()}
        </div>
        <div class="col-xs-4"></div>
        -->
            <div class="form-group">
                <label class="col-xs-3 control-label">Временная зона</label>

                <div class="col-xs-9" style="margin-top: 7px">
                ${user.getTimezoneOffset()}
                </div>
            </div>

            <br/>
        </div>
        <div class="row">
            <form role="form" class="form-horizontal" action="/profile" method="POST"
                  onsubmit="return checkField('email')">
                <div class="form-group">
                    <label for="email" class="col-xs-3 control-label">Email</label>

                    <div class="col-xs-6">
                        <input type="email" class="form-control" name="email"
                               id="email" maxlength="50" oninput="removeHasError('email')"
                               placeholder="${user.getEmail()}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-6 col-xs-offset-3">
                        <button class="btn-success btn btn-block" type="submit">Изменить email</button>
                    </div>
                </div>
            </form>
            <form class="form-horizontal" action="/profile" method="POST"
                  onsubmit="return checkField('password') && checkField('old_password')">
                <div class="form-group">
                    <label for=password" class="col-xs-3 control-label">Старый пароль</label>

                    <div class="col-xs-6">
                        <input type="password" class="form-control" name="old_password"
                               id="old_password" maxlength="50" oninput="removeHasError('old_password')"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for=password" class="col-xs-3 control-label">Новый пароль</label>

                    <div class="col-xs-6">
                        <input type="password" class="form-control" name="password"
                               id="password" maxlength="50" oninput="removeHasError('password')"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-6 col-xs-offset-3">
                        <button class="btn-success btn btn-block" type="submit">Изменить пароль</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<hr>
</#macro>
<#macro scripts>
<script src="js/checkField.js"></script>
</#macro>
<#include "base.ftl">