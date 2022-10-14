package datastruct;

public interface Table<T,E extends Comparable<E>>{
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public T select ( E key ) ;
	
	/**
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public boolean insert ( E key, T data ) ;
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean delete ( E key ) ;

	
}
