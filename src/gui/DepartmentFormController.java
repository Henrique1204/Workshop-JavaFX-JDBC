package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Department;

public class DepartmentFormController implements Initializable
{
	private Department entidade;
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

	//Setter
	public void setDepartment(Department entidade)
	{
		this.entidade = entidade;
	}

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

	public void atualizarDadosForm()
	{
		if (entidade == null)
		{
			throw new IllegalStateException("Entidade esta vazia");
		}

		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getName());
	}

	//Métodos da interface
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		iniciarNodes();
	}
}