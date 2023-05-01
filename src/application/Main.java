/**
 * Program is a game where two wizards fight. play can move their wizard and shoot fireballs. Game ends when the wizard's health decreases to zero.
 */

package application;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	Pane game = new Pane();
	Player player1 = new Player("player1", game); // local player
	Player player2 = new Player("player2", game); // network player

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			TextInputDialog getAddr = new TextInputDialog();
			getAddr.setHeaderText(null);
			getAddr.setTitle(null);
			getAddr.setContentText(
					"Enter the IP address of the player's computer you wish to compete with using spaces: ");
			Optional<String> addr = getAddr.showAndWait(); // Ask for players IP address, but using spaces to seperate, ex. 192 168 1 1
			String name = addr.get();
			Networking net = new Networking(name); // start the connection
			player1.Movement(game, net, player2, player1); // players move around

			game.setOnMousePressed(event -> { // if left mouse button is clicked, cast a fireball!
				Fireball fireball = new Fireball(game);
				fireball.CastFireball(player1, player2, game, event.getX(), event.getY(), net); // cast the fireball where the mouse pointer is.
				try {
					net.encodeData(Commands.FIREBALL.ordinal()); // send the command over to the other player that you have cast a fireball
				} catch (IOException e) {
					e.printStackTrace();
				}
				net.sendFireball(event.getX(), event.getY()); // send the data of the fireball.
			});

			game.setStyle("-fx-background-color: lightgray;");
			primaryStage.setResizable(false);

			Scene scene = new Scene(game, 500, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
