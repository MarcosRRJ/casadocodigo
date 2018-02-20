package br.com.caelum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.dao.ProdutoDao;
import br.com.caelum.dao.UsuarioDAO;
import br.com.caelum.model.Produto;
import br.com.caelum.model.Role;
import br.com.caelum.model.Usuarios;

@Controller
public class HomeController {
	
	@Autowired
	private UsuarioDAO usuarioDao;

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
	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-oajksfbvad6584i57j54f9684nvi658efnoewfmnvowefnoeijn")
	public String urlMagicaMaluca() {
	    Usuarios usuario = new Usuarios(); 
	    usuario.setNome("Admin");
	    usuario.setEmail("admin@casadocodigo.com.br");
	    usuario.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
	    usuario.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));

	    usuarioDao.gravar(usuario);

	    return "Url Mágica executada";
	}
}
