package com.javaf.javase.persistence.example;

import com.javaf.javase.persistence.IManager;
import com.javaf.javase.persistence.IQuery;
import com.javaf.javase.persistence.IResult;
import com.javaf.javase.persistence.IResults;
import com.javaf.javase.persistence.ITransaction;
import com.javaf.javase.persistence.ManagerFactory;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.Read;
import com.javaf.javase.persistence.example.Tables.TCTRL_IMAGE_DIGIT;


public class ExampleRead {

	public static void main(String[] args) {
	
		final ManagerFactory _factory   = ManagerFactory.getInstance();
		final IManager _manager         = _factory.createManager();
		final ITransaction _transaction = _manager.getTransaction();
		
		final Read _read = new Read();
		_read.select(TCTRL_IMAGE_DIGIT.CPSSOA, TCTRL_IMAGE_DIGIT.CIDTFD_IMAGE_DIGIT)
			 .from(TCTRL_IMAGE_DIGIT.class)
			 .where(TCTRL_IMAGE_DIGIT.CUSUAR_INCL)
			 	.eq("I901864");
		
		try{
			_transaction.begin();

			final IQuery _query     = _manager.createQuery(_read);
			final IResults _results = _query.execute();
			
			while(_results.hasNext()){
				final IResult _result = _results.next();
				System.out.println(_result.get(TCTRL_IMAGE_DIGIT.CPSSOA));
				System.out.println(_result.get(TCTRL_IMAGE_DIGIT.CIDTFD_IMAGE_DIGIT));
			}
			
			_transaction.commit();
			
		}catch(PersistenceException _e){
			_transaction.rollback();
			
			_e.printStackTrace();
			
		}finally{
			
			_manager.close();
		}
		
	}

}
