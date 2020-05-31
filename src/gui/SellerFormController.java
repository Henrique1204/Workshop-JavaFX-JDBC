package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DBException;
import gui.listener.DataChangeListener;
import gui.util.Alerta;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entidades.Department;
import model.entidades.Seller;
import model.exception.ValidacaoException;
import model.servicos.DepartmentServico;
import model.servicos.SellerServico;

public class SellerFormController implements Initializable
{
	private Seller entidade;
	private SellerServico servico;
	private DepartmentServico depServico;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private TextField txtBaseSalary;
	@FXML
	private ComboBox<Department> comboBoxDepartment;
	@FXML
	private Label lblErrorName;
	@FXML
	private Label lblErrorEmail;
	@FXML
	private Label lblErrorBirthDate;
	@FXML
	private Label lblErrorBaseSalary;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnCancelar;
	private ObservableList<Department> obsList;

	//Setters
	public void setSeller(Seller entidade)
	{
		this.entidade = entidade;
	}

	public void setServicos(SellerServico servicoSeller, DepartmentServico servicoDepartment)
	{
		this.servico = servicoSeller;
		this.depServico = servicoDepartment;
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
		Restricoes.setTextFieldMaxLength(txtName, 70);
		Restricoes.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		Restricoes.setTextFieldDouble(txtBaseSalary);

		iniciarComboBoxDepartment();
	}

	public void atualizarDadosForm()
	{
		if (entidade == null)
		{
			throw new IllegalStateException("Entidade esta vazia");
		}

		txtId.setText(String.valueOf(entidade.getId()));
		txtName.setText(entidade.getName());
		txtEmail.setText(entidade.getEmail());

		if (entidade.getBrithDate() != null)
		{
			dpBirthDate.setValue(LocalDateTime.ofInstant(entidade.getBrithDate().toInstant(), ZoneId.systemDefault()).toLocalDate());	
		}

		Locale.setDefault(Locale.US);
		txtBaseSalary.setText( String.format( "%.2f", entidade.getBaseSalary() ) );

		if (entidade.getDepartment() == null)
		{
			comboBoxDepartment.getSelectionModel().selectFirst();
		}
		else
		{
			comboBoxDepartment.setValue(entidade.getDepartment());	
		}
	}

	private Seller getDadosForm()
	{
		ValidacaoException exception = new ValidacaoException("Validacao erro");
		Instant instante;

		if (txtName.getText() == null || txtName.getText().trim().equals(""))
		{
			exception.addError("nome", "Campo ta vazio");
		}

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals(""))
		{
			exception.addError("email", "Campo ta vazio");
		}

		if (dpBirthDate.getValue() == null)
		{
			exception.addError("birthDate", "Campo ta vazio");
			instante = null;
		}
		else
		{
			instante = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));	
		}

		if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals(""))
		{
			exception.addError("baseSalary", "Campo ta vazio");
		}

		if (exception.getErros().size() > 0)
		{
			throw exception;
		}

		return new Seller(
					Utils.converterInt(txtId.getText()),
					txtName.getText(),
					txtEmail.getText(),
					Date.from(instante),
					Utils.converterDouble(txtBaseSalary.getText()),
					comboBoxDepartment.getValue()
				);
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

	public void carregarObjetosAssociados()
	{
		if (depServico == null)
		{
			throw new IllegalStateException("depServico ta nulo");
		}

		List<Department> lista = depServico.buscarTodos();
		obsList = FXCollections.observableArrayList(lista);
		comboBoxDepartment.setItems(obsList);
	}

	private void setMsgErro(Map<String, String> erro)
	{
		Set<String> campos = erro.keySet();

		lblErrorName.setText( (campos.contains("nome")) ? erro.get("nome") : "" );
		lblErrorEmail.setText( (campos.contains("email")) ? erro.get("email") : "" );
		lblErrorBirthDate.setText( (campos.contains("birthDate")) ? erro.get("birthDate") : "" );
		lblErrorBaseSalary.setText( (campos.contains("baseSalary")) ? erro.get("baseSalary") : "" );
	}

	private void iniciarComboBoxDepartment()
	{
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>()
		{
			@Override
			public void updateItem(Department item, boolean vazio)
			{
				super.updateItem(item, vazio);
				setText(vazio ? "" : item.getName());
			}
		};

		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}

	//Métodos da interface
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		iniciarNodes();
	}
}