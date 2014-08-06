package br.com.pursale.controllers;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Pedido;
import br.com.pursale.domain.Usuario;
import br.com.pursale.persistence.PedidoDAO;

@RequestScoped
@ManagedBean
public class PedidoView {
	
	@EJB
	private PedidoDAO pedidoDAO;
	
	private Pedido pedido;
	
	private Long id;
	
	private int tipo;
	
	private Usuario usuario;
	
	public Usuario getUsuario(){
		return usuario;
	}
	
	public int getTipo(){
		return tipo;
	}
	
	public Pedido getPedido(){
		return pedido;
	}
	
	public Long getId(){
		return id;
	}
	
	@PostConstruct
	public void init(){
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		id = Long.valueOf(params.get("id"));
		tipo = Integer.valueOf(params.get("tipo"));
		
		pedido = pedidoDAO.retrieveById(id);
		
		if(tipo == 0){ // Usuario = Comprador, pegar os dados do Vendedor
			usuario = pedido.getProduto().getUsuario();
		}else{ // Usuario = Vendedor, pegar os dados do Comprador
			usuario = pedido.getUsuario();
		}
	}
}
