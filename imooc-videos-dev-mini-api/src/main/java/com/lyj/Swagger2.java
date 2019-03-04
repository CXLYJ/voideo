package com.lyj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 作者 lyj
 * @version 创建时间：2018年11月6日 下午3:58:09
 * 开启Swagger2
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	/**
	 * @Description:swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 */
	@Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建api文档的详细信息的方法：apiInfo()
                .apiInfo(apiInfo())
                .select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.lyj.controller"))
                .paths(PathSelectors.any())
                .build();
    }

	/**
	 * @Description: 构建 api文档的信息
	 */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("Spring Boot集成Swagger2接口总览")
                // 设置接口描述
                .description("跟金哥一起学学习")
                // 设置联系方式
                .contact("李依金，" + "博客：http://iclyj.cn")
                // 设置版本
                .version("1.0")
                // 构建
                .build();
    }
	
}
