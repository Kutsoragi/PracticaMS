package integracion.empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;

import integracion.transactions.TransactionManager;
import negocio.empleado.TEmpleado;
import negocio.empleado.TEmpleadoCompleto;
import negocio.empleado.TEmpleadoParcial;

public class DAOEmpleadoImpl implements DAOEmpleado{
	
	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;
	

	public int registrarEmpleado(TEmpleado tEmpleado) {
		Integer idEmpleado = 0;
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("INSERT INTO empleado (id_empleado, dni, nombre, tipo_empleado, horas, sueldo_horas, sueldo_completo) VALUES (null,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, tEmpleado.getDNI());
			pStatement.setString(2, tEmpleado.getNombre());
			if (tEmpleado.isCompleto()){
				pStatement.setString(3, "completo");
				pStatement.setNull(4, Types.NULL);
				pStatement.setNull(5, Types.NULL);
				pStatement.setDouble(6, tEmpleado.getSueldo());
			}
			else{
				pStatement.setString(3, "parcial");
				pStatement.setInt(4, tEmpleado.getHoras());
				pStatement.setDouble(5, tEmpleado.getSueldoPorHoras());
				pStatement.setNull(6, Types.NULL);
			}
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idEmpleado = rs.getInt(1);
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idEmpleado;
	}

	public int borrarEmpleado(int id) {
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("UPDATE empleado SET activo = false WHERE id_empleado = " + id);
			pStatement.executeUpdate();
			closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void modificarEmpleado(TEmpleado tEmpleado) {
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("UPDATE empleado SET nombre = ?, tipo_empleado = ?, horas = ?, sueldo_horas = ?, sueldo_completo = ?, activo = ?  WHERE dni = ?");
			pStatement.setString(1, tEmpleado.getNombre());
			pStatement.setString(7, tEmpleado.getDNI());
			pStatement.setBoolean(6, tEmpleado.isActivo());
			if (tEmpleado.isCompleto()){
				pStatement.setString(2, "completo");
				pStatement.setNull(3, Types.NULL);
				pStatement.setNull(4, Types.NULL);
				pStatement.setDouble(5, tEmpleado.getSueldo());
			}
			else{
				pStatement.setString(2, "parcial");
				pStatement.setInt(3, tEmpleado.getHoras());
				pStatement.setDouble(4, tEmpleado.getSueldoPorHoras());
				pStatement.setNull(5, Types.NULL);
			}
			pStatement.executeUpdate();
			closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public TEmpleado buscarEmpleadoPorID(int id) {
		TEmpleado empleado = null;
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM empleado WHERE id_empleado = " + id + " FOR UPDATE");
			rs = pStatement.executeQuery();
			if (rs.next()){
				if(rs.getString(5).equalsIgnoreCase("completo")){
					empleado = new TEmpleadoCompleto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(8));
				}
				else{
					empleado = new TEmpleadoParcial(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(6), rs.getDouble(7));
				}
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleado;
	}

	public LinkedList<TEmpleado> mostrarListaEmpleados() {
		LinkedList<TEmpleado> lista = new LinkedList<TEmpleado>();
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM empleado WHERE activo = true");
			rs = pStatement.executeQuery();
			while (rs.next()){
				if(rs.getString(5).equalsIgnoreCase("completo")){
					lista.add(new TEmpleadoCompleto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(8)));
				}
				else{
					lista.add(new TEmpleadoParcial(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(6), rs.getDouble(7)));
				}
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public TEmpleado buscarEmpleadoPorDNI(String dni) {
		TEmpleado empleado = null;
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT * FROM empleado WHERE dni = ?  FOR UPDATE");
			pStatement.setString(1, dni);
			rs = pStatement.executeQuery();
			if (rs.next()){
				if(rs.getString(5).equalsIgnoreCase("completo")){
					empleado = new TEmpleadoCompleto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(8));
				}
				else{
					empleado = new TEmpleadoParcial(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(6), rs.getDouble(7));
				}
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleado;
	}
	
	public LinkedList<TEmpleado> mostrarEmpleadosPorJornada(boolean esCompleta){
		LinkedList<TEmpleado> lista = new LinkedList<TEmpleado>();
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			if(esCompleta){
				pStatement = conexion.prepareStatement("SELECT * FROM empleado WHERE activo = true AND tipo_empleado = 'completo'");
				rs = pStatement.executeQuery();
				while(rs.next()){
					lista.add(new TEmpleadoCompleto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(8)));
				}
			}
			else{
				pStatement = conexion.prepareStatement("SELECT * FROM empleado WHERE tipo_empleado = 'parcial' AND activo = true");
				rs = pStatement.executeQuery();
				while(rs.next()){
					lista.add(new TEmpleadoParcial(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(6), rs.getDouble(7)));
				}
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	private void closeResultSet() throws SQLException {
		rs.close();
	}
	
	private void closeStatement() throws SQLException {
		pStatement.close();
	}
}
