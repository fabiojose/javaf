package bradesco.tf.force;

import javax.swing.JLabel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import bradesco.tf.TFConstants.DSN;
import bradesco.tf.dsn.UtilDSN;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings({"deprecation"})
public class ForceDialogTitleListener implements InternalFrameListener {

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
