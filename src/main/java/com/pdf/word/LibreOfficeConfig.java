//package com.pdf.word;
//
//import org.jodconverter.core.office.OfficeManager;
//import org.jodconverter.local.office.LocalOfficeManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LibreOfficeConfig {
//
//	@Bean(initMethod = "start", destroyMethod = "stop")
//	public OfficeManager officeManager() {
//	    return LocalOfficeManager.builder()
//	            .officeHome("C:\\Program Files\\LibreOffice")
//	            .portNumbers(2002)
//	            .install()
//	            .build();
//	}
//
//}
