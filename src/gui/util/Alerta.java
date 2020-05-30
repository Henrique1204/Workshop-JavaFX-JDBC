package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerta
{
	public static void mostrarAlerta(String titulo, String cabecalho, String conteudo, AlertType tipo)
	{
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(conteudo);
		alerta.show();
	}

	public static Optional<ButtonType> mostrarConfirmacao(String titulo, String conteudo)
	{
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(conteudo);
		return alerta.showAndWait();
	}
}