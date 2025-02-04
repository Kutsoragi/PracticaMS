package integracion.transactions;

public abstract class TransactionManager {
	
	private static TransactionManager instance;

	public static TransactionManager getInstance() {
		if (instance == null) {
			instance = new TransactionManagerImpl();
		}
		return instance;
	}

	public abstract Transaction newTransaction() throws Exception;
	public abstract void removeTransaction();
	public abstract Transaction getTransaction();
}