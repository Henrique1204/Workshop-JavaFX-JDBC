package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJdbc;
import model.dao.impl.SellerDaoJdbc;
import model.entidades.Department;
import model.entidades.Seller;

public class DaoFactory
{
	public static EntidadeDao<Seller> criarSellerDao()
	{
		return new SellerDaoJdbc(DB.getConnection());
	}

	public static EntidadeDao<Department> criarDepartmentDao()
	{
		return new DepartmentDaoJdbc(DB.getConnection());
	}
}