package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerta;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable
{

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;

	//Métodos implementados
	@FXML
	public void onMenuItemSellerAction()
	{
		System.out.println("OnMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartmentAction()
	{
		carregarView("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction()
	{
		carregarView("/gui/About.fxml");
	}

	//synchronized garante que o método não seja interrompido
	private synchronized void carregarView(String caminhoAbsoluto)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader( getClass().getResource(caminhoAbsoluto) );
			VBox novoVBox = loader.load();

			Scene cena = Main.getCena();

			VBox mainVBox = (VBox) ((ScrollPane) cena.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);

			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(novoVBox.getChildren());
		}
		catch (IOException e)
		{
			Alerta.mostrarAlerta("IOException", "Erro carregando a página", e.getMessage(), AlertType.ERROR);
		}
	}

	//métodos interface
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		
	}
}