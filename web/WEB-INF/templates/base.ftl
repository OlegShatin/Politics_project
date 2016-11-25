<#ftl encoding="utf-8"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
<@title/>
    <!--style-->
    <link href="/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<@custom_css/>

</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/news">Новости</a>
                </li>
                <li>
                    <a href="/candidates">Кандидаты</a>
                </li>
                <li>
                    <a href="/results">Статистика</a>
                </li>
                <li>
                    <a href="/profile">Мой профиль</a>
                </li>
                <li>
                    <a href="/conversations">Мои обращения</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><@in_out/></li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>
<div class="container">
<@content/>
    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">

            </div>
        </div>
        <!-- /.row -->
    </footer>
</div>
<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<@scripts/>
</body>
</html>