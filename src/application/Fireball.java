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

	public Fireball(Pane game) {
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

		fireballView.setX(player1.playerView.getX() + 11 + (40 * (Xval / Math.abs(Xval))));
		fireballView.setY(player1.playerView.getY() + 15);

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
						isHit(player1);
						game.getChildren().remove(fireballView);
						this.stop();

					} else if (fireballBounds.intersects(player2Bounds)) {
						isHit(player2);
						game.getChildren().remove(fireballView);
						this.stop();

					}

				}

			}

		};
		timer.start();

	}

	public int PathagoreanTheorem(int x, int y) {

		return ((int) Math.sqrt((x * x) + (y * y)) / 5);
	}

	public void isHit(Player p) {
		p.health -= 1;
		System.out.println(p.health);
	}

}
