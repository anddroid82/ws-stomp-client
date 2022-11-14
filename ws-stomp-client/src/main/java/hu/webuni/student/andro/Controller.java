package hu.webuni.student.andro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stomp")
@NoArgsConstructor
public class Controller {
	@Autowired
	private ClientService clientService;
	private String token;
	private final String url="http://localhost:8080";
	
	@GetMapping
	public ResponseEntity<String> connect(@RequestParam String topic){
		if (token != null) {
			clientService.connect(topic,token);
			return ResponseEntity.ok("ok");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> getToken(@RequestBody ClientUser user){
		WebClient webClient = WebClient.create(url);
		token=webClient
				.post()
				.uri("/api/login")
				.body(Mono.just(user),ClientUser.class)
				.retrieve()
				.bodyToMono(String.class).block();
		//System.out.println(token);
		return ResponseEntity.ok(token);
	}
	
	
}
