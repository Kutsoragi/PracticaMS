package negocio.pase;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import integracion.factoria_dao.DAOFactoria;
import integracion.pase.DAOPase;
import integracion.pelicula.DAOPelicula;
import integracion.transactions.Transaction;
import integracion.transactions.TransactionManager;
import negocio.pelicula.TPelicula;


public class SAPaseImpl implements SAPase {

	public int registrarPase(TPase pase) throws Exception {
		
		int respuesta = 0;
		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		DAOPelicula daoPeli = DAOFactoria.getInstancia().generarDAOPelicula();
		TPase paseLeido = daoPase.buscarPasePorID(pase.getID());
		TPelicula peliLeida = daoPeli.buscarPeliculaPorID(pase.getPelicula());
		
		try{
			verificarFecha(pase.getFecha());
			verificarHora(pase.getHoraInicio(), "inicio");
			verificarHora(pase.getHoraFin(), "finalizacion");
			if (pase.getHoraInicio().trim().compareTo(pase.getHoraFin().trim()) >= 0) throw new IllegalArgumentException("Hora Inicio no puede ser mayor o igual que Hora Fin.");
			if (peliLeida != null){
				if (daoPase.franjaValida(pase)){
					if (paseLeido == null){
						respuesta = daoPase.registrarPase(pase);
						transaction.commit();
					}else throw new IllegalArgumentException("Ya existe el pase");
				}
				else throw new IllegalArgumentException("No puede haber dos pases en la misma sala en un mismo horario");
			}
			else throw new IllegalArgumentException("No existe la pelicula");
		}
		catch (IllegalArgumentException e){
			transaction.rollback();
			throw e;
		}

		return respuesta;
	}
	
	public int borrarPase(int id) throws Exception {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		
		int respuesta = 0;
		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();

		
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		TPase paseLeido = daoPase.buscarPasePorID(id);
		
		if (paseLeido != null && paseLeido.isActivo()){
			respuesta = daoPase.borrarPase(id);
			transaction.commit();
		}
		else{
			transaction.rollback();
		}

		return respuesta;	
	}

	public TPase buscarPasePorID(int id) throws Exception {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		
		TPase pase = null;
		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();

		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		
		pase = daoPase.buscarPasePorID(id);

		if(pase != null && pase.isActivo()){
			transaction.commit();
		}else{
			pase = null;
			transaction.rollback();
		}

		return pase;	
	}
	
	public int modificarPase(TPase pase) throws Exception {
		
		
		int respuesta = 0;
		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		TPase paseLeido = daoPase.buscarPasePorID(pase.getID());
		DAOPelicula daoPeli = DAOFactoria.getInstancia().generarDAOPelicula();
		TPelicula peliLeida = daoPeli.buscarPeliculaPorID(pase.getPelicula());
		
		try{
			verificarFecha(pase.getFecha());
			verificarHora(pase.getHoraInicio(), "inicio");
			verificarHora(pase.getHoraFin(), "finalizacion");
			if (pase.getHoraInicio().trim().compareTo(pase.getHoraFin().trim()) >= 0) throw new IllegalArgumentException("Hora Inicio no puede ser mayor o igual que Hora Fin.");
			if(peliLeida != null){
				if (paseLeido != null && paseLeido.isActivo()){
					if (daoPase.franjaValida(pase)){
						respuesta = daoPase.modificarPase(pase);
						transaction.commit();
					}
					else throw new IllegalArgumentException("No puede haber dos pases en la misma sala en un mismo horario");
				}
				else throw new IllegalArgumentException("No existe el pase introducido");
			}
			else throw new IllegalArgumentException("No existe la pelicula");
		}
		catch (IllegalArgumentException e){
			transaction.rollback();
			throw e;
		}
		
		return respuesta;	
	}

	public List<TPase> mostrarListaPases() throws Exception {
		
		List<TPase> respuesta = null;
		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		
		
		respuesta = daoPase.mostrarListaPases();
		
		if(respuesta != null){
			transaction.commit();
		}
		else{
			transaction.rollback();
		}

		return respuesta;
	}

	public List<TPase> mostrarPasesPorPelicula(int idPelicula) throws Exception {
		if (idPelicula < 1) throw new IllegalArgumentException("ID incorrecto.");
		
		List<TPase> respuesta = null;
		
		Transaction transaction = TransactionManager.getInstance().newTransaction();
		transaction.start();
		
		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		TPelicula peliculaLeida = daoPelicula.buscarPeliculaPorID(idPelicula);
		if (peliculaLeida != null && peliculaLeida.isActivo()) {
			DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
			respuesta = daoPase.mostrarPasesPorPeliculas(idPelicula);
			
			if(respuesta != null){
				transaction.commit();
			}else{
				transaction.rollback();
			}
		}
		else{
			transaction.rollback();
		}

		return respuesta;	
	}
	
	private void verificarFecha(String fecha) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
			format.parse(fecha);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Fecha incorrecta. Formato fecha: " + "YYYY-MM-DD" + ".");
		}
	}
	
	private void verificarHora(String hora, String tipoHora) {
		if (!hora.trim().matches("(?:[0-1]\\d|2[0-3]):[0-5]\\d")) throw new IllegalArgumentException("Hora de " + tipoHora + " incorrecta. Formato hora: HH:MM (24H).");
	}
}