package integracion.pelicula;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import integracion.factoria_query.QueryFactory;
import negocio.pelicula.TPelicula;

public class DAOPeliculaTest {

	@Test
	public void registrarPeliculaExito() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		TPelicula pelicula = new TPelicula(120, "Nombre");
		try {
			int res = daoPelicula.registrarPelicula(pelicula);
			assertTrue(res > 0); //registrada correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
		
	@Test
	public void modificarPeliculaExito() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		
		TPelicula pelicula = new TPelicula(1, 120, "Nombre");
		try {
			int res = daoPelicula.modificarPelicula(pelicula);
			assertTrue(res > 0); //modificada correctamente
		} catch(Exception e) { fail(e.getMessage()); }
	}
	
	@Test
	public void modificarPeliculaFallo() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		
		TPelicula pelicula = new TPelicula(200, 120, "Nombre");
		try {
			int res = daoPelicula.modificarPelicula(pelicula);
			assertFalse(res > 0); //nada que modificar
		} catch(Exception e) { fail(e.getMessage()); }
	}

	@Test
	public void borrarPeliculaExito() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		
		int id = 1;
		try {
			int res = daoPelicula.borrarPelicula(id);
			assertTrue(res > 0); //borrada correctamente
		} catch(Exception e) {
			fail(e.getMessage());
		}	
	}

	@Test
	public void buscarPeliculaExito() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		
		int id = 2;
		try {
			TPelicula res = daoPelicula.buscarPeliculaPorID(id); 
			assertTrue(res != null); //no es nulo, existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void buscarPeliculaFallo() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		
		int id = 200;
		try {
			TPelicula res = daoPelicula.buscarPeliculaPorID(id);  
			assertTrue(res == null); //es nulo, no existe
		} catch(Exception e) { fail(e.getMessage());}	
	}
	
	@Test
	public void mostrarListaPeliculasExito() {
		DAOPelicula daoPelicula = new DAOPeliculaImpl();
		
		try {
			List<TPelicula> res = daoPelicula.mostrarListaPeliculas();
			assertTrue(!res.isEmpty()); 
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mostrarPeliculasPorFechaExito() {
		
		String fecha = "2020-10-10";
		try {
			List<TPelicula> res = (List<TPelicula>) QueryFactory.getInstance().mostrarPeliculasPorFecha().execute(fecha);
			assertTrue(res != null && !res.isEmpty());
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mostrarPeliculasPorFechaFallo() {
		String fecha = "1000-10-10";
		
		try {
			List<TPelicula> res = (List<TPelicula>) QueryFactory.getInstance().mostrarPeliculasPorFecha().execute(fecha);
			assertFalse(res != null && !res.isEmpty());
		} catch(Exception e) { fail(e.getMessage());}
	}
}
