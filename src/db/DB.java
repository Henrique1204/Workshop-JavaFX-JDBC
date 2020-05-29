package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB
{
	private static Connection conn = null;

	public static Connection getConnection()
	{
		if (conn == null)
		{
			try
			{
				Properties props = carregarProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e)
			{
				throw new DBException(e.getMessage());
			}
		}

		return conn;
	}

	public static void fecharConnection()
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				throw new DBException(e.getMessage());
			}
		}
	}

	private static Properties carregarProperties()
	{
		try(FileInputStream fs = new FileInputStream("db.properties"))
		{
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e)
		{
			throw new DBException(e.getMessage());
		}
	}

	public static void fecharStatement(Statement st)
	{
		if (st != null)
		{
			try
			{
				st.close();
			}
			catch (SQLException e)
			{
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void fecharResultSet(ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				throw new DBException(e.getMessage());
			}
		}
	}
}