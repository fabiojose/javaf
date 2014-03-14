package bradesco.tf.outside.cadu;

import java.util.ArrayList;
import java.util.List;

import br.com.bradesco.core.aq.service.TransferBean;
import bradesco.tf.TransferBeanWrapper;
import bradesco.tf.TFConstants.CADUV2;

import com.javaf.Application;
import com.javaf.Constants.APPLICATION;
import com.javaf.brazil.Pessoa;
import com.javaf.pattern.ICommand2;

/**
 * Implemento atributos necessários para chamada CADUV 2. 
 * @author fabiojm - Fábio José de Moraes
 */
public final class CaduV2CallingDTO implements TransferBean {
	private static final long serialVersionUID = -6482654291416781131L;

	private String sistema;
	private boolean externo;
	private boolean permitirCadastro;
	private String produto;
	private int tela;
	private boolean identificado;
	
	private boolean validarCapacidade;
	
	private Pessoa pessoa;
	
	private TransferBeanWrapper wrapper;
	
	private List<ICommand2<? extends Pessoa>> afterID;
	
	public CaduV2CallingDTO(){
		setSistema(Application.getInstance().valueOf(String.class, APPLICATION.CODE_PROPERTY));
		setExterno(Boolean.TRUE);
		setPermitirCadastro(Boolean.TRUE);
		setTela(CADUV2.TELA_CADASTRO_GERAL);
		setValidarCapacidade(Boolean.FALSE);
		
		setPessoa(new Pessoa());
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	public boolean isIdentificado() {
		return identificado;
	}

	public void setIdentificado(boolean identificado) {
		this.identificado = identificado;
	}

	public boolean isPermitirCadastro() {
		return permitirCadastro;
	}

	public void setPermitirCadastro(boolean permitirCadastro) {
		this.permitirCadastro = permitirCadastro;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public int getTela() {
		return tela;
	}

	public void setTela(int tela) {
		this.tela = tela;
	}

	public boolean isValidarCapacidade() {
		return validarCapacidade;
	}

	public void setValidarCapacidade(boolean validarCapacidade) {
		this.validarCapacidade = validarCapacidade;
	}

	public TransferBeanWrapper getWrapper() {
		if(null== wrapper){
			wrapper = new TransferBeanWrapper();
		}
		return wrapper;
	}

	public void setWrapper(TransferBeanWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public List<ICommand2<? extends Pessoa>> getAfterID() {
		if(null== afterID){
			afterID = new ArrayList<ICommand2<? extends Pessoa>>();
		}
		
		return afterID;
	}

	/**
	 * Configurar lista de comandos para execução após a IDentificação da Pessoa na tela componente do CADUv2
	 * @param afterID
	 */
	public void setAfterID(List<ICommand2<? extends Pessoa>> afterID) {
		this.afterID = afterID;
	}
	
}
