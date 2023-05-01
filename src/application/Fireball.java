package application;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * 
 * Creates a fireball asset that will deal damage to a player if it overlaps their character.
 * 
 * @author McMahonJake
 * @version 1.0
 */

public class Fireball {
/**
 * fireballImage retrieves the image for the fireball
 * fireballView retrieves the fireballImage to be viewed.
 * 
 * xSpeed amount the fireball will move on the x axis
 * ySpeed amount the fireball will move on the y axis
 * 
 * 
 */
	
	Image fireballImage;
	ImageView fireballView;
	double xSpeed;
	double ySpeed;
	
	/**
	 * constructor for fireball
	 * 
	 * @param game passes the pane game to the constructor
	 */

	public Fireball(Pane game) {
		//fireballImage/fireballView allows the image to be viewed in game
		fireballImage = new Image("file:Resources/fireball.gif", 25, 25, false, false);
		fireballView = new ImageView(fireballImage);

		game.getChildren().add(fireballView);
	}
/**
 * 
 * @param player1 you, the player
 * @param player2 your opponent
 * @param game the game pane
 * @param x x value of where the mouse clicked
 * @param y y value of where the mouse clicked
 * @param n networking data being passed
 */
	public void CastFireball(Player player1, Player player2, Pane game, double x, double y, Networking n) {

		
		n.sendFireball(x, y);
		
		//xVal is the distance on the x axis between the player and the mouse click
		int Xval = (int) x - (int) player1.playerView.getX();
		//yVal is the distance on the y axis between the player and the mouse click
		int Yval = (int) y - (int) player1.playerView.getY();
		//speed is the amount of pixels the fireball will move
		int speed = PythagoreanTheorem(Xval, Yval);
		xSpeed = Xval / speed;
		ySpeed = Yval / speed;

		//sets initial point the fireball will spawn at
		fireballView.setX(player1.playerView.getX() + 11 + (40 * (Xval / Math.abs(Xval))));
		fireballView.setY(player1.playerView.getY() + 15);
		
		//timer will play the fireball translation
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				
				//Translation in effect
				fireballView.setX(fireballView.getX() + xSpeed);
				fireballView.setY(fireballView.getY() + ySpeed);

				//if-else chain where the fireball will bounce off edges of the screen
				if (fireballView.getX() < 0 && xSpeed < 0) {
					xSpeed *= -1;
				} else if (fireballView.getX() > 475 && xSpeed > 0) {
					xSpeed *= -1;
				} else if (fireballView.getY() < 0 && ySpeed < 0) {
					ySpeed *= -1;
				} else if (fireballView.getY() > 475 && ySpeed > 0) {
					ySpeed *= -1;
				} else {
					//another if-else chain to determine if fireball collides with a player
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
/**
 * finds the "speed" using the pythagorean theorem. 
 * Finds the third side of the triangle formed between two points, and divides it by 5.
 * 
 * @param x x value between mouse click and player
 * @param y y value between mouse click and player
 * @return an int representing the speed of the fireball
 */
	public int PythagoreanTheorem(int x, int y) {

		return ((int) Math.sqrt((x * x) + (y * y)) / 5);
	}
	/**
	 * subtracts 1 from player's health if they are hit
	 * 
	 * @param p player being hit by fireball
	 */

	public void isHit(Player p) {
		p.health -= 1;
		System.out.println(p.health);
	}

}
