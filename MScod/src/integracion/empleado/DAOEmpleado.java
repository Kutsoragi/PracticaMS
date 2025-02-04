package integracion.empleado;

import java.util.LinkedList;

import negocio.empleado.TEmpleado;

public interface DAOEmpleado {

	int registrarEmpleado(TEmpleado tEmpleado);
	int borrarEmpleado(int id);
	void modificarEmpleado(TEmpleado tEmpleado);
	TEmpleado buscarEmpleadoPorID(int id);
	LinkedList<TEmpleado> mostrarListaEmpleados();
	public TEmpleado buscarEmpleadoPorDNI(String dni);
	public LinkedList<TEmpleado> mostrarEmpleadosPorJornada(boolean esCompleta);
}
