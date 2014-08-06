package br.com.pursale.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped 
@ManagedBean
public class Menu {
	
	private String localhost = "http://127.0.0.1:8080/Pursale/";
	
	private String meusProdutosURI = localhost + "product/list.faces";
	
	private String novoProdutoURI = localhost + "product/new_product.faces";
	
	private String meusProdutosPausadosURI = localhost + "product/list_paused.faces";
	
	private String editarCadastroURI = localhost + "user_registration/edit.faces";
	
	private String minhasComprasURI = localhost + "pedidos/list.faces?tipo=0";
	
	private String minhasVendasURI = localhost + "pedidos/list.faces?tipo=1";
	
	private String minhaReputacaoURI = localhost + "reputacao/list.faces";
	
	
	public String getLocalhost(){
		return localhost;
	}
	
	public String minhaReputacao(){
		return minhaReputacaoURI;
	}
	
	public String minhasVendas(){
		return minhasVendasURI;
	}
	
	public String minhasCompras(){
		return minhasComprasURI;
	}
	
	public String meusProdutos(){
		return meusProdutosURI;
	}
	
	public String novoProduto(){
		return novoProdutoURI;
	}
	
	public String meusProdutosPausados(){
		return meusProdutosPausadosURI;
	}
	
	public String editarCadastro(){
		return editarCadastroURI;
	}
	
	
}
