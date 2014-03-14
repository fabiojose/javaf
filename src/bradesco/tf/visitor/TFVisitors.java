package bradesco.tf.visitor;

import bradesco.tf.TransferBeanWrapper;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class TFVisitors {
	private static final TFVisitors INSTANCE = new TFVisitors();
	
	private Visitor<ObjectPool, Boolean> showMessage;
	private Visitor<TransferBeanWrapper, Boolean> maisResultados;
	private Visitor<TransferBeanWrapper, Boolean> datastoreReadHasMoreData;
	private Visitor<ObjectPool, Void> runtoDoNot;
	private Visitor<ObjectPool, Boolean> executeRunto;
	
	private Visitor<ObjectPool, Void> pesquisarPlain;
	private Visitor<ObjectPool, Void> pesquisarDoNot;
	private Visitor<ObjectPool, Boolean> executePesquisar;
	
	private TFVisitors(){
		
	}
	
	public static final synchronized TFVisitors getInstance(){
		return INSTANCE;
	}

	public Visitor<ObjectPool, Boolean> getShowMessage() {
		if(null== showMessage){
			showMessage = new ShowMessageVisitor();
		}
		
		return showMessage;
	}

	public Visitor<ObjectPool, Void> getPesquisarPlain() {
		if(null== pesquisarPlain){
			pesquisarPlain = new PesquisarPlainVisitor();
		}
		
		return pesquisarPlain;
	}

	public Visitor<ObjectPool, Void> getPesquisarDoNot() {
		if(null== pesquisarDoNot){
			pesquisarDoNot = new PesquisarDoNotExecuteVisitor();
		}
		
		return pesquisarDoNot;
	}

	public Visitor<ObjectPool, Boolean> getExecutePesquisar() {
		if(null== executePesquisar){
			executePesquisar = new ExecutePesquisarVisitor();
		}
		
		return executePesquisar;
	}

	public Visitor<TransferBeanWrapper, Boolean> getMaisResultados() {
		if(null== maisResultados){
			maisResultados = new MaisResultadosVisitor();
		}
		return maisResultados;
	}

	public Visitor<ObjectPool, Boolean> getExecuteRunto() {
		if(null== executeRunto){
			executeRunto = new ExecuteRuntoVisitor();
		}
		
		return executeRunto;
	}

	public Visitor<ObjectPool, Void> getRuntoDoNot() {
		if(null== runtoDoNot){
			runtoDoNot = new RuntoDoNotExecuteVisitor();
		}
		
		return runtoDoNot;
	}

	public Visitor<TransferBeanWrapper, Boolean> getDatastoreReadHasMoreData() {
		if(null== datastoreReadHasMoreData){
			datastoreReadHasMoreData = new DatastoreReadHasMoreDataVisitor();
		}
		return datastoreReadHasMoreData;
	}

}
