package com.javaf.javase.persistence.example;

import java.util.Date;

import com.javaf.javase.persistence.Create;
import com.javaf.javase.persistence.IManager;
import com.javaf.javase.persistence.ITransaction;
import com.javaf.javase.persistence.ManagerFactory;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.example.Tables.TCTRL_IMAGE_DIGIT;



public final class ExampleCreate {

	public static void main(String[] args) {
		
		final ManagerFactory _factory   = ManagerFactory.getInstance();
		final IManager _manager         = _factory.createManager();
		final ITransaction _transaction = _manager.getTransaction();
		
		final Create _insert = new Create();
		
		_insert
			.into(TCTRL_IMAGE_DIGIT.class)
			.value(TCTRL_IMAGE_DIGIT.NCTRL_IMAGE_DIGIT,  8888877L)
			.value(TCTRL_IMAGE_DIGIT.CPSSOA,             1666L)
			.value(TCTRL_IMAGE_DIGIT.CIDTFD_IMAGE_DIGIT, "CID:888778889855454")
			.value(TCTRL_IMAGE_DIGIT.CPSSOA_JURID ,      2666L)
			.value(TCTRL_IMAGE_DIGIT.NSEQ_UND_ORGNZ,     4666L)
			.value(TCTRL_IMAGE_DIGIT.CIDTFD_CONCS_PODER, 1)
			.value(TCTRL_IMAGE_DIGIT.HINCL_REG,          new Date())
			.value(TCTRL_IMAGE_DIGIT.CUSUAR_INCL,        "I901864");
		
		try{
			_transaction.begin();

			_manager.persist(_insert);
			
			_transaction.commit();
			
		}catch(PersistenceException _e){
			_transaction.rollback();
			
			_e.printStackTrace();
			
		}finally{
			
			_manager.close();
		}
	}

}
