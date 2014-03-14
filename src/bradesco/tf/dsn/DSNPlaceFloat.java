package bradesco.tf.dsn;

import com.javaf.Constants.DOUBLE;
import com.javaf.model.ValuePlace;

import br.com.bradesco.core.aq.dataservice.ISimpleObject;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DSNPlaceFloat extends ValuePlace<Float> {

	private ISimpleObject simple;
	public DSNPlaceFloat(final String label, final ISimpleObject simple){
		setLabel(label);
		this.simple = simple;
	}
	
	@Override
	public Float getValue() {
		return simple.getFloatValue();
	}
	
	public void setValue(final Float value){
		simple.setFloatValue(value);
	}

	@Override
	public void reset() {
		simple.setFloatValue((float)DOUBLE._0);		
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
		final DSNPlaceFloat other = (DSNPlaceFloat) obj;
		if (simple == null) {
			if (other.simple != null)
				return false;
		} else if (!simple.getFullKey().equals(other.simple.getFullKey()))
			return false;
		return true;
	}

	
}
