package bradesco.tf.outside.cadu;

import java.math.BigDecimal;
import java.util.List;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IArquitecturalActions;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.BradescoConstants;
import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.UtilIdentify;
import bradesco.tf.TFConstants.CADUV2;
import bradesco.tf.TFConstants.SESSION;
import bradesco.tf.dsn.UtilDSN;

import com.javaf.ObjectPool;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.brazil.Pessoa;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.Localization;
import com.javaf.pattern.ICommand2;

/**
 * Invocação do CADU V2, enviando os dados de entrada e tratamento do retorno.
 * @author fabiojm - Fábio José de Moraes
 */
@SuppressWarnings("deprecation")
public final class CaduV2Calling {

	private final UtilDSN util                   = UtilDSN.getInstance();
	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private final ILogging logging               = Logging.loggerOf(getClass());
	private final UtilIdentify identify          = UtilIdentify.getInstance();
	private final UtilFormat format              = UtilFormat.getInstance();
	
	public CaduV2Calling(){

	}
	
	public IContainerObject update(final CaduV2CallingDTO input, final IArquitecturalActions actions, final IDataServiceNode node, final IEventsServiceProvider provider, final IOperation operation){
		IContainerObject _result = null;
		
		logging.debug(Localization.getInstance().localize(I18N.CADU_LOG_INICIANDO_CHAMADA));
		final ObjectPool _opool        = arquitetura.getObjectPool(provider);
		final IContainerObject _input  = util.getOrAddOpCallersInput(node, CADUV2.NODE_OP);
		final IContainerObject _output = util.getOrAddOpCallersOutput(node, CADUV2.NODE_OP);
		
		util.getOrAddSimpleObject(CADUV2.NODE_SISTEMA, _input).setValue(input.getSistema());
		util.getOrAddSimpleObject(CADUV2.NODE_EXTERNO, _input).setBooleanValue(input.isExterno());
		util.getOrAddSimpleObject(CADUV2.NODE_IDENTIFICADO, _input).setBooleanValue(Boolean.TRUE);
		util.getOrAddSimpleObject(CADUV2.NODE_PERMITIR_CADASTRO, _input).setBooleanValue(Boolean.FALSE);
		util.getOrAddSimpleObject(CADUV2.NODE_NAVEGACAO_CADASTRO, _input).setBooleanValue(Boolean.FALSE);
		util.getOrAddSimpleObject(CADUV2.NODE_TELA, _input).setIntValue(input.getTela());
		
		//obter a classe cadastral configurada e passar para chamada do cadu, caso seja o cpf seja incluido 
		util.getOrAddSimpleObject(CADUV2.NODE_PRODUTO, _input).setValue(_opool.get(identify.doPsdcProduto(), String.class));
		util.getOrAddSimpleObject(CADUV2.NODE_PARTICIPACAO, _input).setValue(CADUV2.CLASSE_CADASTRAL_PARTICIPACAO_DEFAULT);
		
		util.getOrAddSimpleObject(CADUV2.NODE_EMPRESA, _input).setValue(BradescoConstants.BANCO_BRADESCO_PJ);
		
		//identificado pelo club
		util.getOrAddSimpleObject(CADUV2.NODE_CLUB_INPUT, _input).setBigDecimalValue(new BigDecimal(input.getPessoa().getClub()));
		
		logging.debug(STRING.NEW_LINE + util.toString(_input));
		actions.executeOperation(node, CADUV2.CALL, Boolean.TRUE);
		logging.debug(STRING.NEW_LINE + util.toString(_output));
		_result = _output;
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private CaduV2CallingDTO invoke(final CaduV2CallingDTO input, final IArquitecturalActions actions, final IDataServiceNode node, final IEventsServiceProvider provider, final IOperation operation) throws InvokeException, CaduV2CallingNotIdentifiedException, CaduV2CallingInvalidTypeException {
		
		logging.debug(Localization.getInstance().localize(I18N.CADU_LOG_INICIANDO_CHAMADA));
		final ObjectPool _opool        = arquitetura.getObjectPool(provider);
		final IContainerObject _input  = util.getOrAddOpCallersInput(node, CADUV2.NODE_OP);
		final IContainerObject _output = util.getOrAddOpCallersOutput(node, CADUV2.NODE_OP);
		
		util.getOrAddSimpleObject(CADUV2.NODE_SISTEMA, _input).setValue(input.getSistema());
		util.getOrAddSimpleObject(CADUV2.NODE_EXTERNO, _input).setBooleanValue(input.isExterno());
		util.getOrAddSimpleObject(CADUV2.NODE_PERMITIR_CADASTRO, _input).setBooleanValue(input.isPermitirCadastro());
		util.getOrAddSimpleObject(CADUV2.NODE_TELA, _input).setIntValue(input.getTela());
		
		//obter a classe cadastral configurada e passar para chamada do cadu, caso seja o cpf seja incluido 
		util.getOrAddSimpleObject(CADUV2.NODE_PRODUTO, _input).setValue(_opool.get(identify.doPsdcProduto(), String.class));
		util.getOrAddSimpleObject(CADUV2.NODE_PARTICIPACAO, _input).setValue(CADUV2.CLASSE_CADASTRAL_PARTICIPACAO_DEFAULT);;
		
		logging.debug(STRING.NEW_LINE + util.toString(_input));
		actions.executeOperation(node, CADUV2.CALL, Boolean.TRUE);
		logging.debug(STRING.NEW_LINE + util.toString(_output));
		
		try{
			// verificar se existem dados na saída.
			if(util.getOrAddSimpleObject(CADUV2.NODE_IDENTIFICADO, _output, Boolean.FALSE).getBooleanValue()){
				input.getPessoa().setClub(util.getOrAddSimpleObject(CADUV2.NODE_CLUB_OUTPUT, _output, INTEGER._0).getBigDecimalValue().longValue());
				input.getPessoa().setNome(util.getOrAddSimpleObject(CADUV2.NODE_NOME_OUTPUT, _output, STRING.EMPTY).getValue());
				
				logging.debug(Localization.getInstance().localize(I18N.CADU_LOG_FINALIZANDO_CHAMADA_SUCESSO, input.getPessoa().getClub()) );
				
				/*try{
					//detalhando a pessoa
					final PessoaDetalharOCBM _ocbm = new PessoaDetalharOCBM(input.getPessoa());
					fwo.invoke(_ocbm, new DataServiceNodeProvider<Pessoa>(), provider, node, operation);
					
					//copiar de volta os valores simples
					UtilReflection.getInstance().plainCopy(_ocbm, input.getPessoa());
					
					final PessoaType _type = input.getWrapper().get(CADUV2.TIPO_PESSOA, PessoaType.class);
					if(null!= _type){
						if(!_type.equals(input.getPessoa().getDocumento().getTipo())){
							throw new CaduV2CallingInvalidTypeException();
						}
					}
					
				}catch(UnsuccessfulException _e){
					throw new InvokeException(_e.getMessage(), _e);
				}*/
				
				boolean _continue = Boolean.TRUE;
				final boolean _isCondicional = format.toBoolean(input.getWrapper().get(CADUV2.CONDICIONADO));
				
				if(_isCondicional){
					_continue = !input.getPessoa().equals( input.getWrapper().get(CADUV2.TO_COMPARE) ); 
				}
				
				//em caso positivo e o cadastro estiver incompleto no cadu, chama tela componente para atualização de dados
				/*final boolean _update = input.getWrapper().get(CADUV2.UPDATE_ON_INCOMPLETE, Boolean.class, Boolean.FALSE);
				boolean _incomplete   = Boolean.FALSE;
				do{
					//configurado para validar a capacidade
					if(_continue &&
							input.isValidarCapacidade()){
						
						_incomplete = Boolean.TRUE;
						
						final PessoaValidarCapacidadeCivilOCBM _ocbm = new PessoaValidarCapacidadeCivilOCBM(input.getPessoa());
						
						//obter produto e participacao configurados no pool de objetos
						_ocbm.setProduto(_opool.get(identify.doPsdcProduto(), String.class));
						_ocbm.setParticipacao(_opool.get(identify.doPsdcParticipacao(), String.class));
						_ocbm.setRepresentacao(_opool.get(identify.doPsdcRepresentacao(), String.class));
						try{
							//validar capacidade civil - maior ou emancipado
							fwo.invoke(_ocbm, new DataServiceNodeProvider<PessoaValidarCapacidadeCivilOCBM>(), provider, node, operation);
							
							//cadastro completo
							_incomplete = Boolean.FALSE;
						}catch(UnsuccessfulException _e){
							//caso não seja cadastro incompleto, lança exceção
							if(!FWO.MESSAGE.CADASTRO_INCOMPLETO.equals(_e.getData())){
								throw new InvokeException(_e.getMessage(), _e);
								
							} else if(_update) {
								
								if(MESSAGE.SIM== show.yesnot(node, I18N.DESEJA_COMPLETAR_CADASTRO)){
									//caso contrário e configurado para atualizar dados na tela componente, invocar tela do CaduV2 para complementar dados
									update(input, actions, node, provider, operation);
	
								} else {
									throw new InvokeException(_e.getMessage(), _e);
								}
								
							} else {
								throw new InvokeException(_e.getMessage(), _e);
							}
						}
					}
					
					//enquando cadastro incompleto
				}while(_incomplete);*/
				
				if(_continue){
					//executar os commands configurados
					final List<ICommand2<? extends Pessoa>> _commands = input.getAfterID();
					for(ICommand2 _command : _commands){
						_command.execute(input.getPessoa());
					}
					
					//guardar no pool de objetos da sessão do usuário a instancia da chamada
					arquitetura.getObjectPool(provider).put(SESSION.CADUV2_CALL, input);
				}
			} else {
				logging.debug(Localization.getInstance().localize(I18N.CADU_LOG_FINALIZANDO_CHAMADA_NAO_IDENT));
				
				final boolean _isThrow = format.toBoolean(input.getWrapper().remove(CADUV2.THROW_WHEN_NOT_IDENTIFIED), Boolean.TRUE);
				if(_isThrow){
					throw new CaduV2CallingNotIdentifiedException(Localization.getInstance().localize(I18N.CADU_CLIENTE_NAO_IDENT_CAD));
				}
			}
		}finally{
			//limpar area de chamada
			util.removeOpCallers(node);
		}
		
		return input;
	}
	
	/**
	 * Invocar CADU V2.
	 * 
	 * @param input Conjunto de dados para entrada na chamada.
	 * @param provider Provedor de serviços.
	 * @return Instância preenchida com dados obtidos no CADU V2.
	 */
	public CaduV2CallingDTO invoke(final CaduV2CallingDTO input, final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation) throws InvokeException{
		return invoke(input, provider.getArquitecturalActions(), node, provider, operation);
	}
	
	
	/**
	 * Invocar CADU V2 com dados de entrada padrão.<br/>
	 * Utilize-a para funcionalidades onde não se tem dado algum da Pessoa.
	 * @param provider Provedor de serviços.
	 * @return Nova instância preenchida com dados obtidos no CADU V2.
	 */
	public CaduV2CallingDTO invoke(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation) throws InvokeException{
		
		final CaduV2CallingDTO _input = new CaduV2CallingDTO();
		return invoke(_input, provider, node, operation);
	}
}
