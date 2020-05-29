package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable
{
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label lblErrorName;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnCancelar;

	//Métodos implementados
	@FXML
	public void onBtnSalvarAction()
	{
		System.out.println("Salvou");
	}

	@FXML
	public void onBtnCancelarAction()
	{
		System.out.println("Cancelou");
	}

	private void iniciarNodes()
	{
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtName, 30);
	}

	//Métodos da interface
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		iniciarNodes();
	}
}