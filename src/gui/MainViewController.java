package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.servicos.DepartmentServico;
import model.servicos.SellerServico;

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
		carregarView("/gui/SellerList.fxml", (SellerListController controller) ->
		{
			controller.setSellerServico(new SellerServico());
			controller.atualizarTableView();
		});
	}

	@FXML
	public void onMenuItemDepartmentAction()
	{
		carregarView("/gui/DepartmentList.fxml", (DepartmentListController controller) ->
		{
			controller.setDepartmentServico(new DepartmentServico());
			controller.atualizarTableView();
		});
	}

	@FXML
	public void onMenuItemAboutAction()
	{
		carregarView("/gui/About.fxml", x -> {});
	}

	//synchronized garante que o método não seja interrompido
	private synchronized <T> void carregarView(String caminhoAbsoluto, Consumer<T> acionarInicialização)
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

			T controller = loader.getController();
			acionarInicialização.accept(controller);
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