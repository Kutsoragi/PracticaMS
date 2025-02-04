package negocio.pase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

public class SAPaseTest {

	@Test
	public void registrarPaseExito() {
		SAPase saPase = new SAPaseImpl();
		
		TPase pase = new TPase("09:00", "11:00", "2020-05-27", 5, 1, 1, 20);
		try {
			int res = saPase.registrarPase(pase);
			assertTrue(res > 0); //registrado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void registrarPaseFallo() {
		SAPase saPase = new SAPaseImpl();
		
		TPase pase = new TPase("09:00", "11:00", "2020-05-27", 5, 1, 1, 20);
		try {
			int res = saPase.registrarPase(pase);
			assertTrue(res <= 0); //registrado sin exito. Ya existe un pase en esa franja horaria para esa sala
		} catch(Exception e) { fail(e.getMessage());}
	}
		
	@Test
	public void modificarPaseExito() {
		SAPase saPase = new SAPaseImpl();
		
		TPase pase = new TPase(1, "09:00", "12:00", "2020-09-21", 5, 1, 1, 20);
		try {
			int res = saPase.modificarPase(pase);
			assertTrue(res > 0); //modificado correctamente
		} catch(Exception e) { fail(e.getMessage()); }
	}
	
	@Test
	public void modificarPaseFallo() {
		SAPase saPase = new SAPaseImpl();
		
		TPase pase = new TPase(200, "09:00", "12:00", "2020-09-21", 5, 1, 1, 20);
		try {
			int res = saPase.modificarPase(pase);
			assertFalse(res > 0); //nada que modificar
		} catch(Exception e) { fail(e.getMessage()); }
	}

	@Test
	public void borrarPaseExito() {
		SAPase saPase = new SAPaseImpl();
		
		int id = 1;
		try {
			int res = saPase.borrarPase(id);
			assertTrue(res > 0); //borrado correctamente
		} catch(Exception e) {
			fail(e.getMessage());
		}	
	}

	@Test
	public void buscarPaseExito() {
		SAPase saPase = new SAPaseImpl();
		
		int id = 2;
		try {
			TPase res = saPase.buscarPasePorID(id); 
			assertTrue(res != null); //no es nulo, existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void buscarPaseFallo() {
		SAPase saPase = new SAPaseImpl();
		
		int id = 200;
		try {
			TPase res = saPase.buscarPasePorID(id);  
			assertTrue(res == null); //es nulo, no existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void mostrarListaPasesExito() {
		SAPase saPase = new SAPaseImpl();
		
		try {
			List<TPase> res = saPase.mostrarListaPases();
			assertTrue(!res.isEmpty()); 
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void mostrarPasesPorPeliculaExito() {
		SAPase saPase = new SAPaseImpl();
		
		int idPelicula = 2;
		try {
			List<TPase> res = saPase.mostrarPasesPorPelicula(idPelicula);
			assertTrue(res != null && !res.isEmpty());
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void mostrarPasesPorPeliculaFallo() {
		SAPase saPase = new SAPaseImpl();
		
		int idPelicula = 200;
		try {
			List<TPase> res = saPase.mostrarPasesPorPelicula(idPelicula);
			assertFalse(res != null && !res.isEmpty());
		} catch(Exception e) { fail(e.getMessage());}
	}
	
}
