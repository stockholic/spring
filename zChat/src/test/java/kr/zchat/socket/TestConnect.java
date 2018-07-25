package kr.zchat.socket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TestConnect {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void connectTest() throws URISyntaxException {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String destUri = "http://localhost:8080/zzz";

        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        try {

        	client.start();
        	
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket,echoUri,request);
            System.out.printf("Connecting to : %s%n",echoUri);

            // wait for closed socket connection.
            socket.awaitClose(5,TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally{
            try{
                client.stop();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        logger.debug("-------------------------------------------------------------------------------> end");
    }
		

}
