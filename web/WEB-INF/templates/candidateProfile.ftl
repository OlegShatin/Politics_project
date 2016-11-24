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
        <h1 class="page-header"> Профиль кандидата
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

    <div class="row">
        <div class="col-xs-12" align="center">
            <div >
                <img class="img-responsive img_big img-thumbnail"
                     src="${candidate.getImageSrc()}"
                     alt=""/>

            </div>
            <label>${candidate.getName()}</label>
        </div>

    </div>

        <div class="row">
            <hr/>
        <#--name--->
            <form role="form" class="form-horizontal" enctype="multipart/form-data" action="/profile?field=name"
                  method="POST"
                  onsubmit="return checkField('name')">
                <div class="form-group">
                    <label for="email" class="col-xs-3 control-label">Имя</label>

                    <div class="col-xs-6">
                        <input type="text" class="form-control" name="value"
                               id="name" maxlength="63" oninput="removeHasError('name')"
                               placeholder="${candidate.getName()}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-3 col-xs-offset-6">
                        <button class="btn-success btn btn-block" type="submit">Сменить имя</button>

                    </div>
                </div>
            </form>

        <#--info--->
            <form role="form" class="form-horizontal" enctype="multipart/form-data" action="/profile?field=info"
                  method="POST"
                  onsubmit="return checkField('info')">
                <div class="form-group">
                    <label for="info" class="col-xs-3 control-label">Информация о кандидате</label>

                    <div class="col-xs-6">
                        <textarea class="form-control" rows="3" name="value"
                                  placeholder="${candidate.getInfo()}" id="info" maxlength="400"
                                  oninput="removeHasError('info')"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-3 col-xs-offset-6">
                        <button class="btn-success btn btn-block" type="submit">Изменить информацию</button>

                    </div>
                </div>
            </form>
        <#--achievements--->
            <form role="form" class="form-horizontal" enctype="multipart/form-data" action="/profile?field=achievements"
                  method="POST"
                  onsubmit="return checkField('achievements')">
                <div class="form-group">
                    <label for="achievements" class="col-xs-3 control-label">Достижения</label>

                    <div class="col-xs-6">
                            <textarea class="form-control" rows="3" name="value"
                                      placeholder="${candidate.getAchievements()}" id="achievements" maxlength="400"
                                      oninput="removeHasError('achievements')"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-3 col-xs-offset-6">
                        <button class="btn-success btn btn-block" type="submit">Изменить информацию</button>

                    </div>
                </div>
            </form>
        <#--election_program--->
            <form role="form" class="form-horizontal" enctype="multipart/form-data"
                  action="/profile?field=election_program"
                  method="POST"
                  onsubmit="return checkField('election_program')">
                <div class="form-group">
                    <label for="election_program" class="col-xs-3 control-label">Предвыборная программа</label>

                    <div class="col-xs-6">
                            <textarea class="form-control" rows="3" name="value"
                                      placeholder="${candidate.getElectionProgram()}" id="election_program"
                                      maxlength="400"
                                      oninput="removeHasError('election_program')"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-3 col-xs-offset-6">
                        <button class="btn-success btn btn-block" type="submit">Изменить информацию</button>

                    </div>
                </div>
            </form>
        <#--image--->
            <form role="form" class="form-horizontal" enctype="multipart/form-data"
                  action="/profile?field=election_program"
                  method="POST"
                  onsubmit="return checkField('image_src')">
                <div class="form-group">
                    <label for="election_program" class="col-xs-3 control-label">Изображение</label>

                    <div class="col-xs-6">
                        <input type="file" class="form-control image" name="new_image"
                               id="image_src" maxlength="63"
                               oninput="removeHasError('image_src')"
                               placeholder="${candidate.getParty().getSeatsInParliament()}" max="450"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-3 col-xs-offset-6">
                        <button class="btn-success btn btn-block" type="submit">Обновить изображение</button>

                    </div>
                </div>
            </form>
            <#if candidate.getParty()??>
            <#--seats--->
                <form role="form" class="form-horizontal" enctype="multipart/form-data" action="/profile?field=seats_in_parliament"
                      method="POST"
                      onsubmit="return checkField('seats_in_parliament')">
                    <div class="form-group">
                        <label for="seats" class="col-xs-3 control-label">Мест в парламенете на текущий момент</label>

                        <div class="col-xs-6">
                            <input type="number" class="form-control" name="value"
                                   id="seats_in_parliament" maxlength="63"
                                   oninput="removeHasError('seats_in_parliament')"
                                   placeholder="${candidate.getParty().getSeatsInParliament()}" max="450"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-3 col-xs-offset-6">
                            <button class="btn-success btn btn-block" type="submit">Изменить информацию</button>

                        </div>
                    </div>
                </form>
            </#if>


        </div>
    </div>
</div>

<hr>
</#macro>
<#macro scripts>
<script src="js/checkField.js"></script>
</#macro>
<#include "base.ftl">