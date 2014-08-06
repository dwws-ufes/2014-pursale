package br.com.pursale.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Pedido;
import br.com.pursale.persistence.PedidoDAO;

@Stateful
@LocalBean
@Model
public class PedidoList {
	
	@EJB
	private PedidoDAO pedidoDAO;
	
	private List<Pedido> pedidos;
	
	private List<Pedido> pedidosFinalizados;
	
	private int tipo;
	
	public List<Pedido> getPedidos(){
		return pedidos;
	}
	
	public List<Pedido> getPedidosFinalizados(){
		return pedidosFinalizados;
	}
	
	
	public int getTipo(){
		return tipo;
	}

	@PostConstruct
	public void init(){
		
		String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");
		tipo = Integer.valueOf(param);
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		
		pedidos = pedidoDAO.findByUserAtivos(session.getUsuario().getId(), tipo);
		pedidosFinalizados = pedidoDAO.findByUserFinalizados(session.getUsuario().getId(), tipo);
	}
	
	public String returnClassQualificacao(int status){
		
		if(status == 0) return "aguardando_qualificacao";
		
		if(status == 3) return "qualificado";
		
		if(tipo == 0 ){ // Comprador
			
			if(status == 1) return "aguardando_qualificacao";
			if(status == 2) return  "qualificado";
			
			
		}else{ // Vendedor
			
			if(status == 1) return "qualificado";
			if(status == 2) return "aguardando_qualificacao";
		}
		
		return "qualificado";
	}
}
