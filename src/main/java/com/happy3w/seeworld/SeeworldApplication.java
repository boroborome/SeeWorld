package com.happy3w.seeworld;

import com.happy3w.seeworld.job.AnalyzeJob;
import com.happy3w.seeworld.job.DownloadJob;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "org/springframework/data/elasticsearch/repositories")
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class})
public class SeeWorldApplication {

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet(){
			@Override
			protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
				super.doService(request, response);
			}

			@Override
			protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
				super.doDispatch(request, response);
			}

			@Override
			protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
				super.render(mv, request, response);
			}

			@Override
			protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale, HttpServletRequest request) throws Exception {
				return super.resolveViewName(viewName, model, locale, request);
			}

			@Override
			protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				super.service(request, response);
			}
		};
	}

//	public final static String queueName = "spring-boot";

	@Bean
	Queue downloadQueue() {
		return new Queue(DownloadJob.Queue, false);
	}
	@Bean
	Queue analyzeQueue() {
		return new Queue(AnalyzeJob.Queue, false);
	}


//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange("spring-boot-exchange");
//	}
//
//	@Bean
//	Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(queueName);
//	}
//
//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//											 MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(queueName);
//		container.setMessageListener(listenerAdapter);
//		return container;
//	}

//	@Bean
//	MessageListenerAdapter listenerAdapter(Receiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}

//	@Bean
//	public ServletRegistrationBean dispatcherServletRegistration() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(
//				dispatcherServlet(), "/api/*");
//		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
//		return registration;
//	}

	public static void main(String[] args) {
		SpringApplication.run(SeeWorldApplication.class, args);
	}
}
