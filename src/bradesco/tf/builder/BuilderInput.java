package bradesco.tf.builder;


/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class BuilderInput {

	private Object type;
	
	private Object id;
	private Object target;
	
	public BuilderInput(){
		
	}
	
	public BuilderInput(final Object type, final Object id, final Object target){
		setTarget(target);
		setId(id);
		setTarget(target);
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
}
