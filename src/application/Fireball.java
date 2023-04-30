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
	Boolean isHit1 = false;
	Boolean isHit2 = false;
	

	public Fireball(Player player, Pane game) {
		fireballImage = new Image("file:Resources/fireball.gif", 25, 25, false, false);
		fireballView = new ImageView(fireballImage);

		

		game.getChildren().add(fireballView);
	}

	public void CastFireball(Player player1, Player player2, Pane game, double x, double y, Networking n) {
		
		n.sendFireball(x, y);
		int Xval = (int) x - (int) player1.playerView.getX();
		int Yval = (int) y - (int) player1.playerView.getY();

		int speed = PathagoreanTheorem(Xval, Yval);
		xSpeed = Xval / speed;
		ySpeed = Yval / speed;

		fireballView.setX(player1.playerView.getX() + -32);
		fireballView.setY(player1.playerView.getY() + 16);

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				fireballView.setX(fireballView.getX() + xSpeed);
				fireballView.setY(fireballView.getY() + ySpeed);

				if (fireballView.getX() < 0 && xSpeed < 0) {
					xSpeed *= -1;

				} else if (fireballView.getX() > 475 && xSpeed > 0) {
					xSpeed *= -1;

				} else if (fireballView.getY() < 0 && ySpeed < 0) {
					ySpeed *= -1;

				}

				else if (fireballView.getY() > 475 && ySpeed > 0) {
					ySpeed *= -1;

				} else {
					Bounds fireballBounds = fireballView.getBoundsInParent();
					Bounds playerBounds = player1.playerView.getBoundsInParent();
					Bounds player2Bounds = player2.playerView.getBoundsInParent();
					if (fireballBounds.intersects(playerBounds)) {
						isHit1 = true;
						player1.isHit();
						game.getChildren().remove(fireballView);
						
					} else if (fireballBounds.intersects(player2Bounds)) {
						player2.isHit();
						isHit2 = true;
						game.getChildren().remove(fireballView);
						
						
					}
					
				}

			}

		};
		timer.start();
		
	}

	public int PathagoreanTheorem(int x, int y) {

		return ((int) Math.sqrt((x * x) + (y * y)) / 5);
	}

}
