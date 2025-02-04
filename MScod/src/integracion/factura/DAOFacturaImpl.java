package integracion.factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import integracion.transactions.TransactionManager;
import negocio.factura.TFactura;

public class DAOFacturaImpl implements DAOFactura {

	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;

	
	public TFactura buscarFacturaPorID(int id) {
		TFactura factura = null;
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT DISTINCT id_factura, id_empleado, fecha FROM factura WHERE id_factura = " + id + " FOR UPDATE");
			rs = pStatement.executeQuery();
			if (rs.next()){
				factura = new TFactura(rs.getInt(1), rs.getInt(2), rs.getString(3));
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return factura;
	}
	
	public LinkedList<TFactura> mostrarListaFacturas() {
		LinkedList<TFactura> lista = new LinkedList<TFactura>();
		
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("SELECT DISTINCT id_factura, id_empleado, fecha FROM factura;");
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TFactura(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public int insertarFactura(TFactura tFactura) {
		int idFactura = -1;
		try {
			conexion = (Connection)TransactionManager.getInstance().getTransaction().getResource();
			
			pStatement = conexion.prepareStatement("INSERT INTO factura (id_factura, id_empleado, fecha) VALUES (null,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, tFactura.getIdEmpleado());
			pStatement.setString(2, tFactura.getFecha());
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idFactura = rs.getInt(1);
			}
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idFactura;
	}
	
	private void closeResultSet() throws SQLException {
		rs.close();
	}
	
	private void closeStatement() throws SQLException {
		pStatement.close();
	}

}
