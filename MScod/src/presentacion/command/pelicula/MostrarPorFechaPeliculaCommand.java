
package presentacion.command.pelicula;

import java.util.List;

import negocio.factoria_sa.SAFactoria;
import negocio.pelicula.TPelicula;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class MostrarPorFechaPeliculaCommand implements Command {

	public Context execute(Object data) {
		Context context;
		try {
			List<TPelicula> res = SAFactoria.getInstancia().generarSAPelicula().mostrarPeliculasPorFecha((String) data);
			if (!res.isEmpty())
				context = new Context(Evento.MOSTRAR_POR_FECHA_PELICULA_OK, res);
			else
				context = new Context(Evento.MOSTRAR_POR_FECHA_PELICULA_KO, "No hay Péliculas que mostrar.");
		} catch(Exception e) {
			context = new Context(Evento.MOSTRAR_POR_FECHA_PELICULA_KO, e.getMessage());
		}
		
		return context;
	}
}
