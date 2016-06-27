package tronkos.erik.trabalho2.DAO;

import tronkos.erik.trabalho2.DTOs.RespostaCompletaDTO;
import tronkos.erik.trabalho2.DTOs.RespostaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.json.JsonObject;
import tronkos.erik.trabalho2.utils.Utils;

public class BiblioPDFDAO extends BaseDAO {

//------------------------------------------------------------------------------    
    public RespostaCompletaDTO buscarLista(JsonObject dados) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = null;

        String patrimonio = dados.getString("patrimonio");
        String[] palavrasDaBuscaTitulo = null;
        String[] palavrasDaBuscaAutoria = null;
        String[] palavrasDaBuscaVeiculo = null;
        String[] palavrasDaBuscaChave = null;
        Date dataDe = null;
        Date dataAte = null;
        Map<String, Boolean> checkboxes = null;

        if ("".equals(patrimonio)) {

            palavrasDaBuscaTitulo = extrairPalavrasDaBusca(dados.getString("titulo"));
            palavrasDaBuscaAutoria = extrairPalavrasDaBusca(dados.getString("autoria"));
            palavrasDaBuscaVeiculo = extrairPalavrasDaBusca(dados.getString("veiculo"));
            palavrasDaBuscaChave = extrairPalavrasDaBusca(dados.getString("palavras_chave"));

            if (!"".equals(dados.getString("data_de"))) {
                dataDe = new Timestamp(Long.parseLong(dados.getString("data_de")));
            }
            if (!"".equals(dados.getString("data_ate"))) {
                dataAte = new Timestamp(Long.parseLong(dados.getString("data_ate")));
            }

            checkboxes = new HashMap<>();
            JsonObject jsonCheckboxes = dados.getJsonObject("checkboxes");
            Iterator<?> keys = jsonCheckboxes.keySet().iterator();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (jsonCheckboxes.getBoolean(key)) {
                    checkboxes.put(key, Boolean.TRUE);
                }
            }
        }
        String preparedStatement;
        if ("".equals(patrimonio)) {
            preparedStatement = prepararComandoSQL(palavrasDaBuscaTitulo, palavrasDaBuscaAutoria, palavrasDaBuscaVeiculo, palavrasDaBuscaChave, dataDe, dataAte, checkboxes);
        } else {
            preparedStatement = "SELECT T1.patrimonio, T1.titulo, T1.autoria, (count(*)) AS nrohits \n"
                    + "FROM dadoscatalogo T1 WHERE T1.patrimonio = " + patrimonio + " GROUP BY T1.patrimonio, T1.titulo, T1.autoria;";
        }
        try (Connection conexao = getConnection()) {
            PreparedStatement comandoSQL = conexao.prepareStatement(preparedStatement);

            ResultSet rs = comandoSQL.executeQuery();
            while (rs.next()) {
                umaRefDTO = new RespostaDTO();
                umaRefDTO.setPatrimonio(Long.toString(rs.getLong("patrimonio")));
                umaRefDTO.setTitulo(rs.getString("titulo"));
                umaRefDTO.setAutoria(rs.getString("autoria"));
                listaRefsDTO.addResposta(umaRefDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaRefsDTO;
    }
//------------------------------------------------------------------------------    

    private String[] extrairPalavrasDaBusca(String busca) {
        if ("".equals(busca)) {
            return new String[0];
        }
        busca = Utils.removeDiacriticals(busca);
        String[] temp = busca.split(" ");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }
        return temp;
    }
//------------------------------------------------------------------------------    

    private String prepararComandoSQL(String[] palavrasDaBuscaTitulo, String[] palavrasDaBuscaAutoria, String[] palavrasDaBuscaVeiculo, String[] palavrasDaBuscaChave, Date dataDe, Date dataAte, Map<String, Boolean> checkboxes) {
        /*
Exemplo:
        
SELECT T1.patrimonio, T1.titulo, T1.autoria, (count(*)) AS nrohits
FROM dadoscatalogo T1
INNER JOIN palavrastitulonormal T2 ON(T1.patrimonio = T2.patrimonio) WHERE

T2.palavra_titulo_normal LIKE 'MAVEN'        
OR        
T2.palavra_titulo_normal LIKE 'STYLES'
        
GROUP BY T1.patrimonio, T1.titulo, T1.autoria ORDER BY nrohits DESC, titulo ASC;        
         */
        String inicioSelectExterno
                = "SELECT T1.patrimonio, T1.titulo, T1.autoria, (count(*)) AS nrohits \n"
                + "FROM dadoscatalogo T1 \n"
                + "INNER JOIN palavrastitulonormal T2 ON(T1.patrimonio = T2.patrimonio) \n"
                + "INNER JOIN palavrasautorianormal T3 ON(T1.patrimonio = T3.patrimonio) \n"
                + "INNER JOIN palavrasveiculonormal T4 ON(T1.patrimonio = T4.patrimonio) \n"
                + "INNER JOIN palavras_chave T5 ON(T1.patrimonio = T5.patrimonio) WHERE 1=1 \n";

        String finalSelectExterno
                = "GROUP BY T1.patrimonio, T1.titulo, T1.autoria ORDER BY nrohits DESC, titulo ASC;";

        String comando = "";

        if (palavrasDaBuscaTitulo.length > 0 && checkboxes.containsKey("tituloAND")) {

            String baseComandoTitulo = "T2.palavra_titulo_normal LIKE ";
            comando += " AND ( ";
            for (int i = 0; i < palavrasDaBuscaTitulo.length; i++) {
                comando = comando + baseComandoTitulo + '\'' +palavrasDaBuscaTitulo[i]+'\'' + " \n";
                if (i < (palavrasDaBuscaTitulo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += " ) \n";
        }

        if (palavrasDaBuscaAutoria.length > 0 && checkboxes.containsKey("autoriaAND")) {
            String baseComandoAutoria = "T3.palavra_autoria_normal LIKE ";
            comando += " AND ( ";
            for (int i = 0; i < palavrasDaBuscaAutoria.length; i++) {
                comando = comando + baseComandoAutoria + '\'' + palavrasDaBuscaAutoria[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaAutoria.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += " ) \n";
        }
        if (palavrasDaBuscaVeiculo.length > 0 && checkboxes.containsKey("veiculoAND")) {
            String baseComandoVeiculo = "T4.palavra_veiculo_normal LIKE ";
            comando += " AND ( ";
            for (int i = 0; i < palavrasDaBuscaVeiculo.length; i++) {
                comando = comando + baseComandoVeiculo + '\'' + palavrasDaBuscaVeiculo[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaVeiculo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += ") \n";
        }

        if (palavrasDaBuscaChave.length > 0 && checkboxes.containsKey("palavraschaveAND")) {
            String baseComandoChave = "T5.palchavenormal LIKE ";
            comando += " AND ( ";
            for (int i = 0; i < palavrasDaBuscaVeiculo.length; i++) {
                comando = comando + baseComandoChave + '\'' + palavrasDaBuscaChave[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaChave.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += ") \n";
        }
        if (dataDe != null && checkboxes.containsKey("dataAND")) {
            comando += "AND T1.data_publicacao >= " + '\'' + dataDe.toString() + '\'' + "\n";
        }
        if (dataAte != null && checkboxes.containsKey("dataAND")) {
            comando += "AND T1.data_publicacao <= " + '\'' +dataAte.toString() + '\'' + "\n";
        }

        //----- comandos OR
        if (checkboxes.containsKey("tituloOR") || checkboxes.containsKey("autoriaOR") || checkboxes.containsKey("veiculoOR") || checkboxes.containsKey("palavraschaveOR")) {
            comando += "AND ( ";
        }

        if (palavrasDaBuscaTitulo.length > 0 && checkboxes.containsKey("tituloOR")) {

            String baseComandoTitulo = "T2.palavra_titulo_normal LIKE ";
            comando += " OR ( ";
            for (int i = 0; i < palavrasDaBuscaTitulo.length; i++) {
                comando = comando + baseComandoTitulo + '\'' + palavrasDaBuscaTitulo[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaTitulo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += " ) \n";
        }

        if (palavrasDaBuscaAutoria.length > 0 && checkboxes.containsKey("autoriaOR")) {
            String baseComandoAutoria = "T3.palavra_autoria_normal LIKE ";
            comando += " OR ( ";
            for (int i = 0; i < palavrasDaBuscaAutoria.length; i++) {
                comando = comando + baseComandoAutoria + '\'' + palavrasDaBuscaAutoria[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaAutoria.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += " ) \n";
        }
        if (palavrasDaBuscaVeiculo.length > 0 && checkboxes.containsKey("veiculoOR")) {
            String baseComandoVeiculo = "T4.palavra_veiculo_normal LIKE ";
            comando += " OR ( ";
            for (int i = 0; i < palavrasDaBuscaVeiculo.length; i++) {
                comando = comando + baseComandoVeiculo + '\'' + palavrasDaBuscaVeiculo[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaVeiculo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += ") \n";
        }

        if (palavrasDaBuscaChave.length > 0 && checkboxes.containsKey("palavraschaveOR")) {
            String baseComandoChave = "T5.palchavenormal LIKE ";
            comando += " OR ( ";
            for (int i = 0; i < palavrasDaBuscaVeiculo.length; i++) {
                comando = comando + baseComandoChave + '\'' + palavrasDaBuscaChave[i] + '\'' + " \n";
                if (i < (palavrasDaBuscaChave.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando += ") \n";
        }

        if (dataDe != null && checkboxes.containsKey("dataOR")) {
            comando += "OR T1.data_publicacao >= " + '\'' + dataDe.toString() + '\'' + "\n";
        }
        if (dataAte != null && checkboxes.containsKey("dataOr")) {
            comando += "OR T1.data_publicacao <= " + '\'' + dataAte.toString() + '\'' + "\n";
        }
        //----- fim comandos OR
        if (checkboxes.containsKey("tituloOR") || checkboxes.containsKey("autoriaOR") || checkboxes.containsKey("veiculoOR") || checkboxes.containsKey("palavraschaveOR")) {
            comando += " ) \n";
        }

        comando = inicioSelectExterno + comando + finalSelectExterno;
        return comando;
    }
    //------------------------------------------------------------------------------

    public String salvarNovo(JsonObject dados) {
        ResultSet rst = null;
        long patrimonio = 0L;
        String titulo = dados.getString("titulo").trim();

        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);
            // PRIMEIRA TABELA
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "INSERT INTO dadoscatalogo (titulo,autoria) VALUES(?,?) "
                    + "RETURNING patrimonio;");

            comandoSQL.setString(1, titulo);
            comandoSQL.setString(2, dados.getString("autoria"));
            rst = comandoSQL.executeQuery();
            rst.next();
            patrimonio = rst.getLong("patrimonio");

            String[] palavrasDaBuscaTitulo = extrairPalavrasDaBusca(dados.getString("titulo"));
            String[] palavrasDaBuscaAutoria = extrairPalavrasDaBusca(dados.getString("autoria"));
            String[] palavrasDaBuscaVeiculo = extrairPalavrasDaBusca(dados.getString("veiculo"));
            String[] palavrasDaBuscaChave = extrairPalavrasDaBusca(dados.getString("palavras_chave"));
            Date dataDe = new Timestamp(Long.parseLong(dados.getString("data_publicacao")));

            for (String cadaPalavra : palavrasDaBuscaTitulo) {
                PreparedStatement comandoSQL2 = conexao.prepareStatement(
                        "INSERT INTO palavrastitulonormal (palavra_titulo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL2.setString(1, cadaPalavra);
                comandoSQL2.setLong(2, patrimonio);
                comandoSQL2.executeUpdate();
            }

            for (String cadaPalavra : palavrasDaBuscaAutoria) {
                PreparedStatement comandoSQL3 = conexao.prepareStatement(
                        "INSERT INTO palavrasautorianormal (palavra_autoria_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL3.setString(1, cadaPalavra);
                comandoSQL3.setLong(2, patrimonio);
                comandoSQL3.executeUpdate();
            }

            for (String cadaPalavra : palavrasDaBuscaVeiculo) {
                PreparedStatement comandoSQL4 = conexao.prepareStatement(
                        "INSERT INTO palavrasveiculonormal (palavra_veiculo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL4.setString(1, cadaPalavra);
                comandoSQL4.setLong(2, patrimonio);
                comandoSQL4.executeUpdate();
            }

            for (String cadaPalavra : palavrasDaBuscaChave) {
                PreparedStatement comandoSQL5 = conexao.prepareStatement(
                        "INSERT INTO palavras_chave (palchavenormal ,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL5.setString(1, cadaPalavra);
                comandoSQL5.setLong(2, patrimonio);
                comandoSQL5.execute();
            }
            // COMMIT TRANSACTION
            conexao.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"patrimonio\":\"" + Long.toString(patrimonio) + "\"}";
    }
//------------------------------------------------------------------------------
    
     public String deletarLivro(JsonObject dados) {
        long patrimonio = Long.parseLong(dados.getString("patrimonio"));

        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);
            // PRIMEIRA TABELA
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "DELETE FROM dadoscatalogo T1 where T1.patrimonio =" + patrimonio + ";");

            comandoSQL.execute();
            
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrastitulonormal T1 where T1.patrimonio =" + patrimonio + ";");
            
            comandoSQL.execute();
            
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrasautorianormal T1 where T1.patrimonio =" + patrimonio + ";");
            
            comandoSQL.execute();
            
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrasveiculonormal T1 where T1.patrimonio =" + patrimonio + ";");
            
            comandoSQL.execute();
            
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavras_chave T1 where T1.patrimonio =" + patrimonio + ";");
            
            comandoSQL.execute();
            // COMMIT TRANSACTION
            conexao.commit();

        } catch (Exception e) {
            e.printStackTrace();
            return "{\'status\':\'false\'}";
        }
        return "{\'status\':\'true\'}";
    }

}
