package gui.util;

import javafx.scene.control.TextField;

public class Restricoes
{
	public static void setTextFieldInteger(TextField txt)
	{
		txt.textProperty().addListener( (obs, valorAntigo, valorNovo) ->
		{
			if (valorNovo != null && !valorNovo.matches("\\d*"))
			{
				txt.setText(valorAntigo);
			}
		});
	}

	public static void setTextFieldMaxLength(TextField txt, int max)
	{
		txt.textProperty().addListener( (obs, valorAntigo, valorNovo) ->
		{
			if (valorNovo != null && valorNovo.length() > max )
			{
				txt.setText(valorAntigo);
			}
		});
	}

	public static void setTextFieldDouble(TextField txt)
	{
		txt.textProperty().addListener( (obs, valorAntigo, valorNovo) ->
		{
			if (valorNovo != null && !valorNovo.matches("\\d*([\\.]\\d*)?"))
			{
				txt.setText(valorAntigo);
			}
		});
	}
}