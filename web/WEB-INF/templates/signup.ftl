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
<#macro scripts></#macro>
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

<div>
    <form method="post">
        <div class="row">
            <div class="col-md-4 col-md-offset-2">
                <h3>Имя</h3>
                <input class="input-lg" type="text" name="name">

                <h3>Фамилия</h3>
                <input class="input-lg" type="text" name="surname">

                <h3>Отчество</h3>
                <input class="input-lg" type="text" name="patronymic">

                <h3>Пароль</h3>
                <input class="input-lg" type="password" name="password">

                <h3>Повторите пароль</h3>
                <input class="input-lg" type="password" name="repeated_password">
            </div>
            <div class="col-md-4">
                <h3>Дата Рождения</h3>
                <input class="input-lg" type="date" name="birthday_date">

                <h3>Электронная почта</h3>
                <input class="input-lg" type="email" name="email">

                <h3>Серия и номер паспорта</h3>

                <div class="row">
                    <div class="col-xs-12">
                        <input class="input-lg col-xs-5" type="text" name="passport_series">
                        <input class="input-lg col-xs-7" type="text" name="passport_num">
                    </div>
                </div>
                <h3>Часовой пояс</h3>
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
                </div>

            </div>
        </div>
        <hr>
        <div class="row">
            <input class="input-lg col-md-4 col-md-offset-2 btn-success" type="submit" value="Зарегистрироваться">
        </div>
    </form>
</div>
</#macro>
<#include "base.ftl">
