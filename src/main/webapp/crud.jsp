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
                                <li><a href="/trabalho2">Busca</a></li>
                                <li class="active"><a href="/trabalho2/catalogo">Adicionar/Deletar</a></li>
                            </ul>
                        </div><!--/.nav-collapse -->
                    </div>
                </nav>
            </div>
            <div class="container" style="margin-top: 10%;">
                <div class="panel panel-success">
                    <div class="panel-heading">Adicionar Livro</div>
                    <div class="panel-body">
                        <form id="form_adicao" method="POST" class="form-group">

                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="titulo">Título:</label>
                                        <input class="form-control" type="text" id="input_titulo" name="titulo" required
                                               value=""  size="30"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="autoria">Autoria:</label>
                                        <input class="form-control" type="text" id="input_autoria" name="autoria" required
                                               value=""  size="30"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="veiculo">Veículo:</label>
                                        <input class="form-control" type="text" id="input_veiculo" name="veiculo" required
                                               value=""  size="30"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="data_de">Data de publicação:</label>
                                        <input class="form-control" type="date" id="input_data_publicacao" name="data_publicacao" required
                                               value=""  size="30"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="palavras_chave">Palavras-chave:</label>
                                        <input class="form-control" type="text" id="input_palavras_chave" name="palavras_chave" required
                                               value=""  size="30"/>
                                    </div>
                                </div>
                            </div>
                            <a class="btn btn-default" href="#" onClick="submitForm('form_adicao');">
                                Adicionar
                            </a>
                        </form>
                    </div>
                </div>
            </div>
            <div class="panel panel-danger">
                <div class="panel-heading">Deletar Livro</div>
                <div class="panel-body">
                    <form id="form_delete" method="POST" class="form-inline">
                        <div class="form-group">
                            <label for="id">Patrimônio:</label>
                            <input type="text" name="patrimonio"
                                   value=""  size="30"/>
                        </div>
                        <a class="btn btn-default" href="#" onClick="submitForm('form_delete');">
                            Deletar
                        </a>
                    </form>
                </div>
            </div>
    </body>
</html>
