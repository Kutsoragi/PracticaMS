package negocio.pelicula;

import java.util.LinkedList;
import java.util.List;

import negocio.factura.Pair;

public interface SAPelicula {

	int registrarPelicula(TPelicula pelicula) throws Exception;
	int modificarPelicula(TPelicula pelicula) throws Exception;
	int borrarPelicula(int id) throws Exception;
	TPelicula buscarPeliculaPorID(int id) throws Exception;
	List<TPelicula> mostrarListaPeliculas() throws Exception;
	List<TPelicula> mostrarPeliculasPorFecha(String fecha) throws Exception;
	LinkedList<Pair<String, Integer>> peliculasMasVendidas(int numTop) throws Exception;
}
