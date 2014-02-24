package com.javaf;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.javaf.javase.persistence.Column;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class Constants {

	private Constants(){
		
	}
	
	/**
	 * Atributos da aplicação.
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class APPLICATION {
		
		/**
		 * Chave para armazenamento do nome de arquivo para internacionalização no mapa de propriedades da aplicação.
		 */
		public static final String BUNDLE_PROPERTY     = "RESOURCE_BUNDLE";
		
		/**
		 * Chave para armazenamento do nome de arquivo para propriedades do projeto no mapa de propriedades da aplicação.
		 */
		public static final String PROPERTIES_PROPERTY = "PROPERTIES_PROPERTY";
		
		/**
		 * Chave para armazenamento do código do projeto.
		 */
		public static final String CODE_PROPERTY       = "CODE_PROPERTY";
		
		/**
		 * Chave para armazenamento do nome do projeto. 
		 */
		public static final String NAME_PROPERTY       = "NAME_PROPERTY";
		
		/**
		 * Chave para armazenamento do título do projeto.
		 */
		public static final String TITLE_PROPERTY      = "TITLE_PROPERTY";
		
		/**
		 * Chave para armazenamento da versão do projeto.<br/>
		 * tipo: {@link String}
		 */
		public static final String VERSION_PROPERTY    = "VERSION_PROPERTY";
		
		/**
		 * Chave para armazenamento do caminho absoluto para o diretório de arquivos temporários.<br/>
		 *  - Essa chave armazena uma instância de <code>java.lang.String</code><br/>
		 *  - O valor padrão é carregado da propriedade <code>user.dir</code>
		 * <pre>
		 * System.getProperty("user.dir");
		 * </pre>
		 * @see System#getProperties()
		 */
		public static final String TEMPORARY_PATH_PROPERTY = "TEMPORARY_PATH_PROPERTY";
		
		/**
		 * Política para criação de instâncias das classes util, retornada no método <code>getInstance</code>.<br/>
		 * tipo: {@link InstancePolicy}
		 * @see InstancePolicy
		 */
		public static final String UTIL_INSTANCIES_POLICY = "UTIL_INSTANCIES_POLICY";
		
		/**
		 * Mapeamento de intercionalização, ligando qualquer chave à um texto localizado.<br/>
		 * tipo: {@link Map}
		 * 
		 */
		public static final String I18N_MAPPER = "I18N_MAPPER";
		
		/**
		 * Mapeamento das propriedades, valores default, command, etc.
		 */
		static final Map<Object, Object> PROPERTIES = new HashMap<Object, Object>();
		static{
			Application.getInstance().register(Locale.class,            DEFAULT.LOCALE);
			Application.getInstance().register(BUNDLE_PROPERTY,         DEFAULT.LOCALIZATION_BUNDLE);
			Application.getInstance().register(PROPERTIES_PROPERTY,     DEFAULT.APPLICATION_PROPERTIES);
			Application.getInstance().register(CODE_PROPERTY,           DEFAULT.APPLICATION_CODE);
			Application.getInstance().register(NAME_PROPERTY,           DEFAULT.APPLICATION_NAME);
			Application.getInstance().register(TITLE_PROPERTY,          DEFAULT.APPLICATION_TITLE);
			Application.getInstance().register(VERSION_PROPERTY,        DEFAULT.APPLICATION_VERSION);
			Application.getInstance().register(UTIL_INSTANCIES_POLICY,  DEFAULT.APPLICATION_UTIL_INSTANCIES);
			Application.getInstance().register(I18N_MAPPER,             DEFAULT.APPLICATION_I18N_MAPPER);
			Application.getInstance().register(TEMPORARY_PATH_PROPERTY, DEFAULT.APPLICATION_TEMPORARY_PATH);
		}
	}
	
	public static final class INTEGER {
		public static final int _1NEG = -1;
		public static final int _0 = 0;
		public static final int _1 = 1;
		public static final int _2 = 2;
		public static final int _3 = 3;
		public static final int _4 = 4;
		public static final int _5 = 5;
		public static final int _6 = 6;
		public static final int _7 = 7;
		public static final int _8 = 8;
		public static final int _9 = 9;
		public static final int _10 = 10;
		public static final int _11 = 11;
		public static final int _12 = 12;
		public static final int _13 = 13;
		public static final int _14 = 14;
		public static final int _15 = 15;
		public static final int _16 = 16;
		public static final int _17 = 17;
		public static final int _18 = 18;
		public static final int _19 = 19;
		
		public static final int _100 = 100;
		public static final int _999999 = 999999;
	}
	
	public static final class DOUBLE {
		public static final double _0 = 0D;
	}
	
	public static final class LONG {
		public static final long _0 = 0L;
	}
	
	public static final class STRING {
		public static final String _0 = "0";
		public static final String _1 = "1";
		public static final String _2 = "2";
		public static final String _3 = "3";
		public static final String _4 = "4";
		
		public static final String EMPTY   = "";
		public static final String VIRGULA = ",";
		public static final String DOIS_PONTOS = ":";
		
		public static final String PARENTESES_ABRE  = "(";
		public static final String PARENTESES_FECHA = ")";
		
		public static final String SPACE1 = " ";
		public static final String SPACE2 = "  ";
		
		public static final String COLCHETES_ABRE  = "[";
		public static final String COLCHETES_FECHA = "]";
		public static final String IGUAL           = "=";
		public static final String IGUAL_SPACE     = " = ";
		public static final String DOT             = ".";
		public static final String JAVA_NEW_LINE   = "\n";
		public static final String NEW_LINE = System.getProperty("line.separator", JAVA_NEW_LINE);
		public static final String TAB = "\t";
		
		public static final String MAIOR = ">";
		public static final String MENOR = "<";

		public static final String CHAVES_ABRE      = "{";
		public static final String CHAVES_FECHA     = "}";
		
		public static final String HIFEM = "-";
		public static final String HIFEM_SPACE = " - ";
		
		public static final String F = "F";
		public static final String f = "f";
		public static final String V = "V";
		public static final String v = "v";
		public static final String T = "T";
		public static final String t = "t";
		
		public static final String ASPAS_SIMPLES = "'";
		public static final String ASTERISCO2 = "**";
		
		public static final String UNDERSCORE = "_";
		public static final String IP = "IP";
		public static final String HTTP_PREFIX = "http://";
		public static final String BARRA = "/";
		public static final String[] CPF_CNPJ_REMOVE = new String[]{DOT, HIFEM, BARRA};
	}
	
	public static final class CHAR {
		public static final char DOT = '.';
	}
	
	public static final class REGEX {
		public static final String XML                         = ".*[Xx][Mm][Ll]"; 
		public static final String VIRGULA                     = ",";
		public static final String NAO_NUMEROS                 = "[^0-9]";
		public static final String DOT                         = "\\.";
		public static final String I18N_TEXT                   = "[0-9]+\\*\\*.+";
		public static final String LABEL_HOLDER                = ".*\\[label\\.[0-9]+\\].*";
		public static final String HOLDER                      = "\\[.+\\]";
		public static final String LETRAS_NUMEROS              = ".\\w+";
		public static final String PONTO_E_VIRGULA             = "\\;";
		public static final String REFLECTION_METHOD_SIGNATURE = ".*\\([\\w\\.]+\\)";
		
		public static final String PLACEHOLDER_OPEN     = "#\\{";
		public static final String PLACEHOLDER_CLOSE    = "\\}";
		public static final String PLACEHOLDER          = PLACEHOLDER_OPEN + "[\\.\\w]+" + PLACEHOLDER_CLOSE;
		public static final String PLACEHOLDER_CONTAINS = ".*" + PLACEHOLDER + ".*";
		
		public static final String BRANCH_PROPERTY_PLACEHOLDER          = PLACEHOLDER_OPEN + "branch\\.[\\.\\w]+" + PLACEHOLDER_CLOSE;
		public static final String BRANCH_PROPERTY_PLACEHOLDER_CONTAINS = ".*" + BRANCH_PROPERTY_PLACEHOLDER + ".*";
		public static final String SYSTEM_PROPERTY_PLACEHOLDER          = PLACEHOLDER_OPEN + "system\\.[\\.\\w]+" + PLACEHOLDER_CLOSE;
		public static final String SYSTEM_PROPERTY_PLACEHOLDER_CONTAINS = ".*" + SYSTEM_PROPERTY_PLACEHOLDER + ".*";
		
		public static final String NAMED_PARAMETER_OPEN     = "\\:";
		public static final String NAMED_PARAMETER_CLOSE    = "\\:";
		public static final String NAMED_PARAMETER          = NAMED_PARAMETER_OPEN + "[\\.\\w]+" + NAMED_PARAMETER_CLOSE;
		public static final String NAMED_PARAMETER_CONTAINS = ".*" + NAMED_PARAMETER + ".*";
	}
	
	public static final class PLACE_HOLDER{
		public static final String OPEN  = "#{";
		public static final String CLOSE = "}";
		public static final String INSTANCIA_PREFIXO = OPEN + "instancia.prefixo" + CLOSE;
		public static final String SYSTEM_PREFIX = "system.";
		public static final String BRANCH_PREFIX = "branch.";
		public static final String BRANCH_SERVER = "server";
		public static final String BRANCH_CODE   = "code";
		public static final String BRANCH_USER   = "user";
	}
	
	public static final class NAMED_PARAMETER {
		public static final String OPEN  = ":";
		public static final String CLOSE = ":";
	}
	
	public static final class MASK {
		public static final String CNPJ       = "##.###.###/####-##";
		public static final String CNPJ_15DIG = "###.###.###/####-##";
		public static final String CPF        = "###.###.###-##";
	}
	
	public static final class I18N {
		public static final Map<Object, Object> MAPPER = new HashMap<Object, Object>();
		
		public static final String OK = "OK";
		public static final String NOT_LOCALIZED = "???? {0} ????";
		public static final String NULL_LABEL = MessageFormat.format(NOT_LOCALIZED, "NULL LABEL");
		public static final String NULL_VALUE = MessageFormat.format(NOT_LOCALIZED, "NULL VALUE");
		
		public static final String EVENT = "Event";
		
		public static final String NAO_FOI_POSSIVEL_RENOMEAR_ARQUIVO = "Não foi possível renomear arquivo: ";
		public static final String METODO_SETTER_NAO_ENCONTRADO = "Método setter não encontrado";
		public static final String ARGUMENTO_NAO_SIMPLES = "Argumento não se trata de um tipo simples";
		public static final String CLASSE_INFORMADA_NAO_EXTENSAO = "Classe informada não se trata de uma extensão de {0}";
		public static final String METODO_SETTER_NAO_ENCONTRADO_ATT = "Método setter não encontrado para atributo: {0}";
		public static final String ARGUMENTO_NAO_SUBCLASSE = "Argumento não é uma subclasse de {0}";
		public static final String ARGUMENTO_N_VALOR_DESCONHECIDO = "Valor do Argumento{0} desconhecido: {1}";
		public static final String PROPRIEDADE_NAO_ENCONTRADA = "Propriedade não encontrada: {0}";
		public static final String X_NAO_PODE_SER_OBTIDO = "{0} da implementação não pode ser obtido: {1}";
		public static final String ARGUMENTO_N_DEVE_SER_UMA_INSTANCIA = "Argumento{0} dever ser uma instância de {1}";
		public static final String OPERACAO_DESCONHECIDA = "Operação desconhecida: {0}";
		public static final String DEFAULT = "(Default) ";
		public static final String CONTAINER_DSN_X_NAO_POSSUI = "Container DSN {0} não possui área obrigatória: {1}";
		public static final String CONTAINER_DSN_NAO_E = "Container DSN não se trata do fluxo {0}: {1}";
		public static final String CLASSE_X_NAO_ANOTADA_COM = "Class {0} não está anotada com {1}";
		public static final String AREA_DE_TRABALHO_NO_DSN = "Area de trabalho no DSN: ";
		public static final String LEITURA_ATRIBUTO_SIMPLES = "Leitura atributo simples ";
		public static final String DO_TIPO = " do tipo ";
		public static final String OBTENDO_SIMPLE_OBJECT_NA_AREA_DE_TRABALHO = "Obtendo SimpleObject na area de trabalho: ";
		public static final String VALOR_NAO_FOI_POSSIVEL_TRANSFORMAR = "(  VALOR) Não foi possível transformar: ";
		public static final String DEFAULT_APLICAR = "(DEFAULT) Aplicar: ";
		public static final String DEFAULT_NAO_FOI_POSSIVEL_TRANSFORMAR = "(DEFAULT) Não foi possível transformar: ";
		public static final String NAO_FOI_POSSIVEL_INVOCAR_SET_DO_ATRIBUTO = "Não foi possível invocar o set do atributo ";
		public static final String ATRIBUTO = "Atributo ";
		public static final String NAO_POSSUI_SEU_RESPECTIVO_SETTER = " não possui seu respectivo 'setter'";
		public static final String DYNAMIC = "(DYNAMIC) ";
		public static final String INSTANCIA_NAO_E_UMA_SUBCLASSE_DE = "Instância não é uma subclasse de ";
		public static final String LEITURA_ATRIBUTO_EMBARCADO = "Leitura atributo embarcado ";
		public static final String ATRIBUTO_MARCADO_COMO = "Atributo marcado como ";
		public static final String ESTA_NULL = " está null";
		public static final String ATRIBUTO_MARCADO_COMO_TRANSIENT = "Atributo marcado com Transient: ";
		public static final String NAO_PODE_SER_OBTIDO = " não pôde ser obtido";
		public static final String LEITURA_ATRIBUTO_OCCURS = "Leitura atributo occurs ";
		public static final String OBTENDO_LIST_OBJECT_NA_AREA_DE_TRABALHO = "Obtendo Listobject na area de trabalho: ";
		public static final String DEVE_SER_UMA_IMPLEMENTACAO_DE = " deve ser uma implementação de ";
		public static final String OCCURS_NAO_ESTA_MAPEADO_PARA_ESCRITA = "Occurs não está mapeado para escrita: ";
		public static final String GRAVANDO_ATRIBUTO_SIMPLES = "Gravando atributo simples ";
		public static final String INVOCACAO_DO_GETTER_GEROU_EXCECAO = "Invocação do getter gerou exceção.";
		public static final String GRAVANDO_ATRIBUTO_EMBARADO = "Gravando atributo embarcado ";
		public static final String GRAVANDO_ATRIBUTO_OCCURS = "Gravando atributo occurs ";
		public static final String ANOTADO_PARA_GRAVAR_OCORRENCIAS_OFFSET = "Anotado para gravar ocorrencias offset: size=";
		public static final String OFFSET = ", offset=";
		public static final String OCORRENCIAS_OFFSET_FORAM_GRAVADAS = "Ocorrencias offset foram gravadas: ";
		public static final String NAO_FORAM_GRAVADAS_OFFSET = "Não foram gravadas ocorrencias offset por erro em instância o objeto base.";
		public static final String LIMPANDO_AREA_DO_FWO = "Limpando area do FWO: ";
		public static final String ARGUMENTO_NAO_PODE_SER_INSTANCIADO = "Argumento não pôde ser instanciado: {0}";
		public static final String ARGUMENTO_N_VALOR_ILEGAL = "Argumento{0} possui tipo ilegal: {1}";
		public static final String ATRIBUTO_NAO_POSSUI_SETTER = "Atributo {0} não possui seu respectivo setter";
		public static final String CONFIGURADO_PERSISTENCIA_INSTANCIA_NULL = "Configurado para construir estrutura de persistência, mas não há instância disponível";
		public static final String COLECAO_NAO_ARMAZENA = "Coleção não armazena o tipo {0}: {1}";
		public static final String COLECAO_NAO_POSSUI_TIPO = "Coleção não possui tipo armazenado: {0}";
		public static final String TIPO_NAO_MAPEADO_DSN = "Tipo do não mapeado para Data Service Node: {0}";
		public static final String CONFIRMA_CANCELAR = "Tem certeza que deseja cancelar?";
		public static final String CANCELAR = "Cancelar";
		public static final String X_NAO_FOI_ENCONTRADAO = "{0} não foi encontrado: {1}";
		public static final String GRAVANDO_AREA_INPUT_FLUXO = "Gravando área de input do fluxo: {0}";
		public static final String EXECUTANDO_FLUXO = "Executando fluxo: {0}";
		public static final String LEITURA_AREA_OUTPUT_FLUXO = "Leitura da área de output do fluxo: {0}";
		public static final String RESULTADO_FLUXO_NAO_SUCESSO = "Resultado do fluxo não sinaliza sucesso: {0}";
		public static final String PROBLEMA_GRAVACAO_AREA_FLUXO = "Problema na gravação da área de entrada do fluxo: {0}";
		public static final String PROBLEMA_LEITURA_AREA_FLUXO = "Problema na leitura da área de saída do fluxo: {0}";
		public static final String PROBLEMA_EXECUCAO_FLUXO = "Problema na execução do fluxo: {0}";
		public static final String CLASSE_NAO_ANOTADA = "Classe não está anotada com {0}: {1}";
		public static final String INSTANCIA_NAO_ENCONTRADA_WRAPPER = "Instância de {0} não está presente no wrapper";
		public static final String INSTANCIA_NULA = "Instância é null!";
		public static final String FWO_CALLING_EXCEPTION1 = "Execute o invoke(T, DataServiceNodeProvider, IEventsServiceProvider, IDataServiceNode) para construir o estado interno da chamada";
		public static final String PREENCHER = "Preencher:";
		public static final String DEVE_SER_INFERIOR_OU_IGUAL = " deve ser inferior ou igual à ";
		public static final String SELECIONE_A_CHECAGEM = "Selecione a checagem {0}";
		public static final String SELECIONE_AO_MENOS_X_CHECAGENS = "Selecione ao menos {0} checagem(ns)";
		public static final String SELECIONE_EXATAMENTE_X_CHECAGENS = "Selecione exatamente {0} checagem(ns): {1}";
		public static final String SELECIONE_UMA_OPCAO_EM = "Selecione uma opção em {0}";
		public static final String SELECIONAR_UMA_DAS_OPCOES = "Selecionar uma das opções:";
		public static final String METHODO_X_NAO_RETORNOU_INSTANCIA_DE = "Método {0} não retornou uma instância de {1}: {2}";
		public static final String CLASSE_X_NAO_SEGUE_GENERICS = "Classe {0} não segue regras da API Generics";
		public static final String IDENTIFICADOR_VALIDADOR_NAO_ENCONTRADO_NAS_CONFIGURACOES = "Idenficador do Validador não encontrado nas configurações: ";
		public static final String NENHUMA_CONFIGURACAO_DE_VALIDADORES_ENCONTRADA_NO_EVENT_VALIDATOR = "Nenhuma configuração de validadores encontrada no EventValidator.";
		public static final String NAO_FOI_ENCONTRADA_CONFIGURACAO_PARA_OPERACAO = "Não foi encontrada configuração para operação: ";
		public static final String NAO_FOI_ENCONTARDA_CLASSE_DE_VIEW = "Não foi encontrada classe de View: ";
		public static final String NAO_FOI_ENCONTRADA_CLASSE_MODEL_CONFIGURADA = "Não foi encontrada classe model configurada: ";
		public static final String CLASSE_CONFIGURADA_FILTRO_NAO_ESTENDE = "Classe configurada como filtro de pesquisar não estende ";
		public static final String NAO_FOI_ENCONTRADA_CLASSE_HELPER_CONFIGURADA = "Não foi encontrada classe helper configurada: ";
		public static final String NAO_FOI_ENCONTRADA_CLASSE_FILTRO_PEQUISAR_CONFIGURADA = "Não foi encontrada classe filtro de pesquisar configurada: ";
		public static final String WAS_REMOVED = " was removed.";
		public static final String APPLYING_LABELLING_HOLDERS = "Applying Labelling holders: {0}";
		public static final String LABELLING_HOLDERS_DONE = "Labelling holders done: {0}";
		public static final String BOTAO_NAO_EXISTE_LAYOUT = "Botão {0} não existe no layout {1}";
		public static final String CONFIRMAR_INCLUSAO = "Confirmar inclusão?";
		public static final String CONFIRMAR_ALTERACAO = "Confirmar alteração?";
		public static final String CONFIRMAR_EXCLUSAO = "Confirmar exclusão?";
		public static final String PROPRIEDADE_NAO_CONFIGURADA_PROJETO = "Propriedade não configurada no projeto: {0}";
		public static final String CADU_CHAMADA_V2 = "Chamada CADU V2";
		public static final String CADU_LOG_INICIANDO_CHAMADA = "Iniciando chamada CADU V2...";
		public static final String CADU_LOG_FINALIZANDO_CHAMADA_SUCESSO = "Finalizando a chamada CADU V2 com a Pessoa identificada: CLUB={0}";
		public static final String CADU_LOG_FINALIZANDO_CHAMADA_NAO_IDENT = "Finalizando a chamada CADU V2 sem identificar a Pessoa.";
		public static final String CADU_CLIENTE_NAO_IDENT_CAD = "O Cliente não foi identificado ou cadastrado no CADU";
		public static final String DESEJA_COMPLETAR_CADASTRO = "Deseja completar cadastro?";
		public static final String INVOCANDO_URL = "Invocando URL: {0}";
		public static final String INICIANDO_LEITURA_DAS_LINHAS = "Iniciando leitura das linhas ...";
		public static final String FINALIZANDO_LEITURA_DAS_LINHAS = "Finalizando leitura das linhas.";
		public static final String APLICATIVO_ATHIC_NAO_SUCESSO = "Aplicativo ATHIC Scanner não pode ser executado.\n\nVerifique as configurações.";
		public static final String ANOTADO_OU_EXTENDER = "{0} deve estar anotado com {1} ou ser subclasse de {2}";
		
		public static final String E = "e ";
		
		public static final String ZERO = "zero";
		public static final String UM   = "um";
		public static final String DOIS = "dois";
		public static final String TRES = "tres";
		public static final String QUATRO = "quatro";
		public static final String CINCO = "cinco";
		public static final String SEIS = "seis";
		public static final String SETE = "sete";
		public static final String OITO = "oito";
		public static final String NOVE = "nove";
		
		public static final String DEZ = "dez";
		public static final String ONZE = "onze";
		public static final String DOZE = "doze";
		public static final String TREZE = "treze";
		public static final String QUATORZE = "quatorze";
		public static final String QUINZE = "quinze";
		public static final String DEZESSEIS = "dezesseis";
		public static final String DEZESETE = "dezesete";
		public static final String DEZOITO = "dezoito";
		public static final String DEZENOVE = "dezenove";
		
		public static final String VINTE = "vinte";
		public static final String TRINTA = "trinta";
		public static final String QUARENTA = "quarenta";
		public static final String CINQUENTA = "cinquenta";
		public static final String SESSENTA = "sessenta";
		public static final String SETENTA = "setenta";
		public static final String OITENTA = "oitenta";
		public static final String NOVENTA = "noventa";
		
		public static final String CENTO = "cento";
		public static final String CEM = "cem";
		public static final String DUZENTOS = "duzentos";
		public static final String TREZENTOS = "trezentos";
		public static final String QUATROCENTOS = "quatrocentos";
		public static final String QUINHENTOS = "quinhentos";
		public static final String SEISCENTOS = "seiscentos";
		public static final String SETECENTOS = "setecentos";
		public static final String OITOCENTOS = "oitocentos";
		public static final String NOVECENTOS = "novecentos";
		
		public static final String MIL = "mil";
		
		public static final String VALOR_MAXIMO_PARA_VERBALIZE_E_DE = "Valor máximo para verbalize é de {0}";
		public static final String ARGUMENTO_N_ESTA_NULL = "Argumento{0} está null";
		public static final String NENHUM_RESETER_REGISTRADO_PARA = "Nenhum reseter registrado para {0}";
		
	}
	
	public static final class ENCODING {
		public static final String UTF_8   = "UTF-8";
		public static final String LATIM_1 = "ISO-8859-1";
	}
	
	public static final class REFLECTION {

		public static final Object[] NO_ARGS = new Object[]{}; 
		public static final String VALUES_METHOD = "values";
		public static final String ENUM_VALUE_OF_METHOD = "valueOf(java.lang.String)";
	
		/**
		 * Prefixo para métodos getter.
		 */
		public static final String GETTER_PREFIX = "get";
		
		/**
		 * Prefixo para métodos setter.
		 */
		public static final String SETTER_PREFIX = "set";
		
		/**
		 * Prefixo para métodos "get" do tipo boolean
		 */
		public static final String IS_PREFIX = "is";
		
		public static final String METHOD_GET_SIMPLE_OBJECT = "getSimpleObject";
		public static final String METHOD_ADD_SIMPLE_OBJECT = "addSimpleObject";
		
		public static final String METHOD_GET_CONTAINER_OBJECT = "getContainerObject";
		public static final String METHOD_ADD_CONTAINER_OBJECT = "addContainerObject";
		
		public static final String METHOD_GET_LIST_OBJECT = "getListObject";
		public static final String METHOD_ADD_LIST_OBJECT = "addListObject";
		
		public static final String METHOD_GET_INSTANCE = "getInstance";
		public static final String METHOD_SEND_ERROR_MESSAGE = "sendErrorMessage";
		public static final String METHOD_SHOW_ERROR_MESSAGE = "showErrorMessage";
		
		public static final String METHOD_ABSOLUTE_OF = "absoluteOf";
		public static final String METHOD_GET_CURRENT_INSTANCE = "getCurrentInstance";
		public static final String METHOD_GET_EXTERNAL_CONTEXT = "getExternalContext";
		
		public static final String ATT_ENTRIES = "entries";
		public static final String ATT_SELECTED_INDEXES = "selectedIndexes";
		
		public static final String METHOD_VALUES = "values";
		
		public static final String METHOD_ADD     = "add";
		public static final String METHOD_ADD_ALL = "addAll";
		public static final String METHOD_BREATH  = "breath";
		
		public static final String METHOD_SET_FW_TYPE = "setFWType";
		public static final String METHOD_SET_IDX     = "setIdx";
		
		public static final String METHOD_GET_NESTED_EXCEPTION = "getNestedException";
		
		private static final Map<String, String> MODIFIABLE_BLACK_METHODS = new HashMap<String, String>();
		static{
			MODIFIABLE_BLACK_METHODS.put("getClass", "getClass");
		};
		public static final Map<String, String> BLACK_METHODS = Collections.unmodifiableMap(MODIFIABLE_BLACK_METHODS);
		
	}
	
	public static final class ATTRIBUTE {
		public static final String NAME = "name";
	}
	
	public static final class DEFAULT {
		public static final String STRING_NAO = "N";
		public static final String STRING_SIM = "S";
		
		/**
		 * Data-Hora no formato ABNT
		 */
		public static final DateFormat ABNT_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		public static final DateFormat ABNT_DATE_FORMAT      = new SimpleDateFormat("dd/MM/yyyy");
		
		public static final DecimalFormat DEFAULT_INTEGER_FORMAT     = new DecimalFormat("#0");
		public static final DecimalFormat DEFAULT_LONG_FORMAT        = new DecimalFormat("#0");
		public static final DecimalFormat DEFAULT_FLOAT_POINT_FORMAT = new DecimalFormat("#0.0#");
		
		public static final DecimalFormat DOCUMENTO_CORPO    = new DecimalFormat("000000000");
		public static final DecimalFormat DOCUMENTO_FILIAL   = new DecimalFormat("0000");
		public static final DecimalFormat DOCUMENTO_CONTROLE = new DecimalFormat("00");
		
		public static final DecimalFormat DOCUMENTO_CPF    = new DecimalFormat("00000000000");
		public static final DecimalFormat DOCUMENTO_CNPJ   = new DecimalFormat("#00000000000000");
		public static final DecimalFormat DOCUMENTO_CNPJ15 = new DecimalFormat("000000000000000");
		
		public static final String PESSOA_FISICA   = "F";
		public static final String PESSOA_JURIDICA = "J";
		
		private static final Map<Object, Object> MODIFIABLE_PESSOAS_TYPE = new HashMap<Object, Object>();
		static{
			MODIFIABLE_PESSOAS_TYPE.put("FISICA", PESSOA_FISICA);
			//PESSOAS_TIPO.put(Constants.COBOL.PESSOA_FISICA,      PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("F",      PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("f",      PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("Fisica", PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("Física", PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("fisica", PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("física", PESSOA_FISICA);
			MODIFIABLE_PESSOAS_TYPE.put("Fis",    PESSOA_FISICA);
			
			MODIFIABLE_PESSOAS_TYPE.put("JURIDICA", PESSOA_JURIDICA);
			//PESSOAS_TIPO.put(Constants.COBOL.PESSOA_JURIDICA,        PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("J",        PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("j",        PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("Juridica", PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("Jurídica", PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("juridica", PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("jurídica", PESSOA_JURIDICA);
			MODIFIABLE_PESSOAS_TYPE.put("Jur",      PESSOA_JURIDICA);
		};
		public static final Map<Object, Object> PESSOAS_TIPO = Collections.unmodifiableMap(MODIFIABLE_PESSOAS_TYPE);
		
		public static final String DEVELOPMENT_TIME = "1";
		
		public static final Locale LOCALE                               = new Locale("pt", "BR");
		public static final String LOCALIZATION_BUNDLE                  = "javajm/java/util/Localization";
		public static final String APPLICATION_PROPERTIES               = "Application.properties";
		public static final String APPLICATION_CODE                     = "javaf";
		public static final String APPLICATION_NAME                     = "PRJ JAVAF";
		public static final String APPLICATION_TITLE                    = "Java Facilities";
		public static final String APPLICATION_VERSION                  = "v0.001";
		public static final InstancePolicy APPLICATION_UTIL_INSTANCIES  = InstancePolicy.BY_THREAD;
		public static final Map<Object, Object> APPLICATION_I18N_MAPPER = I18N.MAPPER;
		public static final String APPLICATION_TEMPORARY_PATH           = System.getProperty("user.dir");
		
		public static final int RESULT_SUCCESS   = 0;
		public static final int RESULT_NOT_FOUND = -99;
		
	}
	
	public static final class PERSISTENCE {
		public static final String TABLE_NAME_FIELD = "NAME";
		public static final String TABLE_ID_FIELD   = "ID";
		
		public static final String CONNECTION_PREFIX = "persistence.connection.";
		public static final String CONNECTION_FACTORY_PROPERTY = CONNECTION_PREFIX + "factory";
		
		public static final String DRIVER_PROPERTY   = CONNECTION_PREFIX + "driver";
		public static final String URL_PROPERTY      = CONNECTION_PREFIX + "url";
		public static final String USERNAME_PROPERTY = CONNECTION_PREFIX + "username";
		public static final String PASSWORD_PROPERTY = CONNECTION_PREFIX + "password";
		
		public static final String JNDI_PROPERTY = CONNECTION_PREFIX + "jndi";
		
		public static final String PROPERTY_PREFIX = "persistence.property.";
		
		public static final String SQL_PRETTY = "sql.pretty";
		
		public static final String LINE_SEPARATOR = "\n";
		
	}
	
	public static final class SQL {
		public static final String VALUES = "VALUES ";
		
		public static final Column<Date> CURRENT_TIMESTAMP = new Column<Date>(1, Date.class);
	}
	
	public static final class PATTERN {
		public static final String ISO_DATE      = "yyyy-MM-dd";
		public static final String ISO_DATE_TIME = "yyyy-MM-dd-HH:mm";
	}
	
	public static final class FORMATTERS {
		/**
		 * Quatro dígitos decimais, inclusive zeros à esquerda.
		 */
		public static final DecimalFormat DECIMAL_4DIGITOS = new DecimalFormat("0000");
		
		/**
		 * Quatro dígitos decimais, inclusive zeros à esquerda.
		 */
		public static final DecimalFormat DECIMAL_5DIGITOS = new DecimalFormat("00000");
		
		/**
		 * Data completa sem caracteres separadores.
		 */
		public static final DateFormat DATE_NO_CHAR_SEP = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
	}
	
	public static final class VERBOSE {
		public static final int MAXIMO = 9999;
		private static final Map<Integer, Object> MODIFIABLE_UNIDADE = new HashMap<Integer, Object>();
		static{
			MODIFIABLE_UNIDADE.put(INTEGER._1, I18N.UM);
			MODIFIABLE_UNIDADE.put(INTEGER._2, I18N.DOIS);
			MODIFIABLE_UNIDADE.put(INTEGER._3, I18N.TRES);
			MODIFIABLE_UNIDADE.put(INTEGER._4, I18N.QUATRO);
			MODIFIABLE_UNIDADE.put(INTEGER._5, I18N.CINCO);
			MODIFIABLE_UNIDADE.put(INTEGER._6, I18N.SEIS);
			MODIFIABLE_UNIDADE.put(INTEGER._7, I18N.SETE);
			MODIFIABLE_UNIDADE.put(INTEGER._8, I18N.OITO);
			MODIFIABLE_UNIDADE.put(INTEGER._9, I18N.NOVE);
		};
		public static final Map<Integer, Object> UNIDADE = Collections.unmodifiableMap(MODIFIABLE_UNIDADE);
		
		private static final Map<Integer, Object> MODIFIABLE_DEZENA = new HashMap<Integer, Object>();
		static{
			MODIFIABLE_DEZENA.put(INTEGER._10, I18N.DEZ);
			MODIFIABLE_DEZENA.put(INTEGER._11, I18N.ONZE);
			MODIFIABLE_DEZENA.put(INTEGER._12, I18N.DOZE);
			MODIFIABLE_DEZENA.put(INTEGER._13, I18N.TREZE);
			MODIFIABLE_DEZENA.put(INTEGER._14, I18N.QUATORZE);
			MODIFIABLE_DEZENA.put(INTEGER._15, I18N.QUINZE);
			MODIFIABLE_DEZENA.put(INTEGER._16, I18N.DEZESSEIS);
			MODIFIABLE_DEZENA.put(INTEGER._17, I18N.DEZESETE);
			MODIFIABLE_DEZENA.put(INTEGER._18, I18N.DEZOITO);
			MODIFIABLE_DEZENA.put(INTEGER._19, I18N.DEZENOVE);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._2, I18N.VINTE);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._3, I18N.TRINTA);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._4, I18N.QUARENTA);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._5, I18N.CINQUENTA);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._6, I18N.SESSENTA);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._7, I18N.SETENTA);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._8, I18N.OITENTA);
			MODIFIABLE_DEZENA.put(INTEGER._10 * INTEGER._9, I18N.NOVENTA);
		};
		public static final Map<Integer, Object> DEZENA = Collections.unmodifiableMap(MODIFIABLE_DEZENA);
		
		private static final Map<Integer, Object> MODIFIABLE_CENTENA = new HashMap<Integer, Object>();
		static{
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10, I18N.CEM);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._2, I18N.DUZENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._3, I18N.TREZENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._4, I18N.QUATROCENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._5, I18N.QUINHENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._6, I18N.SEISCENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._7, I18N.SETECENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._8, I18N.OITOCENTOS);
			MODIFIABLE_CENTENA.put(INTEGER._10 * INTEGER._10 * INTEGER._9, I18N.NOVECENTOS);
		};
		public static final Map<Integer, Object> CENTENA = Collections.unmodifiableMap(MODIFIABLE_CENTENA);
	}
}

