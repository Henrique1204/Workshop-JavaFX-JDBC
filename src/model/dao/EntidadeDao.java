package model.dao;

import java.util.List;

import model.entidades.Department;
import model.entidades.Seller;

public interface EntidadeDao<Tipo>
{
	void inserir(Tipo obj);
	void atualizar(Tipo obj);
	void deletarPorId(Integer id);
	Tipo buscarPorId(Integer id);
	List<Tipo> buscarTodos();
	List<Seller> buscarPorDepartment(Department dep);
}