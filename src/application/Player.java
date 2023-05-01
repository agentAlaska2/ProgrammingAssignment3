package application;

import java.io.IOException;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Creates a player by importing player 1 image or player 2 image,
 * players can control their character with the W A S D keys
 * 
 * @author McMahonJake
 * @version 1.0
 */

public class Player {
	
/**
 * 
 * health is the amount of times a player can take a hit from a fireball
 * playerNum is a string that is passed that tags the Player object as player1 or player2
 * playerImg/imagePath/playerView allows the players to show up on the screen
 * playerBounds essentially creates the equivalent of a collision box around the player sprite
 * speed is the amount of pixels the player can move
 * 
 */
	int health = 15;
	String playerNum;
	Image playerImg;
	String imagePath;
	ImageView playerView;
	Bounds playerBounds;
	int speed = 25;
	
	/**
	 * constructor for the player, positions the player sprite based on which one it grabs
	 * 
	 * @param playerNum is a string that is passed that tags the Player object as player1 or player2
	 * @param game is the pane used in Main
	 */
	public Player(String playerNum, Pane game) {
		this.playerNum = playerNum;
		imagePath = "file:Resources/" + playerNum + ".png";
		playerImg = new Image(imagePath, 50, 50, false, false);
		playerView = new ImageView(playerImg);

		if (playerNum.equals("player1")) {
			playerView.setX(50);
			playerView.setY(150);
			game.getChildren().add(playerView);
		} else {
			playerView.setX(300);
			playerView.setY(150);
			game.getChildren().add(playerView);
		}
		playerBounds = playerView.getBoundsInParent();
	}

	/**
	 * Moves the player based on input from the keyboard, W A S or D
	 * will also send the movement to the other computer to move the player 2 sprite
	 * as well as checks the health of the players
	 * 
	 * @param game pane the game uses
	 * @param n the networking package sent/recieved
	 * @param p the opposing player
	 * @param player1 the player this class is
	 * @throws IOException
	 */
	public void Movement(Pane game, Networking n, Player p, Player player1) throws IOException {

		
		playerView.setFocusTraversable(true);
		playerView.requestFocus();
		
		//event determines which key is pressed, and moves the character in that direction.
		game.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.W && playerView.getY() - speed >= 0) {
				playerView.setY(playerView.getY() - speed);
				event.consume();
				try {
					n.encodeData(Commands.UP.ordinal());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkHealth(player1, p, game);
				moveOtherPlayer(p, player1, n, game);
			} else if (event.getCode() == KeyCode.S && playerView.getY() + speed <= 500 - 32) {
				playerView.setY(playerView.getY() + speed);
				event.consume();
				try {
					n.encodeData(Commands.DOWN.ordinal());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkHealth(player1, p, game);
				moveOtherPlayer(p, player1, n, game);
			} else if (event.getCode() == KeyCode.A && playerView.getX() - speed >= 0) {
				playerView.setX(playerView.getX() - speed);
				event.consume();
				try {
					n.encodeData(Commands.LEFT.ordinal());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkHealth(player1, p, game);
				moveOtherPlayer(p, player1, n, game);
			} else if (event.getCode() == KeyCode.D && playerView.getX() + speed <= 250 - 32) {
				playerView.setX(playerView.getX() + speed);
				event.consume();
				try {
					n.encodeData(Commands.RIGHT.ordinal());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkHealth(player1, p, game);
				moveOtherPlayer(p, player1, n, game);

			}

		});

	}
	
	/**
	 * 
	 * @return the player's bounds
	 */
	public Bounds getBounds() {
		return playerBounds;
	}
	
	/**
	 * checks the player's health to determine if a player has no health left
	 * 
	 * @param player1 red player
	 * @param player2 blue player
	 * @param game game pane
	 */
	public void checkHealth(Player player1, Player player2, Pane game) {
		if (player1.getHealth() <= 0) {
			Text t = new Text(250, 250, "Player 2 wins!");
			player1.playerView.setVisible(false);
			game.getChildren().add(t);
		}
		if (player2.getHealth() <= 0) {
			Text t = new Text(250, 250, "Player 1 wins!");
			player2.playerView.setVisible(false);
			game.getChildren().add(t);
		}
	}
	
	/**
	 * subtracts 1 from the player's health
	 */
	public void isHit() {
		health -= 1;
	}

	/**
	 * 
	 * @return the player's health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * moves the other player based on movement received from other player, or casts fireball
	 * 
	 * @param p player 2, the other player
	 * @param player1 the local player
	 * @param n the networking bit sent/received
	 * @param game game pane
	 */
	public void moveOtherPlayer(Player p, Player player1, Networking n, Pane game) {
		try {
			int key = n.decodeData();
			if (key == 0 && p.playerView.getY() - speed >= 0) {
				p.playerView.setY(p.playerView.getY() - speed);
			} else if (key == 1 && p.playerView.getY() + speed <= 500 - 32) {
				p.playerView.setY(p.playerView.getY() + speed);
			} else if (key == 3 && p.playerView.getX() - speed >= 250) {
				p.playerView.setX(p.playerView.getX() - speed);
			} else if (key == 2 && p.playerView.getX() + speed <= 500 - 32) {
				p.playerView.setX(p.playerView.getX() + speed);
			} if (key == 4) {
				double[] pos = n.recieveFireball();
				Fireball fireball = new Fireball(game);
				fireball.CastFireball(p, player1, game, pos[0], pos[1], n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
