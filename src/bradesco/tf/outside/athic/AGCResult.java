package bradesco.tf.outside.athic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.javaf.Constants.STRING;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class AGCResult extends ATHICResult {

	private URL csv;
	private List<AGCResultDTO> data;

	void setCsv(final URL csv){
		this.csv = csv;
	}
	
	public URL getCsv() {
		return csv;
	}

	public List<AGCResultDTO> getData() {
		if(null== data){
			data = new ArrayList<AGCResultDTO>();
		}
		return data;
	}

	void setData(List<AGCResultDTO> data) {
		this.data = data;
	}
	
	public String toString(){
		return super.toString() + STRING.SPACE1 + getData().toString();
	}
}
