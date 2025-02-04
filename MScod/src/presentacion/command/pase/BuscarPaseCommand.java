package presentacion.command.pase;

import negocio.factoria_sa.SAFactoria;
import negocio.pase.TPase;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class BuscarPaseCommand implements Command {

	public Context execute(Object data) {
		Context context;
		try {
			TPase res = SAFactoria.getInstancia().generarSAPase().buscarPasePorID((Integer) data);
			if (res != null)
				context = new Context(Evento.BUSCAR_PASE_OK, res);
			else
				context = new Context(Evento.BUSCAR_PASE_KO, "Pase no encontrado.");
		} catch(Exception e) {
			context = new Context(Evento.BUSCAR_PASE_KO, e.getMessage());
		}
		
		return context;
	}
}
