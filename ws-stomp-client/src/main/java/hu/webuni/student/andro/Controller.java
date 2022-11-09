package hu.webuni.student.andro;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/stomp")
@AllArgsConstructor
public class Controller {
	
	private ClientService clientService;
	
	@Async
	@GetMapping
	public ResponseEntity<String> connect(){
		clientService.connect();
		return ResponseEntity.ok("hello");
	}
}