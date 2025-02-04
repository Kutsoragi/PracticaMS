package negocio.factura;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import integracion.empleado.DAOEmpleado;
import integracion.factoria_dao.DAOFactoria;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.pase.DAOPase;
import integracion.transactions.Transaction;
import integracion.transactions.TransactionManager;
import negocio.empleado.TEmpleado;
import negocio.pase.TPase;

public class SAFacturaImpl implements SAFactura {

	public TCarrito abrirFactura() {
		return new TCarrito();
	}
	
	private TLineaFactura getLinea(LinkedList<TLineaFactura> lineasFactura, int idPase) {
		TLineaFactura lineaFactura = null;
		ListIterator<TLineaFactura> iterator = lineasFactura.listIterator();
		while (lineaFactura == null && iterator.hasNext()) {
			TLineaFactura linea = iterator.next();
			if (linea.getPase().getID() == idPase)
				lineaFactura = linea;
		}
		return lineaFactura;
	}
	
	private void removeLineaFactura(LinkedList<TLineaFactura> lineasFactura, int idPase) {
		ListIterator<TLineaFactura> iterator = lineasFactura.listIterator();
		while(iterator.hasNext()) {
		    if(iterator.next().getPase().getID() == idPase)
		    	iterator.remove();
		}
	}
	
	public TCarrito añadirPase(TCarrito carrito) throws Exception {
		int idPase =  carrito.getIdPaseAuxiliar();
		int cantidad = carrito.getCantidadAuxiliar();
		
		LinkedList<TLineaFactura> lineasFactura = carrito.getLineasFactura();
		
		TPase tPase = new TPase(idPase, 1);
		TLineaFactura lineaFactura = getLinea(lineasFactura, idPase);
		
		if (lineaFactura != null)
			lineaFactura.setCantidad(lineaFactura.getCantidad() + cantidad);
		else
			lineasFactura.add(new TLineaFactura(tPase, cantidad));
		
		return carrito;
	}

	public TCarrito quitarPase(TCarrito carrito) {
		int idPase =  carrito.getIdPaseAuxiliar();
		int cantidad = carrito.getCantidadAuxiliar();
		LinkedList<TLineaFactura> lineasFactura = carrito.getLineasFactura();
		
		TLineaFactura lineaFactura = getLinea(lineasFactura, idPase);
		if (lineaFactura != null) {
			int nuevaCantidad = lineaFactura.getCantidad() - cantidad;
			if (nuevaCantidad <= 0)
				removeLineaFactura(lineasFactura, idPase);
			else
				lineaFactura.setCantidad(nuevaCantidad);
		} else {
			throw new NullPointerException("El pase especificado no existe en la factura.");
		}
		
		return carrito;
	}
	
	public boolean cerrarFactura(TCarrito carrito) throws Exception {
		int idEmpleado = carrito.getFactura().getIdEmpleado();

		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		// validar empleado
		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		TEmpleado empleadLeido = daoEmpleado.buscarEmpleadoPorID(idEmpleado);
		if (empleadLeido == null || !empleadLeido.isActivo()){
			transaction.rollback();
			throw new IllegalArgumentException("No existe el empleado.");
		}
		
		// validar pases
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		for (TLineaFactura l : carrito.getLineasFactura()) {
			TPase pase = daoPase.buscarPasePorID(l.getPase().getID());
			if (pase == null || !pase.isActivo()){
				transaction.rollback();
				throw new IllegalArgumentException("No existe el pase con ID: " + l.getPase().getID());
			}
			if (l.getCantidad() > pase.getStock()){
				transaction.rollback();
				throw new IllegalArgumentException("No hay suficiente stock del pase: " + pase.getID());
			}
			
			// calcular precio de linea
			l.setPrecio(l.getCantidad()*pase.getPrecio());
		}
		
		// los datos son validos y la factura es creable
		DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura(); 
		DAOLineaFactura daoLineaFactura = DAOFactoria.getInstancia().generarDAOLineaFactura();
		
		int idFactura = daoFactura.insertarFactura(carrito.getFactura());
		if (idFactura < 1){
			transaction.rollback();
			return false;
		}
		
		for (TLineaFactura l : carrito.getLineasFactura()){
			//ACTUALIZA EL STOCK DEL PASE
			TPase pase = daoPase.buscarPasePorID(l.getPase().getID());
			pase.setStock(pase.getStock() - l.getCantidad());
			daoPase.modificarPase(pase);
			//INSERTA LA LINEA FACTURA
			l.setIdFactura(idFactura);
			daoLineaFactura.insertarLineaFactura(l);
		}
		
		transaction.commit();

		return true;
	}
	
	//--
	
	public TCarrito buscarFacturaPorID(int id) throws Exception {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		TCarrito carrito = null;

		DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura();
		TFactura factura = daoFactura.buscarFacturaPorID(id);
		if (factura != null) {
			DAOLineaFactura daoLineaFactura = DAOFactoria.getInstancia().generarDAOLineaFactura();
			LinkedList<TLineaFactura> lineasFactura = daoLineaFactura.mostrarLineas(id);
			
			double precioTotal = 0.0;
			for (TLineaFactura l : lineasFactura)
				precioTotal += l.getPrecio();
			
			carrito = new TCarrito(factura, lineasFactura, precioTotal);
			
			transaction.commit();
		} else {
			transaction.rollback();
		}

		return carrito;
	}
	
	public LinkedList<TCarrito> listarFacturas() throws Exception {

		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		LinkedList<TCarrito> carritos = null;
		
		DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura();
		List<TFactura> facturas = daoFactura.mostrarListaFacturas();
		
		if (facturas != null) {
			carritos = new LinkedList<TCarrito>();
			
			DAOLineaFactura daoLineaFactura = DAOFactoria.getInstancia().generarDAOLineaFactura();
			for (TFactura f : facturas) {
				double precioTotal = 0.0;
				LinkedList<TLineaFactura> lineasFactura = daoLineaFactura.mostrarLineas(f.getID());
				
				for (TLineaFactura l : lineasFactura)
					precioTotal += l.getPrecio();
				
				carritos.add(new TCarrito(f, lineasFactura, precioTotal));
			}

			transaction.commit();
		} else {
			transaction.rollback();
		}

		return carritos;
	}

	public boolean devolverPase(TLineaFactura lineaFactura) throws Exception {	
		boolean valid = false;
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		DAOLineaFactura daoLineaFactura = DAOFactoria.getInstancia().generarDAOLineaFactura();
		TLineaFactura linea = daoLineaFactura.buscarLinea(lineaFactura.getIdFactura(), lineaFactura.getPase().getID());
		if (linea != null) { // linea con pase y factura dados existe
			DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase(); 
			TPase pase = daoPase.buscarPasePorID(linea.getPase().getID());
			
			int nuevaCantidad = linea.getCantidad() - lineaFactura.getCantidad();
			if (nuevaCantidad > 0) {
				linea.setCantidad(nuevaCantidad);
				linea.setPrecio(linea.getPrecio() - lineaFactura.getCantidad() * pase.getPrecio());
				daoLineaFactura.modificarLinea(linea);
				pase.setStock(pase.getStock() + lineaFactura.getCantidad());
				daoPase.modificarPase(pase);
			} else {
				daoLineaFactura.eliminarLinea(linea);
				pase.setStock(pase.getStock() + linea.getCantidad());
				daoPase.modificarPase(pase);
			}
			
			transaction.commit();
			valid = true;
		}
		else{
			transaction.rollback();
		}
		
		return valid;
	}
	
}
