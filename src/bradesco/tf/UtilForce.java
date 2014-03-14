package bradesco.tf;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.aq.service.IServiceProvider;
import br.com.bradesco.core.serviceImpl.ServiceProvider;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import br.com.bradesco.core.ui.beans.basic.BradescoLabel;
import br.com.bradesco.core.ui.beans.containers.BradescoPanel;
import br.com.bradesco.core.windowengine.IArquitecturalWindow;
import br.com.bradesco.core.windowengine.IWindowEngine;
import br.com.bradesco.core.windowengine.WindowEngine;
import bradesco.tf.TFConstants.DSN;
import bradesco.tf.TFConstants.IDENTIFY;
import bradesco.tf.dsn.UtilDSN;
import bradesco.tf.layout.UtilLayout;

import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class UtilForce {
	static final UtilForce INSTANCE = new UtilForce();
	private static final String NORTH  = "North";
	private static final String CENTER = "Center";
	
	private final UtilReflection reflection;
	private final UtilString string;
	private final UtilLayout layout;
	private final ILogging logging = Logging.loggerOf(getClass());
	private UtilForce(){
		reflection = UtilReflection.getInstance();
		string     = UtilString.getInstance();
		layout     = UtilLayout.getInstance();
	}
	
	public static synchronized UtilForce getInstance(){
		return INSTANCE;
	}
	
	private BradescoDialog getDialog(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		BradescoDialog _result = null;
		
		final IServiceProvider _service = ServiceProvider.getInstance();
		final IArquitecturalWindow _window = _service.getWindowEngine().getWindow(node);
		
		if(null!= _window){
			_result = (BradescoDialog)_window.getInternalComponent();
		}
		
		return _result;
	}
	
	JLabel getTitle(final BradescoDialog dialog){
		
		JLabel _result = null;
		
		if(dialog instanceof JInternalFrame){
			_result = getTitle((JInternalFrame)dialog);
		}
		
		return _result;
	}
	
	private JLabel getTitle(final JInternalFrame frame){
		
		JLabel _result = null;
		final Container _c = frame.getContentPane();
		if(_c instanceof JPanel){
			final JPanel _panel = (JPanel)_c;
			final LayoutManager _lm = _panel.getLayout();
			if(_lm instanceof BorderLayout){
				final BorderLayout _layout = (BorderLayout)_lm;
				final Component _component = _layout.getLayoutComponent(NORTH);
				
				if(_component instanceof JLabel){
					_result = (JLabel)_component;
				}
			}
		}
		return _result;
	}
	
	private List<Component> componentsOf(final BradescoDialog dialog){
		final List<Component> _result = new ArrayList<Component>();
		if(dialog instanceof JInternalFrame){
			final JInternalFrame _jif = (JInternalFrame)dialog;
			final Container _container = _jif.getContentPane();
			if(_container instanceof JPanel){
				final JPanel _panel     = (JPanel)_container;
				final LayoutManager _lm = _panel.getLayout();
				if(_lm instanceof BorderLayout){
					final BorderLayout _bl     = (BorderLayout)_lm;
					final Component _component = _bl.getLayoutComponent(CENTER);
					if(_component instanceof JScrollPane){
						final JScrollPane _spane = (JScrollPane)_component;
						final JViewport _view = _spane.getViewport();
						try{
							final Component __component = _view.getComponent(INTEGER._0);
							if(__component instanceof BradescoPanel){
								final BradescoPanel _bpanel = (BradescoPanel)__component;
								_bpanel.getComponents();
								_result.addAll(Arrays.asList(_bpanel.getComponents()));
							}
						}catch(ArrayIndexOutOfBoundsException _e){
						}
					}
				}
			}
		}
		
		return _result;
	}
	
	private List<BradescoLabel> labelsOf(final List<Component> components){
		final List<BradescoLabel> _result = new ArrayList<BradescoLabel>();
		
		for(Component _component : components){
			if(_component instanceof BradescoLabel){
				_result.add((BradescoLabel)_component);
				
			} else {
				
				final Object _olabel = reflection.valueOf(_component, IDENTIFY.LABEL, Boolean.FALSE);
				if(_olabel instanceof BradescoLabel){
					_result.add((BradescoLabel)_olabel);
				}
			}
		}
		
		return _result;
	}
	
	public void doLabelling(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		final BradescoDialog _dialog = getDialog(provider, operation, node);
		if(null!= _dialog){
			final List<BradescoLabel> _labels = labelsOf(componentsOf(_dialog));
			for(BradescoLabel _label : _labels){
				final String _text = _label.getText();
				if(string.hasHolder(_text)){
					logging.debug(I18N.APPLYING_LABELLING_HOLDERS + _text);
					
					final String __text = layout.holders(provider, operation, _text);
					_label.setTextValue(__text);
					
					logging.debug(I18N.LABELLING_HOLDERS_DONE + __text);
				}
			}
		}
	}
	
	public void setTitle(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String title){
		
		final BradescoDialog _dialog = getDialog(provider, operation, node);
		if(null!= _dialog){
			
			final JLabel _title = getTitle(_dialog);
			if(null!= _title){
				_title.setText(title);
				_title.setVisible(Boolean.TRUE);
			}
		}
		
	}
	
	public boolean isOpened(final Class<? extends BradescoDialog> layout){
		
		boolean _result = Boolean.FALSE;
		
		final IServiceProvider _service = ServiceProvider.getInstance();
		final IWindowEngine _engine = _service.getWindowEngine();
		if(_engine instanceof WindowEngine){
			final WindowEngine _wengine = (WindowEngine)_engine;

			//atualizar aquelas que já estavam abertas
			final List<IArquitecturalWindow> _opened = _wengine.getOpenedWindows();
			for(IArquitecturalWindow _window : _opened){
				if(layout.isAssignableFrom(_window.getClass())){
					_result = Boolean.TRUE;
					break;
				}
			}
		
		}
		
		return _result;
	}
	
	void inject(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		final IServiceProvider _service = ServiceProvider.getInstance();
		final IWindowEngine _engine = _service.getWindowEngine();
		if(_engine instanceof WindowEngine){
			final WindowEngine _wengine = (WindowEngine)_engine;

			//atualizar aquelas que já estavam abertas
			final List<IArquitecturalWindow> _opened = _wengine.getOpenedWindows();
			for(IArquitecturalWindow _window : _opened){
				if(_window.getInternalComponent() instanceof JInternalFrame){
					inject(_window);
				}
			}
			
			//injetar o proxy para a lista original de janelas abertas
			_wengine.setOpenedWindows(ForceDialogTitleProxy.newInstance(_opened));
		}
	}
	
	void inject(final IArquitecturalWindow window){
		
		final BradescoDialog _dialog = (BradescoDialog)window.getInternalComponent();
		final JInternalFrame _frame  = (JInternalFrame)_dialog;
		final JLabel _title = UtilForce.INSTANCE.getTitle(_dialog);
		
		if(null!= _title){
			if(!UtilForce.getInstance().hasListener(_frame)){
				_frame.addInternalFrameListener(new ForceDialogTitleListener(_title, _dialog));
			}
		}
	}
	
	boolean hasListener(final JInternalFrame frame){
		
		boolean _result = Boolean.FALSE;
		for(InternalFrameListener _listener : frame.getInternalFrameListeners()){
			if(_listener instanceof ForceDialogTitleListener){
				_result = Boolean.TRUE;
				break;
			}
		}
		
		return _result;
	}
}

class ForceDialogTitleProxy implements InvocationHandler {
	private static final String ADD = "add";
	private static final int ZERO = 0;
	private static final int ONE = 1;
	
	private List<IArquitecturalWindow> windows;
	public ForceDialogTitleProxy(final List<IArquitecturalWindow> windows){
		this.windows = windows;
	}
	
	@SuppressWarnings("unchecked")
	public static List<IArquitecturalWindow> newInstance(final List<IArquitecturalWindow> o){
		final Class<?> _clazz = o.getClass();
		
		return (List<IArquitecturalWindow>)Proxy.newProxyInstance(_clazz.getClassLoader(), _clazz.getInterfaces(), new ForceDialogTitleProxy(o));
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object _result = method.invoke(windows, args);
		
		if(ADD.equals(method.getName())){
			if(args.length == ONE){
				if(args[ZERO] instanceof IArquitecturalWindow){
					final IArquitecturalWindow _window = (IArquitecturalWindow)args[ZERO];
					
					if(_window.getInternalComponent() instanceof JInternalFrame){
						UtilForce.INSTANCE.inject(_window);
					}
				}
			}
		}
		return _result;
	}
	
}

@SuppressWarnings({"deprecation"})
class ForceDialogTitleListener implements InternalFrameListener {

	private final UtilDSN dsn; 
	private BradescoDialog frame;
	private JLabel title;
	
	public ForceDialogTitleListener(final JLabel title, final BradescoDialog frame){
		this.title = title;
		this.frame = frame;
		
		dsn = UtilDSN.getInstance();
	}
	
	public void internalFrameActivated(final InternalFrameEvent e) {
		
		final IDataServiceNode _node = frame.getOperation().getDataServiceNode();
		final ISimpleObject _path    = dsn.getOrAddSimpleObject(DSN.PATH, _node);
		
		title.setText(_path.getValue());
		title.setVisible(Boolean.TRUE);
	}

	public void internalFrameClosed(InternalFrameEvent e) {
	}

	public void internalFrameClosing(InternalFrameEvent e) {
	}

	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	public void internalFrameIconified(InternalFrameEvent e) {
	}

	public void internalFrameOpened(InternalFrameEvent e) {
	}

}
