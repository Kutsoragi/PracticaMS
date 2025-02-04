package presentacion.command.empleado;

import negocio.empleado.TEmpleado;
import negocio.factoria_sa.SAFactoria;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class BuscarEmpleadoCommand implements Command {

	public Context execute(Object data) {
		Context context;
		try {
			TEmpleado res = SAFactoria.getInstancia().generarSAEmpleado().buscarEmpleadoPorID((Integer) data);
			if (res != null)
				context = new Context(Evento.BUSCAR_EMPLEADO_OK, res);
			else
				context = new Context(Evento.BUSCAR_EMPLEADO_KO, "Empleado no encontrado.");
		} catch(Exception e) {
			context = new Context(Evento.BUSCAR_EMPLEADO_KO, e.getMessage());
		}
		
		return context;
	}
}
