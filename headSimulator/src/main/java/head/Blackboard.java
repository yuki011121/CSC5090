package head;

import java.awt.*;
import java.beans.PropertyChangeSupport;

public class Blackboard extends PropertyChangeSupport {
	
	private static Blackboard instance;
	
	private Point point;
	private String statusLabel;
	
	private Blackboard() {
		super(new Object());
		point = new Point(0, 0);
	}
	
	public static Blackboard getInstance() {
		if (instance == null) {
			instance = new Blackboard();
		}
		return instance;
	}
	
	public void setPoint(Point p) {
		point = p;
		firePropertyChange("point", null, p);
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void updateStatusLabel(String disconnected) {
		this.statusLabel = disconnected;
		firePropertyChange("statusLabel", null, disconnected);
	}
}