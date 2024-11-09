package head;

import components.ThePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable, PropertyChangeListener {
	
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	private Point point;
	private boolean isReady = false;
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ThePublisher publisher = new ThePublisher(port);
			publisher.connect();
			isReady = true;
			while (isReady) {
				try {
					Thread.sleep(1000 / 30);
					if (point == null)  continue;
					publisher.publish(point);
				} catch (Exception e) {
					logger.error("Error in Server: {}", e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error("Unexpected error in Server: {}", e.getMessage(), e);
		} finally {
			isReady = false;
		}
	}
	
	public void stop() {
		isReady = false;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("point".equals(evt.getPropertyName())) {
			point = (Point) evt.getNewValue();
		}
	}
	
}