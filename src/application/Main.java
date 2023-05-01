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
	Player player1 = new Player("player1", game);
	Player player2 = new Player("player2", game);

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			TextInputDialog getAddr = new TextInputDialog();
			getAddr.setHeaderText(null);
			getAddr.setTitle(null);
			getAddr.setContentText(
					"Enter the IP address of the player's computer you wish to compete with using spaces: ");
			Optional<String> addr = getAddr.showAndWait();
			String name = addr.get();
			Networking net = new Networking(name);
			player1.Movement(game, net, player2, player1);

			game.setOnMousePressed(event -> {
				Fireball fireball = new Fireball(game);
				fireball.CastFireball(player1, player2, game, event.getX(), event.getY(), net);
				try {
					net.encodeData(Commands.FIREBALL.ordinal());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				net.sendFireball(event.getX(), event.getY());
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
