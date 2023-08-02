package tw.niq.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import tw.niq.example.model.OrderModel;

@Controller
public class OrderController {
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/orders")
	public String getOrders(Model model, 
			@RegisteredOAuth2AuthorizedClient("spring-client") OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		
		String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();

		String url = "http://localhost:8091/orders";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
		
		ResponseEntity<List<OrderModel>> responseEntity = 
				restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<OrderModel>>() {});
		
		List<OrderModel> orders = responseEntity.getBody();
		
		model.addAttribute("orders", orders);
		
		return "orders";
	}
	
}
