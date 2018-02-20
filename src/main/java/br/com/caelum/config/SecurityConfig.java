package br.com.caelum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.caelum.dao.UsuarioDAO;

@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	    .antMatchers("/produtos/form").hasRole("ADMIN")
	    .antMatchers("/carrinho/**").permitAll()
	    .antMatchers("/pagamento/**").permitAll()
	    .antMatchers(HttpMethod.GET, "/produtos").permitAll()
	    .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
	    .antMatchers("/produtos/**").permitAll()
	    .antMatchers("/resources/**").permitAll()
	    .antMatchers("/").permitAll()
	    .anyRequest().authenticated()
	    .and().formLogin().loginPage("/login").permitAll()
	    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));//aceitar fazer o logout via get
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao).passwordEncoder(new BCryptPasswordEncoder());
	}
}
