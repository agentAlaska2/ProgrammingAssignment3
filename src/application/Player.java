package application;

import java.io.IOException;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Player {

	int health = 3;
	String playerNum;
	Image playerImg;
	String imagePath;
	ImageView playerView;
	Point2D playerPos;
	Bounds playerBounds;
	
	public Player(String playerNum, Pane game) {
		this.playerNum = playerNum;
		imagePath = "file:Resources/" + playerNum + ".png";
		playerImg = new Image(imagePath, 50, 50, false, false);
		playerView = new ImageView(playerImg);

		if (playerNum.equals("player1")) {
			playerView.setX(50);
			playerView.setY(150);
			playerPos = new Point2D(50,150);
			game.getChildren().add(playerView);
		} else {
			playerView.setX(300);
			playerView.setY(150);
			playerPos = new Point2D(300,150);
			game.getChildren().add(playerView);
		}
		playerBounds = playerView.getBoundsInParent();
	}

	public void Movement(Pane game, Networking n, Boolean b) throws IOException {
	
		int speed = 25;

		playerView.setFocusTraversable(true);
		playerView.requestFocus();

		if (/*playerNum.equals("player1")*/b) {
			
			game.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.W && playerView.getY() - speed >= 0) {
					playerView.setY(playerView.getY() - speed);
					playerPos.subtract(0,speed);
					event.consume();
					try {
						n.encodeData(Commands.UP.ordinal());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (event.getCode() == KeyCode.S && playerView.getY() + speed <= 500 - 32) {
					playerView.setY(playerView.getY() + speed);
					playerPos.add(0,speed);
					event.consume();
					try {
						n.encodeData(Commands.DOWN.ordinal());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (event.getCode() == KeyCode.A && playerView.getX() - speed >= 0) {
					playerView.setX(playerView.getX() - speed);
					playerPos.subtract(speed,0);
					event.consume();
					try {
						n.encodeData(Commands.LEFT.ordinal());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (event.getCode() == KeyCode.D && playerView.getX() + speed <= 250 - 32) {
					playerView.setX(playerView.getX() + speed);
					playerPos.add(speed,0);
					event.consume();
					try {
						n.encodeData(Commands.RIGHT.ordinal());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				

			});
		}
		
		if (/*playerNum.equals("player2")*/!b) {
			try {
			int key = n.decodeData();
			System.out.println("run");
			if (key == 0 && playerView.getY() - speed >= 0) {
				playerView.setY(playerView.getY() - speed);
			} else if (key == 1 && playerView.getY() + speed <= 500 - 32) {
				playerView.setY(playerView.getY() + speed);
			} else if (key == 3 && playerView.getX() - speed >= 250) {
				playerView.setX(playerView.getX() - speed);
			} else if (key == 2  && playerView.getX() + speed <= 500 - 32) {
				playerView.setX(playerView.getX() + speed);
			}
			} catch (Exception e){
				System.out.println("No data");
			}
		}

	}

	public Point2D getPlayerPos() {
		return playerPos;
	}
	
	public Bounds getBounds() {
		return playerBounds;
	}
	
	public void isHit() {
		health -= 1;
		System.out.println(health);
	}
	
	public int getHealth() {
		return health;
	}
}
