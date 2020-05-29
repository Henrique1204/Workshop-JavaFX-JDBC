package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.EntidadeDao;
import model.entidades.Department;
import model.entidades.Seller;

public class DepartmentDaoJdbc implements EntidadeDao<Department>
{
	private Connection conn;

	//Construtor
	public DepartmentDaoJdbc(Connection conn)
	{
		this.conn = conn;
	}

	//Métodos sobrescrevidos
	@Override
	public void inserir(Department obj)
	{
		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());

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
					throw new DBException("Erro inesperado, nenhuma linha foi afetada");
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
	public void atualizar(Department obj)
	{
		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
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
	public Department buscarPorId(Integer id)
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next())
			{
				Department obj = instanciarDepartment(rs);

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
	public List<Department> buscarTodos()
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Id");

			rs = st.executeQuery();

			List<Department> lista = new ArrayList<>();

			while (rs.next())
			{
				lista.add( new Department( rs.getInt("id"), rs.getString("Name") ) );
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
	public List<Seller> buscarPorDepartment(Department dep)
	{
		return null;
	}

	//Métodos implementados
	private Department instanciarDepartment(ResultSet rs) throws SQLException
	{
		Department department = new Department( rs.getInt("Id"), rs.getString("Name") );
		return department;
	}
}