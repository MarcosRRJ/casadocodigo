package br.com.caelum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.dao.ProdutoDao;
import br.com.caelum.model.Produto;

@Controller
public class HomeController {

	@Autowired
	private ProdutoDao produtoDao;

	@RequestMapping("/")
	@Cacheable(value="produtosHome")
	public ModelAndView home() {
		List<Produto> produtos = produtoDao.getListar();
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
}
