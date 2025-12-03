package com.pdf.word;

import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JodConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public OfficeManager officeManager() {
        return LocalOfficeManager.builder()
                .officeHome("/usr/lib/libreoffice")  // Linux path for Render
                .portNumbers(2002)
                .build();
    }
}
