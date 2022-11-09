package hu.webuni.student.andro;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Service
public class ClientService implements StompFrameHandler {
	
	private WebSocketStompClient webSocketStompClient;
	private final String serverUrl = "ws://localhost:8080/api/stomp";
	private final String topicNumber = "1201"; //csak teszthez fixen
	private StompSession stompSession;
	private StompSessionHandler stompSessionHandler;
	
	@Async
	public void connect() {
		webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompSessionHandler = new CustomStompSessionHandler();
		try {
			webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
	        StompHeaders connectHeaders = new StompHeaders();
	        
	        webSocketStompClient.setTaskScheduler(new DefaultManagedTaskScheduler());
	        ListenableFuture<StompSession> connectListener = webSocketStompClient.connect(serverUrl, new WebSocketHttpHeaders(),connectHeaders, stompSessionHandler);
	        stompSession = connectListener.get();
			
	        //stompSession=webSocketStompClient.connect(serverUrl, stompSessionHandler).get();
	        
	        /*synchronized (this.stompSession){
	            StompSession s = this.stompSession;
	            s.subscribe("/topic/course/"+topicNumber, this);
	        }*/
	        
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
