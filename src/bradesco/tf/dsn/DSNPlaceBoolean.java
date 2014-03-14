package bradesco.tf.dsn;

import com.javaf.model.ValuePlace;

import br.com.bradesco.core.aq.dataservice.ISimpleObject;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class DSNPlaceBoolean extends ValuePlace<Boolean> {

	private ISimpleObject simple;
	public DSNPlaceBoolean(final String label, final ISimpleObject simple){
		setLabel(label);
		this.simple = simple;
	}
	
	public Boolean getValue() {
		return simple.getBooleanValue();
	}

	public void reset() {
		simple.setBooleanValue(Boolean.FALSE);
	}
	
	public String getName(){
		return simple.getFullKey();
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((simple == null) ? 0 : simple.getFullKey().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DSNPlaceBoolean other = (DSNPlaceBoolean) obj;
		if (simple == null) {
			if (other.simple != null)
				return false;
		} else if (!simple.getFullKey().equals(other.simple.getFullKey()))
			return false;
		return true;
	}
	
	

}
