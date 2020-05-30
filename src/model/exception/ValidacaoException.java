package model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String msg)
	{
		super(msg);	
	}

	private Map<String, String> erros = new HashMap<>();

	public Map<String, String> getErros()
	{
		return this.erros;
	}

	public void addError(String nomeCampo, String msgErro)
	{
		erros.put(nomeCampo, msgErro);
	}
}