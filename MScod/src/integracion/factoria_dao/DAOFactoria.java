package integracion.factoria_dao;
import integracion.empleado.DAOEmpleado;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.pase.DAOPase;
import integracion.pelicula.DAOPelicula;

public abstract class DAOFactoria{
		
		private static DAOFactoria instancia;
		
		public synchronized static DAOFactoria getInstancia(){
			if(instancia == null){
				instancia = new DAOFactoriaImpl();
			}
			return instancia;
		}

		public abstract DAOPelicula generarDAOPelicula();
		public abstract DAOPase generarDAOPase();
		public abstract DAOEmpleado generarDAOEmpleado();
		public abstract DAOFactura generarDAOFactura();
		public abstract DAOLineaFactura generarDAOLineaFactura();
	}
