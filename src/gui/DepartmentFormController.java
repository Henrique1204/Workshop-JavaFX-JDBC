package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DBException;
import gui.util.Alerta;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Department;
import model.servicos.DepartmentServico;

public class DepartmentFormController implements Initializable
{
	private Department entidade;
	private DepartmentServico servico;
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

	//Setters
	public void setDepartment(Department entidade)
	{
		this.entidade = entidade;
	}

	public void setServico(DepartmentServico servico)
	{
		this.servico = servico;
	}

	//Métodos implementados
	@FXML
	public void onBtnSalvarAction(ActionEvent evento)
	{
		if (entidade == null || servico == null)
		{
			String msg = (entidade == null && servico != null) ? "Entidade estava vazia" :
						 (entidade != null && servico == null) ? "Servico estava vazio" : "Servico e entidade estavam vazios";

			throw new IllegalStateException(msg);
		}

		try
		{
			entidade = getDadosForm();
			servico.salvarOuAtualizar(entidade);
			Utils.stageAtual(evento).close();
		}
		catch (DBException e)
		{
			Alerta.mostrarAlerta("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	public void onBtnCancelarAction(ActionEvent evento)
	{
		Utils.stageAtual(evento).close();
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

	private Department getDadosForm()
	{
		return new Department( Utils.converterInt( txtId.getText() ), txtName.getText() );
	}

	//Métodos da interface
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		iniciarNodes();
	}
}