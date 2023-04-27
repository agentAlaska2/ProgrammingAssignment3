package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Image background = new Image("C:\\Users\\McMahonJake\\eclipse-workspace\\ProgrammingAssignment3\\src\\application\\ImageAssets\\TempFloor.png", 30, 30, false, false);	
			
			TilePane tiledBackground = new TilePane();
			
			
			for(int i = 0; i < 225; i++) {
				tiledBackground.getChildren().add(new ImageView(background));
			}
			tiledBackground.setPrefColumns(15);
			
			tiledBackground.setPrefRows(15);
			tiledBackground.setAlignment(Pos.CENTER);
			
			
			
			
			Scene scene = new Scene(tiledBackground,450,450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
