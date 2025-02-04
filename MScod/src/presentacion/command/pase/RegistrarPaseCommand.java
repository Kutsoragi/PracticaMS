package presentacion.command.pase;

import negocio.factoria_sa.SAFactoria;
import negocio.pase.TPase;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class RegistrarPaseCommand implements Command {

	public Context execute(Object data) {
		Context context;
		int res;
		try {
			res = SAFactoria.getInstancia().generarSAPase().registrarPase((TPase) data);
			if (res > 0)
				context = new Context(Evento.REGISTRAR_PASE_OK, "Pase registrado con ID: " + res + ".");
			else
				context = new Context(Evento.REGISTRAR_PASE_KO, "El Pase no se pudo registrar con éxito.");
		} catch (Exception e) {
			context = new Context(Evento.REGISTRAR_PASE_KO, e.getMessage());
		}
		
		return context;
	}	
}
