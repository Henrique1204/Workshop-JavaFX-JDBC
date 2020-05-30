package model.servicos;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EntidadeDao;
import model.entidades.Department;

public class DepartmentServico
{
	private EntidadeDao<Department> dao = DaoFactory.criarDepartmentDao();

	//M�todos implementados
	public void salvarOuAtualizar(Department obj)
	{
		if (obj.getId() == null)
		{
			dao.inserir(obj);
		}
		else
		{
			dao.atualizar(obj);
		}
	}

	public void remover(Department obj)
	{
		dao.deletarPorId(obj.getId());
	}

	//M�todos sobrescrevidos
	public List<Department> buscarTodos()
	{
		return dao.buscarTodos();
	}
}