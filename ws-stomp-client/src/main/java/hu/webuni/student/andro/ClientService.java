package hu.webuni.student.andro;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Service
public class ClientService implements StompFrameHandler {
	
	private WebSocketStompClient webSocketStompClient;
	private final String serverUrl = "ws://localhost:8080/api/stomp/";
	private final String topicNumber = "1156"; //csak teszthez fixen
	private StompSession stompSession;
	
	public void connect() {
		webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
		StompSessionHandler sessionHandler = new CustomStompSessionHandler();
		try {
			stompSession = webSocketStompClient.connect(serverUrl, sessionHandler).get();
			//stompSession.subscribe("/topic/course/" + topicNumber, this);
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		Message msg = (Message) payload;
		System.out.println("Received : " + msg.getMessage() + " from : " + msg.getUsername());
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return null;
	}

}
