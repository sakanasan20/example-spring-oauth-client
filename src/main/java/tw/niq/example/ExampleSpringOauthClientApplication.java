package tw.niq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ExampleSpringOauthClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleSpringOauthClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, 
			OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository) {
		
		ServletOAuth2AuthorizedClientExchangeFilterFunction servletOAuth2AuthorizedClientExchangeFilterFunction = 
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, 
						oAuth2AuthorizedClientRepository);
		
		servletOAuth2AuthorizedClientExchangeFilterFunction.setDefaultOAuth2AuthorizedClient(true);
		
		return WebClient.builder()
				.apply(servletOAuth2AuthorizedClientExchangeFilterFunction.oauth2Configuration())
				.build();
	}
	
}
