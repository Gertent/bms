package com.rmd.bms.provider;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ImportResource("classpath:applicationContext.xml")
public class BMSApplication {

	// private static final Logger logger =
	// LoggerFactory.getLogger(WMSApplication.class);

	@Bean
	public CountDownLatch closeLatch() {
		return new CountDownLatch(1);
	}

	/*
	 * @Bean
	 * 
	 * @ConditionalOnExpression("${spring.datasource.jmxEnabled:true}") public
	 * ConnectionPool jdbcPool(DataSource dataSource) throws SQLException {
	 * return dataSource.createPool().getJmxPool(); }
	 */

	public static void main(String[] args) throws Exception {

		ApplicationContext ctx = new SpringApplicationBuilder()
				.sources(BMSApplication.class).web(false).run(args);
		System.out.println("项目启动");
		// logger.info("项目启动!");
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		//SpringApplication.run(WMSApplication.class, args);

		CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
		closeLatch.await();
	}
}
