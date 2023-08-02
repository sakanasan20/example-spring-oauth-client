package tw.niq.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import tw.niq.example.model.AlbumResponseModel;

//@Controller
public class AlbumController {
	
	@Autowired
	OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
	
//	@Autowired
//	RestTemplate restTemplate;
	
	@Autowired
	WebClient webClient;

	@GetMapping("/albums")
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//		OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//		
//		OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
//				oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), 
//				oAuth2AuthenticationToken.getName());
//		
//		String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
//		
//		System.out.println("jwtAccessToken: " + jwtAccessToken);
//		
//		System.out.println(oidcUser);
//		
//		OidcIdToken oidcIdToken = oidcUser.getIdToken();
//		
//		String oidcIdTokenString = oidcIdToken.getTokenValue();
//		
//		System.out.println("oidcIdTokenString: " + oidcIdTokenString);
		
		String url = "http://localhost:8082/albums";

//		HttpHeaders httpHeaders = new HttpHeaders();
//		
//		httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
//		
//		HttpEntity<List<AlbumResponseModel>> httpEntity = new HttpEntity<>(httpHeaders);
//		
//		ResponseEntity<List<AlbumResponseModel>> responseEntity = 
//				restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<AlbumResponseModel>>() {});
//		
//		List<AlbumResponseModel> albums = responseEntity.getBody();
		
		List<AlbumResponseModel> albums = webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<AlbumResponseModel>>() {})
				.block();

		model.addAttribute("albums", albums);
		
		return "albums";
	}
	
}
