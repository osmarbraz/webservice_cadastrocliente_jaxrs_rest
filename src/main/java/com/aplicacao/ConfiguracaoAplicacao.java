package com.aplicacao;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author osmar
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ConfiguracaoAplicacao extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Não modifique o método addRestResourceClasses(). 
     * 
     * É preenchido automaticamente com todos os recursos definidos no projeto. 
     * Se necessário, comente a chamada deste método em getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.servico.ClienteRecurso.class);
    }
}
