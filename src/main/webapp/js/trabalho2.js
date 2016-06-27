
function submitForm(formID) {
    var formData = $('#' + formID).serializeArray();
    var dataFinal = {};
    var url = "";
    for (i = 0; i < formData.length; i++) {
        dataFinal[formData[i]['name']] = formData[i]['value'];
    }

    if (formID == 'form_busca') {
        url = 'http://localhost:8080/trabalho2/busca';

        if (dataFinal['data_de'] != "" && dataFinal['data_de'] != null) {
            dataFinal['data_de'] = Date.parse(dataFinal['data_de']).toString();
        }
        if (dataFinal['data_ate'] != "" && dataFinal['data_ate'] != null) {
            dataFinal['data_ate'] = Date.parse(dataFinal['data_ate']).toString();
        }

        dataFinal['checkboxes'] = {};
        if ($('#tituloAND').is(':checked')) {
            dataFinal['checkboxes']['tituloAND'] = true;
        }
        if ($('#tituloOR').is(':checked')) {
            dataFinal['checkboxes']['tituloOR'] = true;
        }
        if ($('#autoriaAND').is(':checked')) {
            dataFinal['checkboxes']['autoriaAND'] = true;
        }
        if ($('#autoriaOR').is(':checked')) {
            dataFinal['checkboxes']['autoriaOR'] = true;
        }
        if ($('#veiculoAND').is(':checked')) {
            dataFinal['checkboxes']['veiculoAND'] = true;
        }
        if ($('#veiculoOR').is(':checked')) {
            dataFinal['checkboxes']['veiculoOR'] = true;
        }
        if ($('#dataAND').is(':checked')) {
            dataFinal['checkboxes']['dataAND'] = true;
        }
        if ($('#dataOR').is(':checked')) {
            dataFinal['checkboxes']['dataOR'] = true;
        }
        if ($('#palavraschaveAND').is(':checked')) {
            dataFinal['checkboxes']['palavraschaveAND'] = true;
        }
        if ($('#palavraschaveOR').is(':checked')) {
            dataFinal['checkboxes']['palavraschaveOR'] = true;
        }

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(dataFinal),
            complete: function () {
                alert(JSON.stringify(dataFinal));
            },
            success: function (response) {
                alert(JSON.stringify(response));
                for (i = 0; i < response['arrayDeRespostas'].length; i++) {
                    $('#bodyResposta').append('<tr><td>' + response['arrayDeRespostas'][i]['patrimonio'] + '</td><td>' + response['arrayDeRespostas'][i]['titulo'] + '</td><td>' + response['arrayDeRespostas'][i]['autoria'] + '</td></tr>');
                }
            },
            dataType: "json",
            contentType: "application/json"
        });
    } else if (formID == 'form_adicao') {
        url = 'http://localhost:8080/trabalho2/catalogo';

        for (i = 0; i < dataFinal.length; i++) {
            if (dataFinal[i] == null || dataFinal[i] == "") {
                alert("Favor preencher todos os campos");
                return;
            }
        }
        dataFinal['data_publicacao'] = Date.parse(dataFinal['data_publicacao']).toString();

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(dataFinal),
            complete: function () {
                //alert(JSON.stringify(dataFinal));
            },
            success: function (response) {
                alert("Adicionado livro com número de Patrimônio: " + response['patrimonio']);
            },
            dataType: "json",
            contentType: "application/json"
        });
    } else if (formID == 'form_delete') {
        url = 'http://localhost:8080/trabalho2/delete';

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(dataFinal),
            complete: function () {
                //alert('Deletado livro de patrimônio: '+ dataFinal['patrimonio']);
            },
            success: function (response) {
                if (response['status'] == 'true') {
                    alert('Deletado livro de patrimônio: ' + dataFinal['patrimonio']);
                } else {
                    alert('Erro');
                }

            },
            dataType: "json",
            contentType: "application/json"
        });
    }

}

function disableAllButPatrimonio() {
    $('#input_titulo').attr('disabled', 'disabled');
    $('#input_autoria').attr('disabled', 'disabled');
    $('#input_veiculo').attr('disabled', 'disabled');
    $('#input_data_de').attr('disabled', 'disabled');
    $('#input_data_ate').attr('disabled', 'disabled');
    $('#input_palavras_chave').attr('disabled', 'disabled');

    $('#tituloAND').attr('disabled', 'disabled');
    $('#tituloOR').attr('disabled', 'disabled');
    $('#autoriaAND').attr('disabled', 'disabled');
    $('#autoriaOR').attr('disabled', 'disabled');
    $('#veiculoAND').attr('disabled', 'disabled');
    $('#veiculoOR').attr('disabled', 'disabled');
    $('#dataAND').attr('disabled', 'disabled');
    $('#dataOR').attr('disabled', 'disabled');
    $('#palavraschaveAND').attr('disabled', 'disabled');
    $('#palavraschaveOR').attr('disabled', 'disabled');
}

function onChangePatrimonio() {
    if ($('#input_patrimonio').val() == "") {
        $('#input_titulo').attr('disabled', false);
        $('#input_autoria').attr('disabled', false);
        $('#input_veiculo').attr('disabled', false);
        $('#input_data_de').attr('disabled', false);
        $('#input_data_ate').attr('disabled', false);
        $('#input_palavras_chave').attr('disabled', false);

        $('#tituloAND').attr('disabled', false);
        $('#tituloOR').attr('disabled', false);
        $('#autoriaAND').attr('disabled', false);
        $('#autoriaOR').attr('disabled', false);
        $('#veiculoAND').attr('disabled', false);
        $('#veiculoOR').attr('disabled', false);
        $('#dataAND').attr('disabled', false);
        $('#dataOR').attr('disabled', false);
        $('#palavraschaveAND').attr('disabled', false);
        $('#palavraschaveOR').attr('disabled', false);
    }
}