package model.servicos;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EntidadeDao;
import model.entidades.Seller;

public class SellerServico
{
	private EntidadeDao<Seller> dao = DaoFactory.criarSellerDao();

	//M�todos implementados
	public void salvarOuAtualizar(Seller obj)
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

	public void remover(Seller obj)
	{
		dao.deletarPorId(obj.getId());
	}

	//M�todos sobrescrevidos
	public List<Seller> buscarTodos()
	{
		return dao.buscarTodos();
	}
}