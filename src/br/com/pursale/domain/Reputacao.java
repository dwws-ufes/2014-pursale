package br.com.pursale.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Reputacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Pedido pedido;
	
	@ManyToOne
	private Usuario comprador;
	
	@ManyToOne
	private Usuario vendedor;
	
	@NotNull
	private String comentario;
	
	@NotNull
	private int tipo; // 1 - Comprador, 2 = Vendedor
	
	@NotNull
	private int avaliacao; // 0 - Ruim, 1 - Neutra, 2 - Boa
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date data;
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public void setComprador(Usuario usuario) {
		this.comprador = usuario;
	}
	
	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario usuario) {
		this.vendedor = usuario;
	}
}
