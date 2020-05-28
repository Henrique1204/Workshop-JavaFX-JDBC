package model.servicos;

import java.util.ArrayList;
import java.util.List;

import model.entidades.Department;

public class DepartmentServico
{
	public List<Department> buscarTodos()
	{
		List<Department> lista = new ArrayList<>();

		lista.add(new Department(1, "Livros"));
		lista.add(new Department(2, "Computadores"));
		lista.add(new Department(3, "Eletronicos"));

		return lista;
	}
}