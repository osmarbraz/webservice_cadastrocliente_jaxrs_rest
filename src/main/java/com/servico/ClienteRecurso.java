package com.servico;

import com.dao.ClienteDAO;
import com.dao.HashMapClienteDAO;
import com.entidade.Cliente;
import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author osmar
 */
@Path("/cliente")
public class ClienteRecurso {

    private ClienteDAO dao = HashMapClienteDAO.getInstancia();

    /**
     * Serviço que retorna a lista de cliente.
     *
     * curl http://localhost:8080/webservice_cliente_jaxrs_rest/rest/cliente
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLista() {
        System.out.println("Executando serviço getLista.");
        List<Cliente> listaClientes = dao.getLista();

        if (listaClientes.isEmpty()) {
            //Mensagem de retorno
            return Response.noContent().build();
        }

        return Response.ok(listaClientes).build();
    }

    /**
     * Serviço que retorna um cliente.
     *
     * curl http://localhost:8080/webservice_cliente_jaxrs_rest/rest/cliente/1
     *
     * @param clienteId Id do cliente a ser procurado.
     *
     * @return
     */
    @GET
    @Path("{clienteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("clienteId") String clienteId) {
        System.out.println("Executando serviço getCliente.");
        //Objeto cliente a ser procurado
        Cliente clienteParaProcurar = new Cliente();
        clienteParaProcurar.setClienteId(clienteId);        
        //Aplica o filtro no objeto cliente a ser procurado
        List lista = dao.aplicarFiltro(clienteParaProcurar);
        //Objeto de retorno
        Cliente cliente = null;
        //Se encontrar
        if (!lista.isEmpty()) {
            cliente = (Cliente) lista.iterator().next();
        }         
        //Verifica se encontrou o objeto cliente
        if (cliente != null) {
            //Mensagem de retorno
            return Response.ok(cliente, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Serviço que insere um cliente.
     *
     * curl -v -X POST -H "Content-Type: application/json" -d "{\"clienteId\":\"3\",\"nome\":\"Pedro\",\"cpf\":\"45678912399\"}" http://localhost:8080/webservice_cliente_jaxrs_rest/rest/cliente
     *
     * @param cliente Um novo cliente.
     * @return
     * @throws java.net.URISyntaxException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Cliente cliente) throws URISyntaxException {
        System.out.println("Executando serviço inserir.");
        dao.inserir(cliente);  
        //Mensagem de retorno
        return Response.ok("Cliente inserido!", MediaType.TEXT_PLAIN).build();
    }

    /**
     * Serviço que altera um cliente.
     *
     * curl -v -X PUT -H "Content-Type: application/json" -d "{\"clienteId\":\"3\",\"nome\":\"Antônio\",\"cpf\":\"45678912399\"}" http://localhost:8080/webservice_cliente_jaxrs_rest/rest/cliente/3
     *
     * @param cliente Objeto com os dados do cliente.
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(Cliente cliente) {
        System.out.println("Executando serviço alterar.");
        if (dao.alterar(cliente) != 0) {
            //Mensagem de retorno
            return Response.ok("Cliente alterado!", MediaType.TEXT_PLAIN).build();
        } else {
            return Response.notModified().build();
        }
    }

    /**
     * Serviço que exclui um cliente.
     *
     * curl -v -X DELETE http://localhost:8080/webservice_cliente_jaxrs_rest/rest/cliente/3
     *
     * @param clienteId Id do cliente a ser excluído.
     * @return
     */
    @DELETE
    @Path("{clienteId}")
    public Response excluir(@PathParam("clienteId") int clienteId) {
        System.out.println("Executando serviço excluir.");
        Cliente clienteExcluir = new Cliente();
        clienteExcluir.setClienteId(clienteId);
        if (dao.excluir(clienteExcluir)!= 0) {
            return Response.ok("Cliente excluído!", MediaType.TEXT_PLAIN).build();
        } else {
            return Response.notModified().build();
        }
    }
}
