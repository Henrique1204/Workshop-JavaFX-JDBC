package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Department;

public class DepartmentListController implements Initializable
{

	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumnId; //Primeiro tipo do TableColumn<> é o tipo da entidade e o segundo o tipo do valor
	@FXML
	private TableColumn<Department, String> tableColumnName;
	@FXML
	private Button btnNovo;

	//Métodos implementados
	@FXML
	public void onBtnNovoAction()
	{
		System.out.println("OnBtnNovoAction");
	}

	private void iniciarNode()
	{
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getCena().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	//Métodos da interface
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		iniciarNode();
	}
}