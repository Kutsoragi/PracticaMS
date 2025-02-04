package presentacion.command.pelicula;

import java.util.LinkedList;

import negocio.factoria_sa.SAFactoria;
import negocio.factura.Pair;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class PeliculasMasVendidasCommand implements Command {

	public Context execute(Object data) {
		Context context;
		LinkedList<Pair<String, Integer>> res;
		try {
			res = SAFactoria.getInstancia().generarSAPelicula().peliculasMasVendidas((Integer) data);
			if (res != null)
				context = new Context(Evento.PELICULAS_MAS_VENDIDAS_OK, res);
			else
				context = new Context(Evento.PELICULAS_MAS_VENDIDAS_KO, "No hay Péliculas que mostrar.");
		} catch (Exception e) {
			context = new Context(Evento.PELICULAS_MAS_VENDIDAS_KO, e.getMessage());
		}
		
		return context;
	}
}
