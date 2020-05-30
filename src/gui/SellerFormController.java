package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DBException;
import gui.listener.DataChangeListener;
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
import model.entidades.Seller;
import model.exception.ValidacaoException;
import model.servicos.SellerServico;

public class SellerFormController implements Initializable
{
	private Seller entidade;
	private SellerServico servico;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
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
	public void setSeller(Seller entidade)
	{
		this.entidade = entidade;
	}

	public void setServico(SellerServico servico)
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
			notifyDataChangeListeners();
			Utils.stageAtual(evento).close();
		}
		catch( ValidacaoException e)
		{
			setMsgErro(e.getErros());
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

	private Seller getDadosForm()
	{
		/*
		ValidacaoException exception = new ValidacaoException("Validacao erro");

		if (txtName.getText() == null || txtName.getText().trim().equals(""))
		{
			exception.addError("nome", "Campo ta vazio");
		}

		if (exception.getErros().size() > 0)
		{
			throw exception;
		}

		return new Seller( Utils.converterInt( txtId.getText() ), txtName.getText() );
		*/

		return null;
	}

	public void subscribeDataChangeListener(DataChangeListener listener)
	{
		dataChangeListeners.add(listener);
	}


	private void notifyDataChangeListeners()
	{
		for(DataChangeListener item : this.dataChangeListeners)
		{
			item.onDataChange();
		}
	}

	private void setMsgErro(Map<String, String> erro)
	{
		Set<String> campos = erro.keySet();

		if (campos.contains("nome"))
		{
			lblErrorName.setText(erro.get("nome"));
		}
	}

	//Métodos da interface
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		iniciarNodes();
	}
}