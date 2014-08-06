package br.com.pursale.controllers;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Pedido;
import br.com.pursale.domain.Produto;
import br.com.pursale.domain.Usuario;
import br.com.pursale.persistence.PedidoDAO;
import br.com.pursale.persistence.ProdutoDAO;

@Stateful
@LocalBean
@Model
public class AddPedido {
	
	@EJB
	private PedidoDAO pedidoDAO;
	
	@EJB
	private ProdutoDAO produtoDAO;
	
	private Produto produto;
	
	private int quantidade = 1;
	
	public String save(){
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		
		Usuario usuario = session.getUsuario();
		
		if(quantidade > 0 && quantidade <= produto.getQuantidade()){ // Se a quantidade eh valida
			
			Date date = new Date(System.currentTimeMillis());
			
			Pedido pedido = new Pedido();
			
			pedido.setProduto(produto);
			pedido.setData(date);
			pedido.setQuantidade(quantidade);
			pedido.setUsuario(usuario);
			pedido.setValorTotal(quantidade * produto.getPreco());
			
			if(pedidoDAO.save(pedido) != null){
				
				int qntProduto = produto.getQuantidade() - quantidade;
				produto.setQuantidade(qntProduto);
				
				if(qntProduto == 0){
					produto.setStatus(0);
				}
				
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Compra do produto \"" + produto.getNome() + "\" realizada com sucesso!"));
				
				produtoDAO.save(produto);
				
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Falha ao comprar o produto \"" + produto.getNome() + "\""));
			}
			
			return "/pedidos/list.faces?faces-redirect=true&tipo=0";
			
		}else{
			return null;
		}
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
