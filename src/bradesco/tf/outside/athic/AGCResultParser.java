package bradesco.tf.outside.athic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REGEX;
import com.javaf.javase.text.ParsingException;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class AGCResultParser {

	public List<AGCResultDTO> parse(final URL csv) throws IOException, ParsingException {
		final List<AGCResultDTO> _result = new ArrayList<AGCResultDTO>();

		final LineNumberReader _reader = new LineNumberReader( new BufferedReader(new InputStreamReader(csv.openStream())) );
		String _line = null;
		
		try{
			while(null!= (_line = _reader.readLine()) ){
				final String[] _places = _line.split(REGEX.PONTO_E_VIRGULA);
				if(_places.length >= INTEGER._3){
					final AGCResultDTO _agc = new AGCResultDTO();
					_agc.setIcor(         _places[INTEGER._0]);
					_agc.setIdentificador(_places[INTEGER._1]);
					_agc.setNome(         _places[INTEGER._2]);
					
					_result.add(_agc);
					
				} else {
					throw new ParsingException("Sintaxe inválida na linha " + _reader.getLineNumber() + " (icor;numero identificador do documento; nome do documento): " + _line);
				}
			}
			
		}finally{
			_reader.close();
		}
		
		return _result;
	}
}
