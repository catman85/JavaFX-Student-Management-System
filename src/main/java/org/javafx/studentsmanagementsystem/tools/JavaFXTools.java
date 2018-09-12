/**
 * 
 */
package main.java.org.javafx.studentsmanagementsystem.tools;

import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class has some functions that are not there by default in JavaFX 8
 * 
 * @author GOXR3PLUS
 *
 */
public final class JavaFXTools {
	
	private JavaFXTools() {
	}
	
	/**
	 * Show a notification.
	 *
	 * @param title
	 *            The notification title
	 * @param text
	 *            The notification text
	 * @param duration
	 *            The duration that notification will be visible
	 * @param notificationType
	 *            The notification type
	 */
	public static void showNotification(String title , String text , Duration duration , NotificationType notificationType) {
		Platform.runLater(() -> showNotification(title, text, duration, notificationType, null));
	}
	
	/**
	 * Show a notification.
	 *
	 * @param title
	 *            The notification title
	 * @param text
	 *            The notification text
	 * @param duration
	 *            The duration that notification will be visible
	 * @param notificationType
	 *            The notification type
	 */
	public static void showNotification(String title , String text , Duration duration , NotificationType notificationType , Node graphic) {
		
		try {
			
			//Check if it is JavaFX Application Thread
			if (!Platform.isFxApplicationThread()) {
				Platform.runLater(() -> showNotification(title, text, duration, notificationType, graphic));
				return;
			}
			
			//Show the notification
			showNotification2(title, text, duration, notificationType, graphic);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Just a helper method for showNotification methods
	 */
	private static void showNotification2(String title , String text , Duration duration , NotificationType notificationType , Node graphic) {
		Notifications notification1;
		
		//Set graphic
		if (graphic == null)
			notification1 = Notifications.create().title(title).text(text).hideAfter(duration).darkStyle().position(Pos.BOTTOM_RIGHT);
		else
			notification1 = Notifications.create().title(title).text(text).hideAfter(duration).darkStyle().position(Pos.BOTTOM_RIGHT).graphic(graphic);
		
		//Show the notification
		switch (notificationType) {
			case CONFIRM:
				notification1.graphic(JavaFXTools.getFontIcon("fas-question-circle", Color.web("#ad14e2"), 32)).show();
				break;
			case ERROR:
				notification1.graphic(JavaFXTools.getFontIcon("fas-times", Color.web("#f83e3e"), 32)).show();
				break;
			case INFORMATION:
				notification1.graphic(JavaFXTools.getFontIcon("fas-info-circle", Color.web("#1496e5"), 32)).show();
				break;
			case SIMPLE:
				notification1.show();
				break;
			case WARNING:
				notification1.graphic(JavaFXTools.getFontIcon("fa-warning", Color.web("#d74418"), 32)).show();
				break;
			case SUCCESS:
				notification1.graphic(JavaFXTools.getFontIcon("fas-check", Color.web("#64ff41"), 32)).show();
				break;
			default:
				break;
		}
	}
	
	/**
	 * Set Graphic Font Icon
	 * 
	 * @param icon
	 * @param iconLiteral
	 * @param color
	 */
	public static void setFontIcon(Labeled node , FontIcon icon , String iconLiteral , Color color) {
		icon.setIconLiteral(iconLiteral);
		icon.setIconColor(color);
		if (node != null)
			node.setGraphic(icon);
	}
	
	/**
	 * Get the requested Font Icon
	 * 
	 * @param iconLiteral
	 * @param color
	 * @param size
	 * @return
	 */
	public static FontIcon getFontIcon(String iconLiteral , Color color , int size) {
		
		//Create the Icon
		FontIcon icon = new FontIcon(iconLiteral);
		
		//Set Icon Color
		icon.setIconColor(color);
		
		//Set Size
		if (size != 0)
			icon.setIconSize(size);
		
		return icon;
	}
	
}
