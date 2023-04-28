package application;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Fireball {

	Image fireballImage;
	ImageView fireballView;

	public Fireball(Player player, Pane game) {
		fireballImage = new Image("file:Resources/fireball.gif", 65, 65, false, false);
		fireballView = new ImageView(fireballImage);
		fireballView.setX(20); //player.getPlayerPos().getX()
		fireballView.setY(20);//player.getPlayerPos().getY() + 16
		game.getChildren().add(fireballView);
		//CastFireball(game, 0, 0);
	}

	/*
	 * public void CastFireball(Pane game, double x, double y) {
	 * 
	 * TranslateTransition translateTransition = new
	 * TranslateTransition(Duration.seconds(2), fireballView);
	 * translateTransition.setFromX(fireballView.getX());
	 * translateTransition.setFromY(fireballView.getY());
	 * translateTransition.setToX(x); translateTransition.setToY(y);
	 * translateTransition.setCycleCount(1);
	 * 
	 * 
	 * }
	 */
	
	
}
