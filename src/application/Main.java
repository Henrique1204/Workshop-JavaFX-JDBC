package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			//Define o arquivo que será a tela
			FXMLLoader loader = new FXMLLoader( getClass().getResource("/gui/MainView.fxml") );
			ScrollPane scrollPane = loader.load();

			//Ajusta o tamanho do ScrollPane na janela do programa
			scrollPane.setFitToWidth(true);
			scrollPane.setFitToHeight(true);

			//Configura a tela
			Scene cena = new Scene(scrollPane);
			primaryStage.setScene(cena);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}