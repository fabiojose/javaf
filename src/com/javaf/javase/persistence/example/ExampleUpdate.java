package com.javaf.javase.persistence.example;

import com.javaf.javase.persistence.IManager;
import com.javaf.javase.persistence.ITransaction;
import com.javaf.javase.persistence.ManagerFactory;
import com.javaf.javase.persistence.NativeType;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.Update;
import com.javaf.javase.persistence.example.Tables.TCTRL_IMAGE_DIGIT;


public class ExampleUpdate {

	public static void main(String[] args) {
		
		final ManagerFactory _factory   = ManagerFactory.getInstance();
		final IManager _manager         = _factory.createManager();
		final ITransaction _transaction = _manager.getTransaction();
		
		final Update _update = new Update();
		_update.update(TCTRL_IMAGE_DIGIT.class)
			   .set(TCTRL_IMAGE_DIGIT.CUSUAR_MANUT, "I999666")
			   .set(TCTRL_IMAGE_DIGIT.HMANUT_REG,   NativeType.NOW)
			   .where(TCTRL_IMAGE_DIGIT.NCTRL_IMAGE_DIGIT)
			   		.eq(8888888L);
		
		try{
			_transaction.begin();

			_manager.refresh(_update);
			
			_transaction.commit();
			
		}catch(PersistenceException _e){
			_transaction.rollback();
			
			_e.printStackTrace();
			
		}finally{
			
			_manager.close();
		}
		
	}

}
