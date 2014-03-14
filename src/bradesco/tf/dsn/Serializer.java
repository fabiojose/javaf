package bradesco.tf.dsn;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public interface Serializer {

	void write() throws IllegalStateException;
	void read() throws IllegalStateException;
	
}
