package br.com.caelum.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.caelum.model.CarrinhoCompras;
import br.com.caelum.model.DadosPagamento;
import br.com.caelum.model.Usuarios;

@RequestMapping("/pagamento")
@Controller
public class PagamentoController {

	@Autowired
	CarrinhoCompras carrinho;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MailSender sender;

	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuarios usuarios, RedirectAttributes model) {
		return () -> {

			try {

				String uri = "http://book-payment.herokuapp.com/payment";
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()),
						String.class);
				System.out.println(response);

				enviaEmailCompraProduto(usuarios);

				model.addFlashAttribute("sucesso", response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("sucesso", "Valor maior que o permitido");
			}

			return new ModelAndView("redirect:/produtos");
		};
	}

	private void enviaEmailCompraProduto(Usuarios usuarios) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		// email.setTo(usuarios.getEmail());
		email.setTo("marquinhosf3@hotmail.com");
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("compras@casadocodigo.com.br");

		sender.send(email);
	}
}