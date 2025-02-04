package integracion.empleado;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import negocio.empleado.TEmpleado;
import negocio.empleado.TEmpleadoCompleto;

public class DAOEmpleadoTest {

	@Test
	public void registrarEmpleadoExito() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		TEmpleado empleado = new TEmpleadoCompleto("12345678X", "Nombre", true, 2000);
		try {
			int res = daoEmpleado.registrarEmpleado(empleado);
			assertTrue(res > 0); //registrado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void modificarEmpleadoExito() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		TEmpleado empleado = new TEmpleadoCompleto("12345678X", "NombreNuevo", true, 20200);
		try {
			daoEmpleado.modificarEmpleado(empleado);
			assertTrue(true); //modificado correctamente
		} catch(Exception e) { fail(e.getMessage()); }
	}

	@Test
	public void borrarEmpleadoExito() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		int id = 1;
		try {
			int res = daoEmpleado.borrarEmpleado(id);
			assertTrue(res > 0); //borrado correctamente
		} catch(Exception e) {
			fail(e.getMessage());
		}	
	}
	
	@Test
	public void buscarPorDNIExito() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		String dni = "12345678X";
		try {
			TEmpleado res = daoEmpleado.buscarEmpleadoPorDNI(dni); 
			assertTrue(res != null); //no es nulo, existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void buscarPorDNIFallo() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		String dni = "00000000A";
		try {
			TEmpleado res = daoEmpleado.buscarEmpleadoPorDNI(dni); 
			assertTrue(res != null); //es nulo, no existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	@Test
	public void buscarEmpleadoExito() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		int id = 2;
		try {
			TEmpleado res = daoEmpleado.buscarEmpleadoPorID(id); 
			assertTrue(res != null); //no es nulo, existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void buscarEmpleadoFallo() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		int id = 200;
		try {
			TEmpleado res = daoEmpleado.buscarEmpleadoPorID(id);  
			assertTrue(res == null); //es nulo, no existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void mostrarListaEmpleadosExito() {
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		
		try {
			List<TEmpleado> res = daoEmpleado.mostrarListaEmpleados();
			assertTrue(!res.isEmpty()); 
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void mostrarEmpleadosPorJornadaExito() {
		
		DAOEmpleado daoEmpleado = new DAOEmpleadoImpl();
		boolean esCompleta = true;
		
		try {
			List<TEmpleado> res = daoEmpleado.mostrarEmpleadosPorJornada(esCompleta);
			assertTrue(res != null && !res.isEmpty());
		} catch(Exception e) { fail(e.getMessage());}
	}
}
