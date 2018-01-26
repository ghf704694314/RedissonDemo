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
	 * 使用dev的redis单节点集群
	 * @return
	 */
	@Bean
	public RedissonClient redissonClient(){
		Config config=new Config();
		config.useSingleServer().setAddress("redis.ops.com:6400").setDatabase(30).setPassword("tbbdev");
		return Redisson.create(config);
	}
}
