package bradesco.tf.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PropertiesPlaceholderVisitor extends TFVisitor<Properties, Properties> {
	
	private final List<Visitor<Properties, Properties>> visitors = new ArrayList<Visitor<Properties,Properties>>();
	
	public PropertiesPlaceholderVisitor(){
		visitors.add(new PropertiesBradescoBrowserURLVisitor());
		visitors.add(new PropertiesBranchPlaceholderVisitor());
		visitors.add(new PropertiesSystemPlaceholderVisitor());
		visitors.add(new PropertiesPropertyPlaceholderVisitor());
	}
	
	public Properties visit(final Properties properties) {

		for(Visitor<Properties, Properties> _visitor : visitors){
			_visitor.visit(properties);
		}
		
		return properties;
	}

}
