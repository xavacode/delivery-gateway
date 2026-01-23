package com.hawa.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    private static final String SCHEME_NAME = "bearerAuth";
    private static final String BEARER_FORMAT = "JWT";
    private static final String SCHEME = "bearer";

//    @Bean
//    public OperationCustomizer customize() {
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//            List<String> tags = operation.getTags();
////            if (tags != null && tags.contains("Login")) {
////                operation.setSecurity(Collections.emptyList());
////            }
//            if (operation.getSummary().equals(OpenApiDocumentationConstant.Security.LOGIN)) {
//                operation.setSecurity(Collections.emptyList());
//            }
//            return operation;
//        };
//    }

    @Bean
    public OpenAPI defineOpenApi() {
        Contact myContact = new Contact();
        myContact.setName("admin");
        myContact.setEmail("admin@hawa.com");

        Info information = new Info()
                .title("Delivery Gateway API")
                .version("1.0")
                .description(
                        "This API exposes endpoints to manage deliveries.")
                .contact(myContact);
        return new OpenAPI().info(information);

    }

}
