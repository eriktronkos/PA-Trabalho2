
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Segundo Trabalho de PA</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" />
        <script type="text/javascript" src="js/mercury.js"></script>
        <script type="text/javascript" src="js/jquery-2.2.4.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <nav class="navbar navbar-inverse navbar-fixed-top">
                    <div class="container">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="#">Trabalho 1 - Programação Avançada</a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li><a href="/livros/busca">Busca</a></li>
                                <li class="active"><a href="/livros/crud">Adicionar/Deletar</a></li>
                            </ul>
                        </div><!--/.nav-collapse -->
                    </div>
                </nav>
            </div>
            <div class="container" style="margin-top: 10%;">
                <div class="panel panel-success">
                    <div class="panel-heading">Adicionar Livro</div>
                    <div class="panel-body">
                        <form id="form_1_id" method="GET" action="crud" class="form-inline">

                            <div class="col-sm-8">
                                <input id="botao_escolhido_form_1_id" type="hidden" name="botaoEscolhido" value="CREATE"/>

                                <div class="form-group">
                                    <label for="titulo">Título</label>
                                    <input type="text" name="titulo"
                                           value=""  size="30"/>
                                </div>
                                <div class="form-group">
                                    <label for="autor">Autor</label>
                                    <input type="text" name="autor"
                                           value=""  size="30"/>
                                </div>
                            </div>
                            <div class="col-sm-offset-2">
                                <a class="btn btn-default" href="#" onClick="Javascript:submitForm('form_1_id');">
                                    Adicionar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">Editar Livro</div>
                    <div class="panel-body">
                        <form id="form_3_id" method="GET" action="crud" class="form-inline">
                            <input id="botao_escolhido_form_3_id" type="hidden" name="botaoEscolhido" value="UPDATE"/>
                            <div class="form-group">
                                <label for="id">ID</label>
                                <input type="text" name="id"
                                       value=""  size="30"/>
                            </div>
                            <div class="form-group">
                                <label for="titulo">Título</label>
                                <input type="text" name="titulo"
                                       value=""  size="30"/>
                            </div>
                            <div class="form-group">
                                <label for="autor">Autor</label>
                                <input type="text" name="autor"
                                       value=""  size="30"/>
                            </div>
                            <a class="btn btn-default" href="#" onClick="Javascript:submitForm('form_3_id');">
                                Editar
                            </a>
                        </form>
                    </div>
                </div>
                <div class="panel panel-danger">
                    <div class="panel-heading">Deletar Livro</div>
                    <div class="panel-body">
                        <form id="form_2_id" method="GET" action="crud" class="form-inline">
                            <input id="botao_escolhido_form_2_id" type="hidden" name="botaoEscolhido" value="DELETE"/>
                            <div class="form-group">
                                <label for="id">ID</label>
                                <input type="text" name="id"
                                       value=""  size="30"/>
                            </div>
                            <a class="btn btn-default" href="#" onClick="Javascript:submitForm('form_2_id');">
                                Deletar
                            </a>
                        </form>
                    </div>
                </div>
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${requestScope.Dialogo}</strong>
                </div>
                </body>
                </html>
