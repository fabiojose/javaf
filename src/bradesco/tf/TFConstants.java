package bradesco.tf;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bradesco.tf.cases.BranchPropertyCodeCase;
import bradesco.tf.cases.BranchPropertyServerCase;
import bradesco.tf.cases.BranchPropertyUserCase;
import bradesco.tf.outside.cadu.CaduV2CallingDTO;
import bradesco.tf.text.BooleanDSNViewFormat;
import bradesco.tf.validate.Loader;

import com.javaf.Application;
import com.javaf.Constants;
import com.javaf.ObjectPool;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.PLACE_HOLDER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.STRING;
import com.javaf.model.Attribute;
import com.javaf.pattern.ICase;
import com.javaf.pattern.factory.FactoryMethodFactory;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class TFConstants {
	
	private TFConstants(){
		
	}
	
	public static final class APPLICATION {
	
		static final Map<Object, OPERATION> OPERATIONS = new HashMap<Object, OPERATION>();
		
	}
	
	public static final class FACTORY {
		public static final IFactory FACTORY_INSTANCE_ORCHESTRATOR = new FactoryMethodFactory(InstanceOrchestrator.class.getName(), REFLECTION.METHOD_GET_INSTANCE, REFLECTION.NO_ARGS);
	}
	
	public static final class AMBIENTE {
		public static final String ATHIC_SCANNER         = "athic.scanner";
		public static final String ATHIC_URLCSV          = "athic.urlcsv";
		public static final String ATHIC_BPO_PRODUCT_PV  = "athic.BPO.product.PV";
		public static final String ATHIC_BPO_PRODUCT_NC  = "athic.BPO.product.NC";
		public static final String ATHIC_AGENCIA_PRODUCT = "athic.AGC.product";
		
		public static final String DEVELOPMENT_TIME = "1";
		
		public static final String RUNTIME  = "runtime";
		public static final String SYSTEM   = "system";
		public static final String SYSTEM_PROPERTY = "Ambiente";
	}

	/**
	 * Expõe constantes pessoais à identificação de instâncias armazenadas no cache da VM.
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class IDENTIFY {
		public static final String INSTANCE_ORCHESTRATOR = "CURRENT_INSTANCE_ORCHESTRATOR";
		
		public static final String KEY_FWO_CALLING     = ".FWO_CALLING.";
		public static final String KEY_TYPE            = ".type";
		public static final String KEY_PESQUISAR       = ".pesquisar";
		public static final String KEY_PREFIXO         = ".prefixo";
		public static final String KEY_VALIDADOR       = ".validador";
		public static final String KEY_RUNNING         = ".running";
		public static final String KEY_BINDDING        = ".bindding";
		public static final String KEY_LOCAL_POOL      = ".pool";
		public static final String KEY_HELPER          = ".helper";
		public static final String KEY_EVENT_REPLACERS = ".event.replacers";
		public static final String KEY_EVENT_ON_START          = ".event.onStart";
		public static final String KEY_EVENT_ON_FINISH         = ".event.onFinish";
		public static final String KEY_EVENT_ON_FINISH_COMMAND = ".event.onFinish.command";
		public static final String KEY_DSN_PLACE               = ".dsn.place";
		public static final String KEY_TO_ENABLE               = ".toEnable";
		public static final String KEY_NODE                    = ".node";
		public static final String KEY_EXECUTE_FINALIZAR       = ".execute.finalizar";
		public static final String KEY_EXECUTE                 = ".execute";
		public static final String KEY_LAYOUT_VISIBLE          = ".layout.visible";
		public static final String KEY_LAYOUT_ENABLED          = ".layout.enabled";
		public static final String KEY_LABELS                  = ".label.";
		public static final String KEY_PSDC_REPRESENTACAO      = "psdc.cc.representacao";
		public static final String KEY_SHOT_INSTANCE           = ".shot.instance";
		public static final String KEY_PSDC_PRODUTO            = "psdc.cc.produto";
		public static final String KEY_PSDC_PARTICIPACAO       = "psdc.cc.participacao";
		
		public static final String CONCESSAO_RP   = "concessaorp";
		public static final String CONCESSAO_RL   = "concessaorl";
		public static final String CONCESSAO      = "concessao";
		public static final String DIGITALIZACAO  = "digitalizacao";
		public static final String TIPO_PODER     = "tipopoder";
		public static final String TIPO_DOCUMENTO = "tipodocumento";
		public static final String TIPOS_PODERES  = "tipospoderesoperacionais";
		public static final String GRUPO          = "grupo";
		public static final String REPRESENTANTE  = "representante";
		public static final String PRAZO          = "prazo";
		public static final String P_PRAZO        = "pprazo";
		public static final String HELPER         = "helper";
		public static final String REPROC         = "reproc";
		public static final String SOLICITACAO    = "solicitacao";
		public static final String VINCULO        = "vinculo";
		public static final String PODER          = "poder";
		public static final String SUGESTOES      = "sugestoes";
		public static final String acao           = "acao";
		public static final String FILTRO         = "filtro";
		public static final String INFO           = "info";
		public static final String CONTRATO       = "contrato";
		public static final String ASSIN          = "assin";
		public static final String QUANTIDADE     = "quantidade";
		public static final String DSN_PLACE      = "DSN_PLACE";
		public static final String AGRUPADO       = "agrupado";
		public static final String SELECTED       = "selected";
		public static final String EMAILS         = "emails";
		public static final String SELECTED_VALUE = "selectedValue";
		public static final String AVISO          = "aviso";
		public static final String ANTECEDENCIA   = "antecedencia";
		public static final String AVISAR         = "avisar";
		public static final String EMAIL          = "email";
		public static final String RESULTADO      = "resultado";
		public static final String SITUA          = "situa";
		public static final String PESQUISAR      = "PESQUISAR";
		public static final String NAME           = "name";
		public static final String AGRUPAMENTOS   = "agrupamentos";
		public static final String CLUB           = "club";
		public static final String BY_UORG        = "byUorg";
		public static final String BY             = "by";
		public static final String PROVIDER       = "provider";
		public static final String operation      = "operation";
		public static final String NODE           = "node";
		public static final String DOCUMENTO      = "documento";
		public static final String LABEL          = "label";
		public static final String CADASTRAMENTO  = "cadastramento";
		public static final String FUNDAMENTAL    = "fundamental";
		public static final String DESCRICAO      = "descricao";
		public static final String NUMERO         = "numero";
		
		public static final String SUCCESS = "SUCCESS";
		
		public static final String REPRESENTANTES = "representantes";
		public static final String BATCH = "batch";
		public static final String HINTS = "hints";
		public static final String CONTRATOS = "contratos";
		public static final String PODERES = "poderes";
		public static final String HISTORICO = "historico";
		public static final String DETERMINADA = "determinada";
		public static final String VIGENCIA = "vigencia";
		public static final String PERIODO = "periodo";
		public static final String INICIO = "inicio";
		public static final String FIM = "fim";
		public static final String CLIENTE = "cliente";
		public static final String PESSOA = "pessoa";
		public static final String ENTRIES = "entries";
		public static final String STRING = "string";
		public static final String CODIGO = "codigo";
		public static final String TIPO  = "tipo";
		public static final String SUGESTAO = "sugestao";
		
		public static final String TYPE = "type";
		public static final String LIST = "list";
		public static final String REGISTER = "register";
		public static final String FIELD          = "field";
	}
	
	/**
	 * 
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class FWO {
		public static final String MORE_DATA       = "FWO_CALLING_MORE_DATA";
		public static final String PAGINATION_FLAG = "FWO_CALLING_PAGINATION_FLAG";
		public static final String INSTANCE        = "FWO_CURRENT_INSTANCE";
		
		public static final class MESSAGE {
			public static final String CADASTRO_INCOMPLETO = "FIRP0054";
		}
	}
	
	/**
	 * Expõe constantes gerais sobre o Data Service Node.
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class DSN {
		/**
		 * Nó IContainerObject normalmente utilizado para estrutura de chamadas.
		 */
		public static final String OP_CALLERS = "OP_CALLERS";
		
		/**
		 * Nó IContainerObject utilizado para estrutura de entrada da chamada.
		 */
		public static final String OP_INPUT = "op_input";
		
		/**
		 * Nó IContainerObject utilizado para estrutura de saída da chamada.
		 */
		public static final String OP_OUTPUT = "op_output";
		
		/**
		 * Nó padrão para marcação sobre execução da operação.
		 */
		public static final String DO_EXECUTE = "doExecute";
		
		/**
		 * Nome convencionado do nó onde está a estrutura que representa os dados em um layout.
		 */
		public static final String INSTANCIA_ = "instancia";
		
		/**
		 * Nome convencionado do nó onde reside a estrutura que representa os dados da pesquisa.
		 */
		public static final String PESQUISAR_ = "pesquisar";
		
		/**
		 * Nome convencionado do nó onde reside a estrutura que representa o resultado da pesquisa.
		 */
		public static final String RESULTADO = "resultado";
		
		/**
		 * Nome convencionado do nó onde reside a estrutura que representa as entradas resultantes da pesqrisa
		 */
		public static final String ENTRIES = "entries";
		
		public static final String TABELA = "tabela";
		public static final String INDICES_SELECIONADOS = "indicesSelecionados";
		
		/**
		 * Nome convencionado do nó onde reside a estrutura que representa os indices selecionada de cada entrada resultante.
		 */
		public static final String SELECTED_INDEXES = "selectedIndexes";
		
		/**
		 * Nome convencionado do nó onde reside o índice selecionado em combos normalmente.
		 */
		public static final String SELECTED_INDEX = "selectedIndex";;
		
		public static final String SELECT_LABEL_ITEMS = "selectLabelItems";
		public static final String SELECT_VALUE_ITEMS = "selectValueItems";
		public static final String UI_CONTAINER = "ui";
		public static final String TITLE_CONTAINER = "title";
		public static final String OPERATION = "operation";
		public static final String DEFAULT_LISTBOX_TIPO_DOCUMENTO = "tipo";
		public static final String DEFAULT_LISTBOX_TIPO_PODERES = "tiposPoderesOperacionais";
		
		public static final String FWO_RESULT = "RESULT";
		public static final String FWO_RESPONSE_CODE = "ResponseCode";
		public static final String FWO_MESSAGES = "MESSAGES";
		public static final String FWO_MESSAGES_CODE = "CODE";
		public static final String FWO_MESSAGES_TEXT = "TEXT";
	
		public static final String PATH = "PATH";
		public static final String RUN_TO = "RUN_TO";
		public static final String OUTCOME = "OUTCOME";
	}
	
	/**
	 * Expõe os nomes de camada convencionados
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class LAYER {
		public static final LAYER DSN = new LAYER("DSN");
		
		private String name;
		private LAYER(final String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String toString(){
			return name;
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
			final LAYER other = (LAYER) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}		
	}

	public static final class DEFAULT {
		private static final Map<LAYER, Map<Class<?>, ? extends Object>> MAPPING = new HashMap<LAYER, Map<Class<?>, ? extends Object>>();
		static{
			final Map<Class<?>, String> _dsn = new HashMap<Class<?>, String>();
			MAPPING.put(LAYER.DSN, _dsn);
			
			_dsn.put(Date.class,   STRING.EMPTY);
			_dsn.put(String.class, STRING.EMPTY);
		};
		
		public static Object defaultOf(final LAYER layer, final Class<?> type){
			
			Object _result = null;
			if(null!= type){
				final Map<Class<?>, ? extends Object> _defaults = MAPPING.get(layer);
				if(null!= _defaults){
					_result = _defaults.get(type);
				}
			}
			
			return _result;
		}
		
		/**
		 * Data no formato Bradesco Data Service Node
		 */
		public static final DateFormat DSN_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		
		/**
		 * Valor boolean no formato dsn para VIEW
		 */
		public static final Format DSN_VIEW_BOOLEAN_FORMAT = new BooleanDSNViewFormat();
		
		public static final DecimalFormat BRADESCO_CLUB    = new DecimalFormat("0000000000");
		
		private static final Map<String, Format> MODIFIABLE_FORMATS = new HashMap<String, Format>();
		static{
			MODIFIABLE_FORMATS.put("dd/MM/yyyy", Constants.DEFAULT.ABNT_DATE_FORMAT);
			MODIFIABLE_FORMATS.put("dd/MM/yyyy HH:mm:ss", Constants.DEFAULT.ABNT_DATE_TIME_FORMAT);
		};
		public static final Map<String, Format> FORMATS = Collections.unmodifiableMap(MODIFIABLE_FORMATS);
	}
	
	/**
	 * Expõe constantes para manipulação do retorno de showMessage*
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class MESSAGE {
		public static final int SIM    = 0;
		public static final int NAO    = 1;
		public static final int ESPACE = -1;
		public static final int BOTAO1 = 0;
		public static final int BOTAO2 = 1;
		
		/**
		 * Titulo padrão das mensagens
		 */
		public static final Object DEFAULT_TITLE = Application.getInstance().valueOf(Object.class, Constants.APPLICATION.TITLE_PROPERTY);
	}
	
	/**
	 * Expõe constantes convencionadas para componentes e atributos dos layouts.
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class LAYOUT {
		
		public static final String BOTAO_PESQUISAR       = "botaoPesquisar";
		public static final String BOTAO_CONSULTAR       = "botaoConsultar";
		public static final String BOTAO_INCLUIR         = "botaoIncluir";
		public static final String BOTAO_DETALHAR        = "botaoDetalhar";
		public static final String BOTAO_DETALHAR_1      = "botaoDetalhar" + String.valueOf(INTEGER._1);
		public static final String BOTAO_ALTERAR         = "botaoAlterar";
		public static final String BOTAO_EXCLUIR         = "botaoExcluir";
		public static final String BOTAO_CONFIRMAR       = "botaoConfirmar";
		public static final String BOTAO_MAIS_RESULTADOS = "botaoMaisResultados";
		public static final String BOTAO_MAIS_DADOS      = "botaoMaisDados";
		public static final String BOTAO_COMPLETAR       = "botaoCompletar";
		public static final String BOTAO_VERIFICAR       = "botaoVerificar";
		public static final String BOTAO_PESSOAS         = "botaoPessoas";
		public static final String BOTAO_INCLUIDOS       = "botaoIncluidos";
		public static final String BOTAO_DOCUMENTOS      = "botaoDocumentos";
		public static final String BOTAO_DESBLOQUEAR     = "botaoDesbloquear";
		public static final String BOTAO_SELECIONADOS    = "botaoSelecionados";
		public static final String BOTAO_PODERES         = "botaoPoderes";
		public static final String BOTAO_CONTRATOS       = "botaoContratos";
		public static final String BOTAO_ADICIONAR       = "botaoAdicionar";
		public static final String BOTAO_REMOVER         = "botaoRemover";
		public static final String BOTAO_CONCEDER        = "botaoConceder";
		public static final String GRID_DISPONIVEIS      = "gridDisponiveis";
		public static final String GRID_ADICIONADOS      = "gridAdicionados";
		public static final String INPUT_QUANTIDADE      = "inputQuantidade";
		public static final String PAINEL_BODY           = "painelBody";
		public static final String BOTAO_VISUALIZAR      = "botaoVisualizar";
		public static final String BOTAO_DIGITALIZACAO   = "botaoDigitalizacao";
		public static final String BOTAO_COMPLEMENTO     = "botaoComplemento";
		public static final String BOTAO_VALIDACAO       = "botaoValidacao";
		public static final String INPUT_INICIO          = "inputInicio";
		public static final String INPUT_FIM             = "inputFim";
		public static final String BOTAO_CONFIGURAR      = "botaoConfigurar";
		public static final String TABLE_RESULTADOS      = "tableResultados";
		public static final String BOTAO_ENVOLVIDOS      = "botaoEnvolvidos";
		public static final String BOTAO_RECUSAR         = "botaoRecusar";
		public static final String BOTAO_VALIDAR         = "botaoValidar";
		public static final String BOTAO_GRAVAR          = "botaoGravar";
		public static final String BOTAO_GRAVAR_1        = BOTAO_GRAVAR + String.valueOf(INTEGER._1);
		public static final String BOTAO_HISTORICO       = "botaoHistorico";
		
		public static final String RADIO_REPRESENTANTE = "radioRepresentante";
		public static final String RADIO_PROCURADOR    = "radioProcurador";
		public static final String RADIO_PAIS          = "radioPais";
		public static final String RADIO_OUTROS        = "radioOutros";
		
		public static final String INPUT_UORG = "uorg";
		
		public static final String PATH_SEPARATOR = " > ";

		public static final String CHECKBOX_TODOS = "checkboxTodos";
		public static final String CHECKBOX_VALIDADAS = "pesquisarFiltroSituacaoValidada";
		public static final String CHECKBOX_CADASTRAMENTO = "checkboxCadastramento";
		public static final String CHECKBOX_RECUSADAS = "checkboxRecusadas";
		public static final String CHECKBOX_BLOQUEADAS = "checkboxBloqueadas";
		public static final String CHECKBOX_EXPIRADAS = "checkboxExpiradas";
		public static final String BOTAO_CADASTRAMENTO = "botaoCadastramento";
		public static final String CHECKBOX_SELECIONAR_TUDO = "checkboxSelecionarTudo";
		
		public static final String TABELA = "tabela";
		
		public static final String ETIQUETA_3 = "etiqueta3";
	}
	
	/**
	 * Expõe constantes pertinentes aos eventos das operações.
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class EVENT {
		
		public static final String TO_EXECUTE_1 = "EVENT_TO_EXECUTE_1";
		public static final String TO_EXECUTE_2 = "EVENT_TO_EXECUTE_2";
		public static final String TO_EXECUTE_3 = "EVENT_TO_EXECUTE_3";
		
		/**
		 * Nome de retorno de EvtDecidir convencionado para listar
		 */
		public static final String LISTAR = "LISTAR";
		
		public static final String PESQUISAR = "PESQUISAR";
		
		/**
		 * Nome de retorno de EvtDecidir convencionado para listar apenas, sem exbição do layout Listar.
		 */
		public static final String COLLECTION = "COLLECTION";
		
		/**
		 * Nome de retorno de EvtDecidir convencionado para detalhar
		 */
		public static final String DETALHAR = "DETALHAR";
		
		/**
		 * Nome de retorno de EvtDecidir convencionadao para incluir
		 */
		public static final String INCLUIR = "INCLUIR";
		
		public static final String ALTERAR   = "ALTERAR";
		public static final String ALTERAR_1 = "ALTERAR_1";
		
		public static final String AVANCAR = "AVANCAR";
		
		/**
		 * 
		 * Nome de retorno de EvtDecidir convencionado para listar<Br/>
		 *
		 * Valor padrão para retorno em um evento de decisão sobre abrir ou não a operação.<br/>
		 * Esse valor informativo denota que a operação terá sua execução abortada. 
		 */
		public static final String FINALIZAR = "FINALIZAR";
		
		public static final String ORIGINAL = "ORIGINAL";
		public static final String REPLACER = "REPLACER";
		
		public static final String EXECUTADO = "EXECUTADO";
		public static final String EXECUTADO_ORIGINAL = EXECUTADO + STRING.UNDERSCORE + ORIGINAL;
		public static final String EXECUTADO_REPLACER = EXECUTADO + STRING.UNDERSCORE + REPLACER;
		
		public static final String BYPASS    = "BYPASS";
		
		public static final String EXCEPTION = "EXCEPTION";
		
		public static final String DEFAULT   = "DEFAULT";
		
		public static final String INTEGRAR  = "INTEGRAR";
		
		public static final String INVALID   = "INVALID";
		
		public static final String VALID     = "VALID";
		
		public static final String CLOSE              = "CLOSE_DIALOG";
		public static final String EXECUTE_ON_INVALID = "EXECUTE_ON_INVALID";
		public static final String EXECUTE_PESQUISAR  = "EXECUTE_PESQUISAR";
		
		public static final String VOLTAR = "VOLTAR";
		
		public static final String CONCEDER = "CONCEDER";
		
		public static final String CONCESSAO = "CONCESSAO";
		
		public static final String MORE      = "MORE";
		
		public static final String DESBLOQUEAR = "DESBLOQUEAR";
		
		public static final String CONFIRMAR = "CONFIRMAR";
		
		public static final String SHOW_MESSAGE = "SHOW_MESSAGE";
		
		public static final String INTEGRA_EVENT = "INTEGRA_EVENT";
		
		public static final String EXCLUIR = "EXCLUIR";
		
		public static final String COMPLEMTAR = "COMPLEMTAR";
		
		public static final String GRAVAR = "GRAVAR";
		
		public static final String VALIDAR = "VALIDAR";
		
		public static final String RUN_TO = DSN.RUN_TO;		
	
	}
	
	/**
	 * Expõe constantes convencionadas para componentes da regra de negócio.
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class BUSINESS {
		/**
		 * Nome convencionado para acessar a instância corrente de Data Service Node
		 */
		public static final String DATA_SERVICE_NODE = "CURRENT_DATA_SERVICE_NODE";
		
		/**
		 * Nome convencionado para acessar a instância corrente de Objec Pool
		 */
		public static final String OBJECT_POOL = "CURRENT_OBJECT_POOL";
		
		/**
		 * Nome convencionado para acessar o retorno da chamada à regra de negócio (String)
		 */
		public static final String RETURNED = "CURRENT_RETURNED";
		
		/**
		 * Nome convencionado para acessar o resultado da chamada à regra de negócio (Object)<br/>
		 * Este valor deve ser colocado no BeanWrapper pela implementação do usuário.
		 */
		public static final String RESULT = "CURRENT_RESULT";
		
		/**
		 * Nome convencionado para acessar a instância de trabalho da regra de negócio
		 */
		public static final String INSTANCIA = "CURRENT_INSTANCE";
		
		/**
		 * Nome convencionado para acessar o novo valor que será aplicado em alguma regra de negócio
		 */
		public static final String NEW_VALUE = "CURRENT_NEW_VALUE";
		
		public static final String CALLER    = "CURRENT_CALLER";
		
		/**
		 * Nome convencionada para acessa a intância corrente de IOperation
		 */
		public static final String OPERATION = "CURRENT_OPERATION";
		
		public static final String EXECUTADO = "EXECUTADO";
		
		public static final String BYPASS   = "BYPASS";
		
		public static final String BASE_ID = "CURRENT_BASE_ID";
		
		public static final String OCBM = "CURRENT_OCBM";
		
		public static final String OCBM_PROVIDER = "CURRENT_OCBM_PROVIDER";
		
		public static final String BUSINESS_PROVIDER = "CURRENT_BUSINESS_PROVIDER";
		
		/**
		 * Executar desde o inicio, sem utilizar estados anteriores.
		 */
		public static final String RIGHT_FROM_START = "RIGHT_FROM_START";
	
		public static final String CONCESSAO      = "CONCESSAO";
		public static final String AGRUPAMENTO    = "AGRUPAMENTO";
		public static final String REPRESENTANTES = "REPRESENTANTES";
		public static final String PODERES        = "PODERES";
		public static final String CONTRATOS      = "CONTRATOS";
		
		/**
		 * Chave que deverá possuir um valor do tipo Boolean que indicará se a operação deve limpar o pool de objetos quando finalizar.<br/>
		 * Por padrão sempre o pool de objetos será limpo no evento OperacaoEvtFinalizar
		 */
		public static final String CLEAN_OBJECT_POOL = "CLEAN_OBJECT_POOL";
	}
	
	/**
	 * Expõe chaves padrão de acesso ao BeanWrapper que requisição de recurso à operação Application
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class RESOURCE {
		public static final String REQUESTING = "REQUESTING";
		public static final String NAME       = "RESOURCE_NAME";
		
		public static final String RESPONSE_RECEIVER = "RESPONSE_RECEIVER";
		public static final String RESOURCE_REQUEST  = "RESOURCE_REQUEST";
		
		/**
		 * Chave para acessar instância de IContainerObject caso o chamador da operação queira uma cópia do node da operação para si.<br/>
		 * Essa chave deve ser colocar no ObjectPool da sessão do usuário.<br/>
		 * O valor dessa chave deve ser um instância de IContainerObject
		 */
		public static final String COPY_DSN_TO = "COPY_DSN_TO";
	}
	
	public static final class VALIDATION {		
		/**
		 * Ativar validator já no carregamento da configuração.
		 */
		public static final String LOAD_EAGER = "EAGER";
		
		/**
		 * Carregar validador, mas sua ativação fica a cargo da implementação conforme regras do layout.
		 */
		public static final String LOAD_ON_DEMAND = "ON-DEMAND";
		
		public static final String CHECK_POLICY_AOMENOS_UM = "AOMENOS_UM";
		public static final String CHECK_POLICY_TODOS      = "TODOS";
		public static final String CHECK_POLICY_GRUPO      = "GRUPO";
		
		public static final String XML_BINDDING     = "Application.xml";
		public static final String PACKAGE_BINDDING = Loader.class.getPackage().getName();
		
	}
	
	/**
	 * Expõe constantes correspondentes às propriedades do projeto.
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class PROPERTIES {
		/**
		 * Chave da propriedade que armazena qual o IP que será utilizado para abertura do navegador.
		 */
		public static final String AWB_IP_ID      = "external.bradesco.browser.id.cimg";
		public static final String AWB_IP_ID_CIMG = "external.bradesco.browser.id.cimg";
		public static final String AWB_IP_ID_FIRP = "external.bradesco.browser.id.firp";
		
		/**
		 * Chave da propriedade que armazena qual a URI para acessar a inclusão do Projeto CIMG
		 */
		public static final String AWB_CIMG_URI = "external.bradesco.browser.uri.cimg";
		
		/**
		 * Chave da propriedade que armazena qual a URI para acessar a visualização no Projeto FIRP AWB
		 */
		public static final String AWB_FIRP_VISUALIZAR = "external.firp.listar.uri";
		
		/**
		 * Chave da propriedade que armazena a URI para acessar a visualização/Alterar no Projeto FIRP AWB
		 */
		public static final String AWB_FIRP_ALTERAR = "external.firp.alterar.uri";
		
		/**
		 * Chave da propriedade que armazena qual a URI para validar o identificador da Digitalização
		 */
		public static final String AWB_FIRP_DIGITALIZACAO = "external.firp.validar.url";
		
		public static final String RUNTIME = "runtime";
		
		public static final String SYSTEM = "system";
		
		public static final String INSTANCE_FACTORY = SYSTEM + ".pdk.factory";
		
		public static final String AMBIENTE = "Ambiente";
		
		public static final String PSDC_CC_ON           = "psdc.cc.on";
		public static final String PSDC_CC_PRODUTO      = "psdc.cc.produto";
		public static final String PSDC_CC_PARTICIPACAO = "psdc.cc.participacao";
		
		public static final String UI_MESSAGE_LINE_MARKER       = "ui.message.line.marker";
		public static final String UI_MESSAGE_LINE_WORDS_LENGTH = "ui.message.line.words.length";
		
		public static final String BRANCH_SERVER = PLACE_HOLDER.BRANCH_PREFIX + PLACE_HOLDER.BRANCH_SERVER;
		public static final String BRANCH_CODE   = PLACE_HOLDER.BRANCH_PREFIX + PLACE_HOLDER.BRANCH_CODE;
		public static final String BRANCH_USER   = PLACE_HOLDER.BRANCH_PREFIX + PLACE_HOLDER.BRANCH_USER;
		private static final Map<String, ICase<String>> MODIFIABLE_CASES = new HashMap<String, ICase<String>>();
		static{
			MODIFIABLE_CASES.put(BRANCH_SERVER, new BranchPropertyServerCase());
			MODIFIABLE_CASES.put(BRANCH_CODE, new BranchPropertyCodeCase());
			MODIFIABLE_CASES.put(BRANCH_USER, new BranchPropertyUserCase());
		}
		public static final Map<String, ICase<String>> CASES = Collections.unmodifiableMap(MODIFIABLE_CASES);
	}
	
	public static final class NET {
		public static final String BRANCH_SERVER_PATTERN = "a{0}s001";
	}
	
	/**
	 * Expõe chaves padrão de acesso no ObjectPool
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class OBJECT_POOL {
		public static final String DEFAULT_TIPO_DOCUMENTO_INDEX = DSN.DEFAULT_LISTBOX_TIPO_DOCUMENTO;
		
		public static final String DEFAULT_TIPOS_PODERES_INDEX = DSN.DEFAULT_LISTBOX_TIPO_PODERES;
		
		/**
		 * Chave convencionada para armazenar instância de PessoaDTO para integração entre Operações.
		 * @see Pessoa
		 */
		public static final String PESSOA = "PESSOA";
		
		/**
		 * Chave convencionada para armazena instância de RepresentanteDTO para integração entre Operações.
		 * @see Pessoa
		 */
		public static final String REPRESENTANTE = "REPRESENTANTE";
		
		public static final String CONCESSAO  = "CONCESSAO";
		public static final String SELECTED   = "SELECTED";
		public static final String TIPO_PODER = "TIPO_PODER";
		
		public static final String EVENTS_PROVIDER   = "EVENTS_PROVIDER";
		public static final String BUSINESS_PROVIDER = "BUSINESS_PROVIDER";
		
		public static final String CALLER_OPERATION = "CALLER_OPERATION";

		public static final String INSTANCIA_OPERATION = "OPERATION_INSTANCIA";
		
		public static final String BLACK_LIST = "BLACK_LIST";
	}
	
	/**
	 * Expõe constantes com as chaves de atributos padrão para leitura/armazenamento na sessão.
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class SESSION {
		
		public static final Attribute<CaduV2CallingDTO> CADUV2_CALL = new Attribute<CaduV2CallingDTO>("CADUV2_CALL", CaduV2CallingDTO.class);
		public static final Attribute<ObjectPool>       OBJECT_POOL = new Attribute<ObjectPool>("OBJECT_POOL", ObjectPool.class);
		
	}
	
	/**
	 * Expõe constantes para comunicação com o sistema CADU V2
	 * @author fabiojm - Fábio José de Moraes
	 */
	public static final class CADUV2 {
		
		public static final String NAMEID = "CADUV2";
		
		/**
		 * Nome da operação para invocação do CADU V2 no PRJ PESS.
		 */
		public static final String CALL = "PRJ PESS.ChamadaCaduV2";
		
		/**
		 * Nó dentro de OP_CALLERS para chamada CADU V2.
		 */
		public static final String NODE_OP  = "OP_PRJ PESS_ChamadaCaduV2";
		
		public static final String NODE_SISTEMA = "sistema";
		public static final String NODE_EXTERNO = "externo";
		public static final String NODE_PERMITIR_CADASTRO = "permitirCadastroNovo";
		public static final String NODE_NAVEGACAO_CADASTRO = "NavegacaoCadastro";
		public static final String NODE_PRODUTO = "PRODUTO";
		public static final String NODE_PARTICIPACAO = "PARTICIPACAO";
		public static final String NODE_TELA = "TELA_CODIGO";
		public static final String NODE_IDENTIFICADO = "identificado";
		public static final String NODE_NOME_INPUT = "NOME";
		public static final String NODE_NOME_OUTPUT = "param_nome";
		public static final String NODE_DOCUMENTO_CORPO = "CPF";
		public static final String NODE_DOCUMENTO_FILIAL = "CPF_FILIAL";
		public static final String NODE_DOCUMENTO_CONTROLE = "CPF_DIGITO";
		public static final String NODE_EMPRESA = "EMPRESA";
		public static final String NODE_CLUB_INPUT = "CLUB";
		
		public static final String NODE_CLUB_OUTPUT = "CLUB_SELECTED";
		
		
		/**
		 * Identificador da tela de Cadastro Geral dentro do Sistema CADU V2;
		 */
		public static final int TELA_CADASTRO_GERAL = 0;
		
		public static final String CONDICIONADO = "CONDICIONADO";
		public static final String TO_COMPARE   = "TO_COMPARE";
		
		public static final String CLASSE_CADASTRAL_PARTICIPACAO_DEFAULT = "6";
		
		/**
		 * Lançar exceção quando o cliente não for identificado no cadu.<br/>
		 * true ou false.
		 */
		public static final String THROW_WHEN_NOT_IDENTIFIED = "THROW_WHEN_NOT_IDENTIFIED";
		
		public static final String REPRESENTACAO = NAMEID + STRING.DOT + "REPRESENTACAO";
		
		/**
		 * Quando presente no wrapper do CaduV2Calling, o tipo da instância obtida no cadu será comparado. Caso não seja do mesmo tipo lança-se exceção.
		 */
		public static final String TIPO_PESSOA = NAMEID + STRING.DOT + "TIPO_PESSOA";
		
		/**
		 * Quando presente no wrapper do CaduV2Calling com valor <code>Boolean.TRUE</code> e o cadastro da pessoa incomplento, invocará a tela de atualização de dados da tela componente CaduV2
		 */
		public static final String UPDATE_ON_INCOMPLETE = NAMEID + STRING.DOIS_PONTOS + "UPDATE_ON_INCOMPLETE";
	}
	
	public static final class ATHIC {
		public static final int PESSOA_FISICA   = 3;
		public static final int PESSOA_JURIDICA = 6;
		
		public static final String CLIENTE  = "athic.cliente";
		
		public static final String PRODUCT  = "athic.product";
		public static final String ID       = "athic.id";
		public static final String CSV_PATH = "athic.csv";
		public static final String CSV_NAME = "csv.name";
		public static final String BPO_TYPE = "BPO.type";
	}
}
