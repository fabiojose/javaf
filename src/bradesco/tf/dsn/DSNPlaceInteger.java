package bradesco.tf.dsn;

import br.com.bradesco.core.aq.dataservice.ISimpleObject;

import com.javaf.Constants.INTEGER;
import com.javaf.model.ValuePlace;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DSNPlaceInteger extends ValuePlace<Integer> {

	private ISimpleObject simple;
	public DSNPlaceInteger(final String label, final ISimpleObject simple){
		setLabel(label);
		this.simple = simple;
	}

	public Integer getValue() {

		return simple.getIntValue();
	}
	
	public void setValue(final Integer value){
		simple.setIntValue(value);
	}

	public void reset(){
		simple.setIntValue(INTEGER._0);
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
		final DSNPlaceInteger other = (DSNPlaceInteger) obj;
		if (simple == null) {
			if (other.simple != null)
				return false;
		} else if (!simple.getFullKey().equals(other.simple.getFullKey()))
			return false;
		return true;
	}
	
	
}
