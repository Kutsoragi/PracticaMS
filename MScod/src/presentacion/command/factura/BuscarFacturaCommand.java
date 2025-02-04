package presentacion.command.factura;

import negocio.factoria_sa.SAFactoria;
import negocio.factura.TCarrito;
import presentacion.command.Command;
import presentacion.controller.Context;
import presentacion.controller.Evento;

public class BuscarFacturaCommand implements Command {

	public Context execute(Object data) {
		Context context;
		try {
			TCarrito res = SAFactoria.getInstancia().generarSAFactura().buscarFacturaPorID((Integer) data);
			if (res != null)
				context = new Context(Evento.BUSCAR_FACTURA_OK, res);
			else
				context = new Context(Evento.BUSCAR_FACTURA_KO, "Factura no encontrada.");
		} catch(Exception e) {
			context = new Context(Evento.BUSCAR_FACTURA_KO, e.getMessage());
		}
		
		return context;
	}
}
