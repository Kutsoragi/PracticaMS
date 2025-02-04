package presentacion.command;

import presentacion.command.empleado.BorrarEmpleadoCommand;
import presentacion.command.empleado.BuscarEmpleadoCommand;
import presentacion.command.empleado.ModificarEmpleadoCommand;
import presentacion.command.empleado.MostrarListaEmpleadoCommand;
import presentacion.command.empleado.MostrarPorJornadaEmpleadoCommand;
import presentacion.command.empleado.RegistrarEmpleadoCommand;
import presentacion.command.factura.AbrirFacturaCommand;
import presentacion.command.factura.AnadirPaseFacturaCommand;
import presentacion.command.factura.BuscarFacturaCommand;
import presentacion.command.factura.CerrarFacturaCommand;
import presentacion.command.factura.DevolverPaseFacturaCommand;
import presentacion.command.factura.MostrarListaFacturaCommand;
import presentacion.command.factura.QuitarPaseFacturaCommand;
import presentacion.command.factura_tienda.AbrirFacturaTiendaCommand;
import presentacion.command.factura_tienda.AnadirProductoCommand;
import presentacion.command.factura_tienda.BuscarFacturaTiendaCommand;
import presentacion.command.factura_tienda.CerrarFacturaTiendaCommand;
import presentacion.command.factura_tienda.DevolverProductoCommand;
import presentacion.command.factura_tienda.ListarFacturasTiendaCommand;
import presentacion.command.factura_tienda.QuitarProductoCommand;
import presentacion.command.gui.MostrarGuiCommand;
import presentacion.command.marca.BorrarMarcaCommand;
import presentacion.command.marca.BuscarMarcaCommand;
import presentacion.command.marca.ModificarMarcaCommand;
import presentacion.command.marca.MostrarCaloriasMediasDeMarcaCommand;
import presentacion.command.marca.MostrarMarcasCommand;
import presentacion.command.marca.MostrarProductosPorMarcaCommand;
import presentacion.command.marca.RegistrarMarcaCommand;
import presentacion.command.pase.BorrarPaseCommand;
import presentacion.command.pase.BuscarPaseCommand;
import presentacion.command.pase.ModificarPaseCommand;
import presentacion.command.pase.MostrarListaPaseCommand;
import presentacion.command.pase.MostrarPorPeliculaPaseCommand;
import presentacion.command.pase.RegistrarPaseCommand;
import presentacion.command.pelicula.BorrarPeliculaCommand;
import presentacion.command.pelicula.BuscarPeliculaCommand;
import presentacion.command.pelicula.ModificarPeliculaCommand;
import presentacion.command.pelicula.MostrarListaPeliculaCommand;
import presentacion.command.pelicula.MostrarPorFechaPeliculaCommand;
import presentacion.command.pelicula.PeliculasMasVendidasCommand;
import presentacion.command.pelicula.RegistrarPeliculaCommand;
import presentacion.command.producto.BorrarProductoCommand;
import presentacion.command.producto.BuscarProductoCommand;
import presentacion.command.producto.ModificarProductoCommand;
import presentacion.command.producto.MostrarProductosCommand;
import presentacion.command.producto.MostrarProductosConMasDeXStockCommand;
import presentacion.command.producto.RegistrarProductoCommand;
import presentacion.command.proveedor.BorrarProveedorCommand;
import presentacion.command.proveedor.BuscarProveedorCommand;
import presentacion.command.proveedor.ModificarProveedorCommand;
import presentacion.command.proveedor.MostrarProveedoresCommand;
import presentacion.command.proveedor.RegistrarProveedorCommand;
import presentacion.controller.Evento;

public class CommandFactoryImpl extends CommandFactory {
	
	public Command getCommand(Evento event) {
		Command command = null;
		
		switch(event) {
			case REGISTRAR_PELICULA: command = new RegistrarPeliculaCommand(); break; 
			case MODIFICAR_PELICULA: command = new ModificarPeliculaCommand(); break;
			case BORRAR_PELICULA: command = new BorrarPeliculaCommand(); break; 
			case BUSCAR_PELICULA: command = new BuscarPeliculaCommand(); break;
			case MOSTRAR_LISTA_PELICULA: command = new MostrarListaPeliculaCommand(); break;
			case MOSTRAR_POR_FECHA_PELICULA: command = new MostrarPorFechaPeliculaCommand(); break;
			case PELICULAS_MAS_VENDIDAS: command = new PeliculasMasVendidasCommand(); break;
			
			case REGISTRAR_PASE: command = new RegistrarPaseCommand(); break;
			case MODIFICAR_PASE: command = new ModificarPaseCommand(); break;
			case BORRAR_PASE: command = new BorrarPaseCommand(); break;
			case BUSCAR_PASE: command = new BuscarPaseCommand(); break;
			case MOSTRAR_LISTA_PASE: command = new MostrarListaPaseCommand(); break;
			case MOSTRAR_POR_PELICULA_PASE: command = new MostrarPorPeliculaPaseCommand(); break;
			
			case REGISTRAR_EMPLEADO: command = new RegistrarEmpleadoCommand(); break;
			case MODIFICAR_EMPLEADO: command = new ModificarEmpleadoCommand(); break;
			case BORRAR_EMPLEADO: command = new BorrarEmpleadoCommand(); break;
			case BUSCAR_EMPLEADO: command = new BuscarEmpleadoCommand(); break;
			case MOSTRAR_LISTA_EMPLEADO: command = new MostrarListaEmpleadoCommand(); break;
			case MOSTRAR_POR_JORNADA_EMPLEADO: command = new MostrarPorJornadaEmpleadoCommand(); break;
			
			case ABRIR_FACTURA: command = new AbrirFacturaCommand(); break;
			case AÑADIR_PASE: command = new AnadirPaseFacturaCommand(); break;
			case BUSCAR_FACTURA: command = new BuscarFacturaCommand(); break;
			case CERRAR_FACTURA: command = new CerrarFacturaCommand(); break;
			case DEVOLVER_PASE: command = new DevolverPaseFacturaCommand(); break;
			case MOSTRAR_LISTA_FACTURA: command = new MostrarListaFacturaCommand(); break;
			case QUITAR_PASE: command = new QuitarPaseFacturaCommand(); break;
			
			// JPA
			case REGISTRAR_MARCA: command = new RegistrarMarcaCommand(); break;
			case MODIFICAR_MARCA: command = new ModificarMarcaCommand(); break;
			case BORRAR_MARCA: command = new BorrarMarcaCommand(); break;
			case BUSCAR_MARCA: command = new BuscarMarcaCommand(); break;
			case MOSTRAR_MARCAS: command = new MostrarMarcasCommand(); break;
			case MOSTRAR_CALORIAS_MEDIAS_DE_MARCA: command = new MostrarCaloriasMediasDeMarcaCommand(); break;
			case MOSTRAR_PRODUCTOS_POR_MARCA: command = new MostrarProductosPorMarcaCommand(); break;
			
			case REGISTRAR_PROVEEDOR: command = new RegistrarProveedorCommand(); break;
			case MODIFICAR_PROVEEDOR: command = new ModificarProveedorCommand(); break;
			case BORRAR_PROVEEDOR: command = new BorrarProveedorCommand(); break;
			case BUSCAR_PROVEEDOR: command = new BuscarProveedorCommand(); break;
			case MOSTRAR_PROVEEDORES: command = new MostrarProveedoresCommand(); break;
			
			case REGISTRAR_PRODUCTO: command = new RegistrarProductoCommand(); break;
 			case MODIFICAR_PRODUCTO: command = new ModificarProductoCommand(); break;
			case BORRAR_PRODUCTO: command = new BorrarProductoCommand(); break;
			case BUSCAR_PRODUCTO: command = new BuscarProductoCommand(); break;
			case MOSTRAR_PRODUCTOS: command = new MostrarProductosCommand(); break;
			//case MOSTRAR_PRODUCTO_MAS_CALORICO: command = new MostrarProductoMasCaloricoCommand(); break;
			case MOSTRAR_PRODUCTOS_CON_MAS_DE_X_STOCK: command = new MostrarProductosConMasDeXStockCommand(); break;
			
			case ABRIR_FACTURA_TIENDA: command = new AbrirFacturaTiendaCommand(); break;
 			case AÑADIR_PRODUCTO: command = new AnadirProductoCommand(); break;
			case QUITAR_PRODUCTO: command = new QuitarProductoCommand(); break;
			case CERRAR_FACTURA_TIENDA: command = new CerrarFacturaTiendaCommand(); break;
			case DEVOLVER_PRODUCTO: command = new DevolverProductoCommand(); break;
			case BUSCAR_FACTURA_TIENDA: command = new BuscarFacturaTiendaCommand(); break;
			case LISTAR_FACTURAS_TIENDA: command = new ListarFacturasTiendaCommand(); break;			
			
			// añadir todos los case de mostrar gui en cascada
			case MOSTRAR_INICIO: command = new MostrarGuiCommand(event); break;
			
			default: command = null; break;
		}
		
		return command;
	}
}
