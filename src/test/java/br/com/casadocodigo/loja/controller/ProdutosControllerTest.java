package br.com.casadocodigo.loja.controller;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.caelum.config.AppWebConfiguration;
import br.com.caelum.config.JpaConfigurator;
import br.com.caelum.config.SecurityConfig;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfigurator.class, AppWebConfiguration.class, DataSourceConfigurationTest.class,
		SecurityConfig.class })
@ActiveProfiles("test")
public class ProdutosControllerTest {

	@Autowired
	private Filter springSecurityFilterChain;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(springSecurityFilterChain).build();
	}

	// @Test
	// public void deveRetornarParaHomeComOsLivros() throws Exception {
	// mockMvc.perform(MockMvcRequestBuilders.get("/"))
	// .andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
	// .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));
	// }

	 @Test
	 public void somenteAdminDeveAcessarProdutosForm() throws Exception{
	 mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
	 .with(SecurityMockMvcRequestPostProcessors
	 .user("admin@casadocodigo.com.br").password("123456")
	 .roles("ADMIN")))
	 .andExpect(MockMvcResultMatchers.status().is(200));
	 }

	@Test
	public void somenteUserDeveAcessarProdutosForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form").with(SecurityMockMvcRequestPostProcessors
				.user("user@casadocodigo.com.br").password("123456")
				.roles("USUARIO")))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

}