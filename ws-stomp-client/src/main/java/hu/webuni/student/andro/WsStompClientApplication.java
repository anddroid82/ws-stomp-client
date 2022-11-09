package hu.webuni.student.andro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsStompClientApplication implements CommandLineRunner {

	@Autowired
	private ClientService clientService;
	
	public static void main(String[] args) {
		SpringApplication.run(WsStompClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		clientService.connect();
	}

}
