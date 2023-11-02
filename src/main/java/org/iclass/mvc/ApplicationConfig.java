package org.iclass.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
	private String urlPath="/uploads/**";		//http 프로토콜로 접근할 url
	private String location="file:///d:/iclass0419/upload/";   //로컬(서버)컴퓨터의 경로. 반드시 경로 마지막 /필요함

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(urlPath)
				.addResourceLocations(location);
		
	}
	//톰캣 server.xml 에서 서버로컬의 폴더에 대해 url 지정과 같은 작업.
}
