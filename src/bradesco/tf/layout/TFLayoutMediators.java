package bradesco.tf.layout;

import bradesco.tf.mediator.ILayoutEventMediatorTF;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class TFLayoutMediators {
	private static final TFLayoutMediators INSTANCE = new TFLayoutMediators();
	
	private ILayoutEventMediatorTF<Boolean> commonBotaoRemoverHabilitacao;
	
	private TFLayoutMediators(){
		
	}
	
	public static final synchronized TFLayoutMediators getInstance(){
		return INSTANCE;
	}

	public ILayoutEventMediatorTF<Boolean> getCommonBotaoRemoverHabilitacao() {
		if(null== commonBotaoRemoverHabilitacao){
			commonBotaoRemoverHabilitacao = new CommonBotaoRemoverHabilitacaoMediator();
		}
		
		return commonBotaoRemoverHabilitacao;
	}
	
}
