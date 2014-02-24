package com.javaf.javase.persistence.example;

import java.math.BigDecimal;
import java.util.Date;

import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Id;
import com.javaf.javase.persistence.Table;



public final class Tables {

	private Tables(){
		
	}
	
	public static final class TCTRL_IMAGE_DIGIT extends Table {
		private TCTRL_IMAGE_DIGIT(){
			
		}
		
		public static final String NAME = "DB2PRD.TCTRL_IMAGE_DIGIT";
		
		public static final Column<Long>    NCTRL_IMAGE_DIGIT   = new Column<Long>("NCTRL_IMAGE_DIGIT",     Long.class,    false);
		public static final Column<Long>    CPSSOA              = new Column<Long>("CPSSOA",                Long.class,    false);
		public static final Column<String>  CIDTFD_IMAGE_DIGIT  = new Column<String>("CIDTFD_IMAGE_DIGIT",  String.class,  50);
		public static final Column<Long>    CPSSOA_JURID        = new Column<Long>("CPSSOA_JURID",          Long.class,    false);
		public static final Column<Long>    NSEQ_UND_ORGNZ      = new Column<Long>("NSEQ_UND_ORGNZ",        Long.class,    false);
		public static final Column<Integer> CIDTFD_CONCS_PODER  = new Column<Integer>("CIDTFD_CONCS_PODER", Integer.class, false);
		public static final Column<BigDecimal> QIMAGE_DIGIT     = new Column<BigDecimal>("QIMAGE_DIGIT",    BigDecimal.class);
		public static final Column<BigDecimal> QIMAGE_RECBD     = new Column<BigDecimal>("QIMAGE_RECBD",    BigDecimal.class);
		public static final Column<Date>    HINCL_REG           = new Column<Date>("HINCL_REG",             Date.class,    false);
		public static final Column<String>  CUSUAR_INCL         = new Column<String>("CUSUAR_INCL",         String.class,  9, false);
		public static final Column<Date>    HMANUT_REG          = new Column<Date>("HMANUT_REG",            Date.class);
		public static final Column<String>  CUSUAR_MANUT        = new Column<String>("CUSUAR_MANUT",        String.class,  9);
		
		public static final Id ID = new Id(NCTRL_IMAGE_DIGIT);
		
	}

	public static final class TDOCTO_PSSOA_PODER extends Table {
		private TDOCTO_PSSOA_PODER(){
			
		}
		
		public static final String NAME = "DB2PRD.TDOCTO_PSSOA_PODER";
		
		public static final Column<Long>    CPSSOA             = new Column<Long>("CPSSOA", Long.class, false);
		public static final Column<String>  CIDTFD_DOCTO_REP   = new Column<String>("CIDTFD_DOCTO_REP", String.class, 50, false);
		public static final Column<Long>    CDOCTO             = new Column<Long>("CDOCTO", Long.class, false);
		public static final Column<Long>    CPSSOA_JURID       = new Column<Long>("CPSSOA_JURID", Long.class, false);
		public static final Column<Long>    NSEQ_UND_ORGNZ     = new Column<Long>("NSEQ_UND_ORGNZ", Long.class, false);
		public static final Column<String>  RINFO_DOCTO_CONCS  = new Column<String>("RINFO_DOCTO_CONCS", String.class, 256, false);
		public static final Column<Integer> CSIT_DOCTO_CONCS   = new Column<Integer>("CSIT_DOCTO_CONCS", Integer.class);
		public static final Column<Date>    DINIC_VGCIA_DOCTO  = new Column<Date>("DINIC_VGCIA_DOCTO", Date.class);
		public static final Column<Date>    DFIM_VGCIA_DOCTO   = new Column<Date>("DFIM_VGCIA_DOCTO", Date.class);
		public static final Column<Date>    HINCL_REG          = new Column<Date>("HINCL_REG", Date.class, false);
		public static final Column<String>  CUSUAR_INCL        = new Column<String>("CUSUAR_INCL", String.class, 9, false);
		public static final Column<Date>    HMANUT_REG         = new Column<Date>("HMANUT_REG", Date.class);
		public static final Column<String>  CUSUAR_MANUT       = new Column<String>("CUSUAR_MANUT", String.class, 9);
		public static final Column<String>  CTPO_MANUT         = new Column<String>("CTPO_MANUT", String.class, 1, false);
		public static final Column<Integer> CINDCD_DISPN_IMAGE = new Column<Integer>("CINDCD_DISPN_IMAGE", Integer.class);
		
		public static final Id ID = new Id(CPSSOA, CIDTFD_DOCTO_REP);
		
	}
	
	public static final class THIST_DOCTO_PODER extends Table {
		private THIST_DOCTO_PODER(){
			
		}
		
		public static final String NAME = "DB2PRD.THIST_DOCTO_PODER";
		
		public static final Column<Long>    CPSSOA             = new Column<Long>("CPSSOA", Long.class, false);
		public static final Column<String>  CIDTFD_DOCTO_REP   = new Column<String>("CIDTFD_DOCTO_REP", String.class, 50, false);
		public static final Column<Date>    HINCL_REG_HIST     = new Column<Date>("HINCL_REG_HIST", Date.class, false);
		public static final Column<Long>    CDOCTO             = new Column<Long>("CDOCTO", Long.class, false);
		public static final Column<Long>    CPSSOA_JURID       = new Column<Long>("CPSSOA_JURID", Long.class, false);
		public static final Column<Long>    NSEQ_UND_ORGNZ     = new Column<Long>("NSEQ_UND_ORGNZ", Long.class, false);
		public static final Column<String>  RINFO_DOCTO_CONCS  = new Column<String>("RINFO_DOCTO_CONCS", String.class, 256, false);
		public static final Column<Integer> CSIT_DOCTO_CONCS   = new Column<Integer>("CSIT_DOCTO_CONCS", Integer.class);
		public static final Column<Date>    HINCL_REG          = new Column<Date>("HINCL_REG", Date.class, false);
		public static final Column<String>  CUSUAR_INCL        = new Column<String>("CUSUAR_INCL", String.class, 9, false);
		public static final Column<Date>    HMANUT_REG         = new Column<Date>("HMANUT_REG", Date.class);
		public static final Column<String>  CUSUAR_MANUT       = new Column<String>("CUSUAR_MANUT", String.class, 9);
		public static final Column<String>  CTPO_MANUT         = new Column<String>("CTPO_MANUT", String.class, 1, false);
		public static final Column<Date>    DFIM_VGCIA_DOCTO   = new Column<Date>("DFIM_VGCIA_DOCTO", Date.class);
		public static final Column<Date>    DINIC_VGCIA_DOCTO  = new Column<Date>("DINIC_VGCIA_DOCTO", Date.class);
		public static final Column<Integer> CINDCD_DISPN_IMAGE = new Column<Integer>("CINDCD_DISPN_IMAGE", Integer.class);
		
		public static final Id ID = new Id(CPSSOA, CIDTFD_DOCTO_REP, HINCL_REG_HIST);
		
	}

}
