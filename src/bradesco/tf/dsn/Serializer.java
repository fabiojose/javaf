package bradesco.tf.dsn;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface Serializer {

	void write() throws IllegalStateException;
	void read() throws IllegalStateException;
	
}
