package br.com.pursale.controllers;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Pedido;
import br.com.pursale.domain.Reputacao;
import br.com.pursale.domain.Usuario;
import br.com.pursale.persistence.PedidoDAO;
import br.com.pursale.persistence.ReputacaoDAO;
import br.com.pursale.persistence.UsuarioDAO;

@Stateful
@LocalBean
@Model
public class AddReputacao {
	
	@EJB
	private ReputacaoDAO reputacaoDAO;
	
	@EJB
	private PedidoDAO pedidoDAO;
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
	private Reputacao reputacao = new Reputacao();
	
	private long id;
	
	private int tipo;
	
	public int getTipo(){
		return tipo;
	}
	
	public long getId(){
		return id;
	}
	
	public Reputacao getReputacao(){
		return reputacao;
	}
	
	@PostConstruct
	public void init(){
		
		reputacao.setAvaliacao(999);
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		Usuario usuario = session.getUsuario();
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		id = Long.valueOf(params.get("id"));
		tipo = Integer.valueOf(params.get("tipo"));
		
		Pedido pedido = pedidoDAO.retrieveById(id);
		
		reputacao.setPedido(pedido);
		
		if(tipo == 0){ // Usuario = Comprador, pegar os dados do Vendedor
			reputacao.setComprador(usuario);
			reputacao.setVendedor(pedido.getProduto().getUsuario());
			reputacao.setTipo(1);
		}else{ // Usuario = Vendedor, pegar os dados do Comprador
			reputacao.setVendedor(usuario);
			reputacao.setComprador(pedido.getUsuario());
			reputacao.setTipo(2);
		}
	}
	
	public String save(){ 
		
		Date date = new Date(System.currentTimeMillis());
		reputacao.setData(date);
		
		if(tipo == 0){ // Usuario = Comprador, esta qualificando o vendedor
			reputacao.getVendedor().setReputacao(reputacao.getVendedor().getReputacao() -1 + reputacao.getAvaliacao());
			usuarioDAO.save(reputacao.getVendedor());
			
			reputacao.getPedido().setStatus(reputacao.getPedido().getStatus() + 1);
			pedidoDAO.save(reputacao.getPedido());
			
		}else{ // Usuario = Vendedor, esta qualificando o comprador
			reputacao.getComprador().setReputacao(reputacao.getComprador().getReputacao() -1 + reputacao.getAvaliacao());
			usuarioDAO.save(reputacao.getComprador());
			
			reputacao.getPedido().setStatus(reputacao.getPedido().getStatus() + 2);
			pedidoDAO.save(reputacao.getPedido());
		}
		
		if(reputacaoDAO.save(reputacao) != null){
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Qualificação realizada com sucesso!"));
			
			reputacao = new Reputacao();
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro ao realizar a qualificação."));
		}
		
		return "/pedidos/list.faces?tipo="+tipo+"&id="+id;
	}
}
