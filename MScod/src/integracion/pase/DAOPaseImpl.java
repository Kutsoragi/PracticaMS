package integracion.pase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import integracion.transactions.TransactionManager;
import negocio.pase.TPase;

public class DAOPaseImpl implements DAOPase {

	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;

	
	public int registrarPase(TPase tPase) {
		Integer idPase = 0;
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("INSERT INTO pase (id_pase, fecha, hora_ini, precio, hora_fin, id_peliculas, sala, stock) VALUES (null,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, tPase.getFecha());
			pStatement.setString(2, tPase.getHoraInicio());
			pStatement.setDouble(3, tPase.getPrecio());
			pStatement.setString(4, tPase.getHoraFin());
			pStatement.setInt(5, tPase.getPelicula());
			pStatement.setInt(6, tPase.getSala());
			pStatement.setInt(7, tPase.getStock());
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idPase = rs.getInt(1);
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idPase;
	}

	public int modificarPase(TPase tPase) {
		Integer pase = 0;
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("UPDATE pase SET fecha = ?, hora_ini = ?, precio = ?, hora_fin = ?, id_peliculas = ?, sala = ?, stock = ?  WHERE id_pase = " + tPase.getID());
			pStatement.setString(1, tPase.getFecha());
			pStatement.setString(2, tPase.getHoraInicio());
			pStatement.setDouble(3, tPase.getPrecio());
			pStatement.setString(4, tPase.getHoraFin());
			pStatement.setInt(5, tPase.getPelicula());
			pStatement.setInt(6, tPase.getSala());
			pStatement.setInt(7, tPase.getStock());
			pStatement.executeUpdate();
			pase = tPase.getID();
			
			closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pase;
	}

	public int borrarPase(int id) {
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("UPDATE pase SET activo = false WHERE id_pase = " + id);
			pStatement.executeUpdate();
			closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public TPase buscarPasePorID(int id) {
		TPase pase = null;
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE id_pase = " + id + " FOR UPDATE");
			rs = pStatement.executeQuery();
			if (rs.next()){
				pase = new TPase(rs.getInt(1), rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5), rs.getString(2), rs.getDouble(4), rs.getInt(7), rs.getInt(8), rs.getBoolean(6), rs.getInt(9));
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pase;
	}

	public LinkedList<TPase> mostrarListaPases() {
		LinkedList<TPase> lista = new LinkedList<TPase>();
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE activo = true");
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TPase(rs.getInt(1), rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5), rs.getString(2), rs.getDouble(4), rs.getInt(7), rs.getInt(8), rs.getBoolean(6), rs.getInt(9)));
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public LinkedList<TPase> mostrarPasesPorPeliculas(int idPeli) {
		LinkedList<TPase> lista = new LinkedList<TPase>();
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE activo = true AND id_peliculas = " + idPeli);
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TPase(rs.getInt(1), rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5), rs.getString(2), rs.getDouble(4), rs.getInt(7), rs.getInt(8), rs.getBoolean(6), rs.getInt(9)));
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public boolean franjaValida(TPase tPase){
		boolean valido = true;
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE activo = true AND sala = ? AND fecha = ? AND id_pase != " + tPase.getID() + " AND ((hora_ini <= ? AND hora_fin >= ?) OR (hora_ini <= ? AND hora_fin >= ?))");
			pStatement.setInt(1, tPase.getSala());
			pStatement.setString(2, tPase.getFecha());
			pStatement.setString(3, tPase.getHoraInicio());
			pStatement.setString(4, tPase.getHoraInicio());
			pStatement.setString(5, tPase.getHoraFin());
			pStatement.setString(6, tPase.getHoraFin());
			rs = pStatement.executeQuery();
			if (rs.next()){
				valido = false;
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valido;
	}
	
	
	private void closeResultSet() throws SQLException {
		rs.close();
	}
	
	private void closeStatement() throws SQLException {
		pStatement.close();
	}
}
