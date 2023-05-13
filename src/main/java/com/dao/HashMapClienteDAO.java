package com.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entidade.Cliente;

/**
 * Implementa a persitência em memória para cliente utilizando HashMap.
 *
 * @author osmarbraz
 */
public class HashMapClienteDAO implements ClienteDAO {

    private static Map<String, Cliente> mapa = new HashMap<>();
    
    private static HashMapClienteDAO instancia;

    //Inicializa o hashmap com dois clientes
    static {
        mapa.put("1", new Cliente("1", "João", "12345678912"));
        mapa.put("2", new Cliente("2", "Maria", "98765432121"));        
    }
     
    /** 
     * Método para garantir somente um objeto em memória.
     * 
     * @return Um objeto HashMapClienteDAO
     */
    public static HashMapClienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new HashMapClienteDAO();
        }         
        return instancia;               
    }
        
    /**
     * Construtor sem argumentos.
     */
    private HashMapClienteDAO() {
       
    }

    /**
     * Insere um cliente no hashmap.
     * 
     * @param obj Um cliente.
     * 
     * @return Se conseguiu realizar a inclusão.
     */
    @Override
    public boolean inserir(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            boolean tem = mapa.containsKey(cliente.getClienteId());
            if (tem == false) {
                mapa.put(cliente.getClienteId(), cliente);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Altera um cliente no hashmap.
     * 
     * @param obj Um cliente
     * 
     * @return Se conseguiu realizar a alteração.
     */
    @Override
    public int alterar(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            boolean tem = mapa.containsKey(cliente.getClienteId());
            if (tem == true) {
                Cliente c = (Cliente) mapa.get(cliente.getClienteId());
                c.setNome(cliente.getNome());
                c.setCpf(cliente.getCpf());
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Exclui um cliente do hashmap.
     * 
     * @param obj Um cliente
     * 
     * @return Se conseguiu realizra a exclusão.
     */
    @Override
    public int excluir(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }

        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            boolean tem = mapa.containsKey(cliente.getClienteId());
            if (tem == true) {
                mapa.remove(cliente.getClienteId());
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Retorna a lista de clientes.
     * 
     * @return 
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List getLista() {
        List lista = new LinkedList();
        Iterator it = mapa.values().iterator();
        while (it.hasNext()) { //Avança enquanto tiver objetos
            Cliente c = (Cliente) it.next();
            lista.add(c);
        }
        return lista;
    }

    /**
     * Filtra clientes do hashmap.
     * 
     * @param obj Um cliente
     * @return 
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List aplicarFiltro(Object obj) {
        if (obj != null) {
            Cliente cliente = (Cliente) obj;
            List lista = new LinkedList();
            Iterator it = mapa.values().iterator();

            //Filtro para clienteId
            if (cliente.getClienteId() != null) {
                while (it.hasNext()) { //Avança enquanto tiver objetos
                    Cliente c = (Cliente) it.next();
                    if (c.getClienteId().equalsIgnoreCase(cliente.getClienteId())) {
                        lista.add(c);
                    }
                }
            } else {
                //Filtro para nome
                if (cliente.getNome() != null) {
                    while (it.hasNext()) { //Avança enquanto tiver objetos
                        Cliente c = (Cliente) it.next();
                        if (c.getNome().equalsIgnoreCase(cliente.getNome())) {
                            lista.add(c);
                        }
                    }
                } else {
                    //Filtro para CPF
                    if (cliente.getCpf() != null) {
                        while (it.hasNext()) { //Avança enquanto tiver objetos
                            Cliente c = (Cliente) it.next();
                            if (c.getCpf().equalsIgnoreCase(cliente.getCpf())) {
                                lista.add(c);
                            }
                        }
                    }
                }
            }

            return lista;
        } else {
            return null;
        }
    } 
}
