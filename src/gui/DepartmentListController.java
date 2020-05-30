package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listener.DataChangeListener;
import gui.util.Alerta;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Department;
import model.servicos.DepartmentServico;

public class DepartmentListController implements Initializable, DataChangeListener
{
	private DepartmentServico servico;
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumnId; //Primeiro tipo do TableColumn<> � o tipo da entidade e o segundo o tipo do valor
	@FXML
	private TableColumn<Department, String> tableColumnName;
	@FXML
	private TableColumn<Department, Department> tableColumnEditar;
	@FXML
	private Button btnNovo;

	private ObservableList<Department> obsLista;

	public void setDepartmentServico(DepartmentServico servico)
	{
		this.servico = servico;
	} 

	//M�todos implementados
	@FXML
	public void onBtnNovoAction(ActionEvent evento)
	{
		Stage parentStage = Utils.stageAtual(evento);
		Department obj = new Department();
		ligarAoForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}

	private void iniciarNode()
	{
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getCena().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void atualizarTableView()
	{
		if ( this.servico  == null)
		{
			throw new IllegalStateException("Servico nao settado");
		}

		List<Department> lista = servico.buscarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tableViewDepartment.setItems(obsLista);
		iniciarBtnEditar();
	}

	private void ligarAoForm(Department obj, String caminhoAbsoluto, Stage parentStage)
	{
		try
		{
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(caminhoAbsoluto));
			Pane pane = carregar.load();

			DepartmentFormController controller = carregar.getController();
			controller.setDepartment(obj);
			controller.setServico(new DepartmentServico());
			controller.subscribeDataChangeListener(this);
			controller.atualizarDadosForm();

			Stage ligacaoStage = new Stage();
			ligacaoStage.setTitle("Entre com os dados do Department");
			ligacaoStage.setScene(new Scene(pane));
			ligacaoStage.setResizable(false);
			ligacaoStage.initOwner(parentStage);
			ligacaoStage.initModality(Modality.WINDOW_MODAL);;
			ligacaoStage.showAndWait();
		}
		catch (IOException e)
		{
			Alerta.mostrarAlerta("IO Exception", "Erro ao carregar a View", e.getMessage(), AlertType.ERROR);
		}
		
	}

	private void iniciarBtnEditar()
	{
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Department, Department>()
				{
					private final Button btn = new Button("editar");

					@Override
					protected void updateItem(Department obj, boolean vazio)
					{
						super.updateItem(obj, vazio);

						if (obj == null)
						{
							setGraphic(null);
							return;
						}

						setGraphic(btn);
						btn.setOnAction( evento -> ligarAoForm(obj, "/gui/DepartmentForm.fxml", Utils.stageAtual(evento)));
					}
				});
	}

	//M�todos da interface
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		iniciarNode();
	}

	@Override
	public void onDataChange()
	{
		atualizarTableView();
	}
}