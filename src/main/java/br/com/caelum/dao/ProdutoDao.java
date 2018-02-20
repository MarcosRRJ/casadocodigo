package br.com.caelum.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.caelum.model.Produto;
import br.com.caelum.model.TipoPreco;

@Repository
@Transactional
public class ProdutoDao {

	@PersistenceContext
	private EntityManager maneger;

	public void gravar(Produto produto) {
		maneger.persist(produto);
	}

	public List<Produto> getListar() {

		return maneger.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class).getResultList();
	}

	public Produto find(Integer id) {

		return maneger.createQuery("select distinct(p) from Produto p join fetch p.precos " + "where p.id = :id",
				Produto.class).setParameter("id", id).getSingleResult();

	}

	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco) {
		TypedQuery<BigDecimal> query = maneger.createQuery(
				"select sum(preco.valor) from Produto p "
				+ "join p.precos preco where preco.tipo = :tipoPreco",
				BigDecimal.class);
		query.setParameter("tipoPreco", tipoPreco);
		return query.getSingleResult();
	}

}
