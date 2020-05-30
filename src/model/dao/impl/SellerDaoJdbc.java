package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.EntidadeDao;
import model.entidades.Department;
import model.entidades.Seller;

public class SellerDaoJdbc implements EntidadeDao<Seller>
{
	private Connection conn;

	//Construtor
	public SellerDaoJdbc(Connection conn)
	{
		this.conn = conn;
	}

	//Métodos sobrescrevidos
	@Override
	public void inserir(Seller obj)
	{
		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBrithDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0)
			{
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next())
				{
					int id = rs.getInt(1);
					obj.setId(id);
				}
				else
				{
					throw new DBException("Erro inesperado, nenhuma linha foi afetada!");
				}

				DB.fecharResultSet(rs);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e.getMessage());
		}
		finally
		{
			DB.fecharStatement(st);
		}
	}

	@Override
	public void atualizar(Seller obj)
	{
		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBrithDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.execute();
		}
		catch (SQLException e)
		{
			throw new DBException(e.getMessage());
		}
		finally
		{
			DB.fecharStatement(st);
		}
	}

	@Override
	public void deletarPorId(Integer id)
	{
		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DBException(e.getMessage());
		}
		finally
		{
			DB.fecharStatement(st);
		}
	}

	@Override
	public Seller buscarPorId(Integer id)
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if(rs.next())
			{
				Department dep = instanciarDepartment(rs);
				Seller obj = instanciarSeller(rs, dep);

				return obj;
			}

			return null;
		}
		catch (SQLException e)
		{
			throw new DBException(e.getMessage());
		}
		finally
		{
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	@Override
	public List<Seller> buscarTodos()
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.prepareStatement(
						"SELECT seller.*, department.Name as DepName "
						+ "FROM seller INNER JOIN department "
						+ "ON seller.DepartmentId = department.Id "
						+ "ORDER BY Id");

			rs = st.executeQuery();

			List<Seller> lista = new ArrayList<>();
			//<id do item, Tipo do objeto>
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next())
			{
				//Busca o item na chave -- buscar o valor da coluna DepartmentId
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null)
				{
					dep = instanciarDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}


				Seller obj = instanciarSeller(rs, dep);
				lista.add(obj);
			}

			return lista;
		}
		catch (SQLException e)
		{
			throw new DBException(e.getMessage());
		}
		finally
		{
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	@Override
	public List<Seller> buscarPorDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? ORDER BY Id");

			st.setInt(1, department.getId());

			rs = st.executeQuery();

			List<Seller> lista = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next())
			{
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null)
				{
					dep = instanciarDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller obj = instanciarSeller(rs, dep);
				lista.add(obj);
			}

			return lista;
		}
		catch (SQLException e)
		{
			throw new DBException(e.getMessage());
		}
		finally
		{
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	//Métodos implementados
	private Department instanciarDepartment(ResultSet rs) throws SQLException
	{
		Department department = new Department( rs.getInt("DepartmentId"), rs.getString("DepName") );
		return department;
	}

	private Seller instanciarSeller(ResultSet rs, Department department) throws SQLException
	{
		Seller seller = new Seller(
						rs.getInt("Id"),
						rs.getString("Name"),
						rs.getString("Email"),
						new java.util.Date(rs.getTimestamp("BirthDate").getTime()),
						rs.getDouble("BaseSalary"),
						department
					);

		return seller;
	}
}