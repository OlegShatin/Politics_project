<#ftl encoding="utf-8"/>
<#macro title>
<title>Регистрация</title>
</#macro>
<#macro in_out>
<a href="/login">Войти</a>
</#macro>
<#macro custom_css>
<!-- Custom CSS -->
<link href="css/registration.css" rel="stylesheet">
</#macro>
<#macro scripts>
<script src="js/checkField.js"></script>
<script src="js/passwords_matcher.js"></script>
<script src="js/ajax-email-checker.js"></script>
</#macro>
<#macro content>
<!-- Page Heading -->
<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h1 class="page-header"> Регистрация </h1>
        <#if error?has_content>
            <div class="alert alert-danger" role="alert">
                <strong>Oh snap!</strong> ${error}
            </div>
        </#if>
    </div>
</div>
<!-- /.row -->


<form method="post" onsubmit="return checkField('name') && checkField('surname') &&
    checkField('password') && checkField('repeated_password') && checkField('birthday_date')
     && checkField('passport_series') && checkMinLegth('passport_series',4) && checkField('passport_num') &&checkField('email')  && checkMinLegth('passport_num',6) && checker() && checkEmail()">
    <div class="row">
        <div class="col-md-4 col-md-offset-2">
            <div class="form-group">
                <h4>Имя</h4>
                <input class="form-control" type="text" id="name" name="name" maxlength="20" oninput="removeHasError('name')">
            </div>
            <div class="form-group">
                <h4>Фамилия</h4>
                <input class="form-control" type="text" name="surname" id="surname" maxlength="25" oninput="removeHasError('surname')">
            </div>
            <div class="form-group">
                <h4>Отчество</h4>
                <input class="form-control" type="text" name="patronymic" id="patronymic"  maxlength="20" oninput="removeHasError('patronymic')">

            </div>
            <div class="form-group">
                <h4>Пароль</h4>
                <input class="form-control" type="password" name="password" id="password" maxlength="100" oninput="removeHasError('password')">
            </div>
            <div class="form-group">
                <h4>Повторите пароль</h4>
                <input class="form-control" type="password" name="repeated_password" id="repeated_password" onkeyup="checker()" maxlength="100">
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <h4>Дата Рождения</h4>
                <input class="form-control" type="date" name="birthday_date" id="birthday_date" oninput="removeHasError('birthday_date')">
            </div>
            <div class="form-group">
                <h4>Электронная почта</h4>
                <input class="form-control" type="email" name="email" id="email" maxlength="50" onkeyup="checkEmail()">
            </div>
            <div class="form-group">
                <h4>Серия и номер паспорта</h4>
                <div class="row">
                    <div class="col-xs-5">
                        <input class="form-control" type="text" name="passport_series" id="passport_series" maxlength="4" oninput="removeHasError('passport_series')">
                    </div>
                    <div class="col-xs-7">
                        <input class="form-control" type="text" name="passport_num" id="passport_num" oninput="removeHasError('passport_num')" maxlength="6">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <h4>Часовой пояс</h4>
                <div class="row">
                    <div class="col-xs-5">
                        <select class="form-control" name="timezone_offset">
                            <option>-12</option>
                            <option>-11</option>
                            <option>-10</option>
                            <option>-9</option>
                            <option>-8</option>
                            <option>-7</option>
                            <option>-6</option>
                            <option>-5</option>
                            <option>-4</option>
                            <option>-3</option>
                            <option>-2</option>
                            <option>-1</option>
                            <option>+0</option>
                            <option>+1</option>
                            <option>+2</option>
                            <option selected="selected">+3</option>
                            <option>+4</option>
                            <option>+5</option>
                            <option>+6</option>
                            <option>+7</option>
                            <option>+8</option>
                            <option>+9</option>
                            <option>+10</option>
                            <option>+11</option>
                            <option>+12</option>
                            <option>+13</option>
                            <option>+14</option>
                        </select>
                    </div>
                    <div class="col-xs-7">
                        <input class="form-control btn-success" type="submit" value="Зарегистрироваться">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <hr>
</form>

</#macro>
<#include "base.ftl">
