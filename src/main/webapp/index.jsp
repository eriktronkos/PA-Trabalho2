<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Segundo Trabalho de PA</title>
        <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" />
        <script type="text/javascript" src="webjars/jquery/1.11.1/jquery.min.js"></script>
        <script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/trabalho2.js"></script>
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
                            <a class="navbar-brand" href="#">Trabalho 2 - Programação Avançada</a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li  class="active"><a href="/livros/busca">Busca</a></li>
                                <li><a href="/livros/crud">Adicionar/Deletar</a></li>
                            </ul>
                        </div><!--/.nav-collapse -->
                    </div>
                </nav>
            </div>
            <div class="container" style="margin-top: 10%;">
                <div class="panel panel-info">
                    <div class="panel-heading">Buscar Livro</div>
                    <div class="panel-body">
                        <form id="form_busca" method="POST" action="busca" class="form-group">

                            <div class="row">
                                <div class="form-group">
                                    <div class="col-sm-8">
                                        <label for="patrimonio">Patrimônio:</label>
                                        <input class="form-control" type="text" id="input_patrimonio" name="patrimonio" oninput="disableAllButPatrimonio()"
                                               onchange="onChangePatrimonio()" size="30"/>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="titulo">Título:</label>
                                        <input class="form-control" type="text" id="input_titulo" name="titulo"
                                               value=""  size="30"/>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="tituloAND" value="tituloAND"> E
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="tituloOR" value="tituloOR"> OU
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="autoria">Autoria:</label>
                                        <input class="form-control" type="text" id="input_autoria" name="autoria"
                                               value=""  size="30"/>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="autoriaAND" value="autoriaAND"> E
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="autoriaOR" value="autoriaOR"> OU
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="veiculo">Veículo:</label>
                                        <input class="form-control" type="text" id="input_veiculo" name="veiculo"
                                               value=""  size="30"/>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="veiculoAND" value="veiculoAND"> E
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="veiculoOR" value="veiculoOR"> OU
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-2">
                                    <label>Data de publicação:</label>
                                </div>
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="data_de">De:</label>
                                        <input class="form-control" type="date" id="input_data_de" name="data_de"
                                               value=""  size="30"/>
                                        <label for="data_ate">Até:</label>
                                        <input class="form-control" type="date" id="input_data_ate" name="data_ate"
                                               value=""  size="30"/>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="dataAND" value="dataAND"> E
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="dataOR" value="dataOR"> OU
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="palavras_chave">Palavras-chave:</label>
                                        <input class="form-control" type="text" id="input_palavras_chave" name="palavras_chave"
                                               value=""  size="30"/>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="palavraschaveAND" value="palavraschaveAND"> E
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" id="palavraschaveOR" value="palavraschaveOR"> OU
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-offset-5">
                                <a class="btn btn-default" onClick="submitForm('form_busca');">
                                    Buscar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                <table class="table">
                    <tr>
                        <th>ID</th>
                        <th>Autor</th>
                        <th>Título</th>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </div>
    </body>
</html>
