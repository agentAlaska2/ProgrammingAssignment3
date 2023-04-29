package application;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Fireball {

	Image fireballImage;
	ImageView fireballView;
	double xSpeed;
	double ySpeed;
	Bounds fireballBounds;

	public Fireball(Player player, Pane game) {
		fireballImage = new Image("file:Resources/fireball.gif", 25, 25, false, false);
		fireballView = new ImageView(fireballImage);
		fireballView.setX(player.getPlayerPos().getX());
		fireballView.setY(player.getPlayerPos().getY() + 16);
		fireballBounds = fireballView.getBoundsInParent();
		
		game.getChildren().add(fireballView);
	}

	public void CastFireball(Player player, Pane game, double x, double y) {

		int Xval = (int) x - (int) player.getPlayerPos().getX();
		int Yval = (int) y - (int) player.getPlayerPos().getY();

		int speed = PathagoreanTheorem(Xval, Yval);
		xSpeed = Xval / speed;
		ySpeed = Yval / speed;

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				fireballView.setX(fireballView.getX() + xSpeed);
				fireballView.setY(fireballView.getY() + ySpeed);

				if (fireballView.getX() < 0 && xSpeed < 0) {
					xSpeed *= -1;

				}
				if (fireballView.getX() > 475 && xSpeed > 0) {
					xSpeed *= -1;

				}
				if (fireballView.getY() < 0 && ySpeed < 0) {
					ySpeed *= -1;

				}
				if (fireballView.getY() > 475 && ySpeed > 0) {
					ySpeed *= -1;

				}
				if (fireballBounds.intersects(player.getBounds())){
					game.getChildren().remove(fireballView);
				}
				
				
			}
			
		};
		timer.start();

		
	}

	public int PathagoreanTheorem(int x, int y) {

		return ((int) Math.sqrt((x * x) + (y * y)) / 5);
	}

}
