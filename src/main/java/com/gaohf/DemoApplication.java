package com.gaohf;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA监听器
@ComponentScan
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix="datasource.primary")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test2?characterEncoding=utf-8");
		ds.setUsername("gaohaifeng");
		ds.setPassword("123456");

		ds.setTestWhileIdle(true);
		ds.setTestOnBorrow(true);
		ds.setTestOnReturn(true);
		ds.setValidationQuery("SELECT 1");

		return ds;
	}

	/**
	 * 使用本机的redis三主三从集群
	 * @return
	 */
	@Bean
	public RedissonClient redissonClient(){
		Config config=new Config();
		//可以直接指定一主来获取集群
		config.useClusterServers().addNodeAddress("127.0.0.1:7000");
//		config.useClusterServers().addNodeAddress("127.0.0.1:7000","127.0.0.1:7001","127.0.0.1:7002","127.0.0.1:7003","127.0.0.1:7004","127.0.0.1:7005");
		return Redisson.create(config);
	}
}
