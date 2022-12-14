package hu.webuni.student.andro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${stompserver.url}")
	private String URL;
	@Value("${stompserver.endpoint}")
	private String endpoint;
	
	@GetMapping("/connectandsubscribe")
	public ResponseEntity<String> connectAndSubscribe(@RequestParam String topic){
		if (token != null) {
			clientService.connect(topic,token);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	 
	
	@PostMapping("/login")
	public ResponseEntity<String> getToken(@RequestBody ClientUser user){
		setToken(user);
		return ResponseEntity.ok(token);
	}

	private void setToken(ClientUser user) {
		WebClient webClient = WebClient.create(URL);
		this.token=webClient
				.post()
				.uri(this.endpoint)
				.body(Mono.just(user),ClientUser.class)
				.retrieve()
				.bodyToMono(String.class).block();
	}
	
	@PostMapping
	public ResponseEntity<String> loginConnectAndSubscribe(@RequestBody ClientUser user, @RequestParam String topic){
		setToken(user);
		if (token != null) {
			clientService.connect(topic,token);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
