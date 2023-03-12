package com.ykmxxi.aligong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ykmxxi.aligong.repository.EventRepository;

@Configuration
public class RepositoryConfig {

	@Bean
	public EventRepository eventRepository() {
		return new EventRepository() {
		};
	}
}
