package integracion.transactions;

import java.sql.Connection;

public interface Transaction {
	
	void start();
	void commit();
	void rollback();
	Connection getResource();
}