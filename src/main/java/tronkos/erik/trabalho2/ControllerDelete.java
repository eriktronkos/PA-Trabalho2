package tronkos.erik.trabalho2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonArray;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tronkos.erik.trabalho2.DAO.BiblioPDFDAO;
import tronkos.erik.trabalho2.DTOs.RespostaCompletaDTO;

public class ControllerDelete extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Não é um conjunto de pares nome-valor,
        // então tem que ler como se fosse um upload de arquivo...
        BufferedReader br = new BufferedReader(
                                  new  InputStreamReader(
                                           request.getInputStream(),"UTF8"));
        String textoDoJson = br.readLine();
        
        JsonObject jsonObjectDeJava = null;
        // Ler e fazer o parsing do String para o "objeto json" java
        try (   //Converte o string em "objeto json" java
                // Criar um JsonReader.
                JsonReader readerDoTextoDoJson = 
                        Json.createReader(new StringReader(textoDoJson))) {
                // Ler e fazer o parsing do String para o "objeto json" java
                jsonObjectDeJava = readerDoTextoDoJson.readObject();
                // Acabou, então fechar o reader.
        }catch(Exception e){
            e.printStackTrace();
        }
        
        BiblioPDFDAO dao = new BiblioPDFDAO();
        
        String res = dao.deletarLivro(jsonObjectDeJava);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return;
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}