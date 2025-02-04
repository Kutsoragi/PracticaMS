package presentacion.command.empleado;

import negocio.empleado.TEmpleado;
import negocio.factoria_sa.SAFactoria;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class RegistrarEmpleadoCommand implements Command {

	public Context execute(Object data) {
		Context context;
		int res;
		try {
			res = SAFactoria.getInstancia().generarSAEmpleado().registrarEmpleado((TEmpleado) data);
			if (res > 0)
				context = new Context(Evento.REGISTRAR_EMPLEADO_OK, "Empleado registrado con ID: " + res + ".");
			else
				context = new Context(Evento.REGISTRAR_EMPLEADO_KO, "Ya existe un empleado con ese DNI.");
		} catch (Exception e) {
			context = new Context(Evento.REGISTRAR_EMPLEADO_KO, e.getMessage());
		}
		
		return context;
	}
}
