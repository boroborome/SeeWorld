package com.happy3w.seeworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
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
