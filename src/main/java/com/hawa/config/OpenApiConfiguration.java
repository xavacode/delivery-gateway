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
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Local");

        Server server1 = new Server();
        server1.setUrl("http://ec2-3-27-3-52.ap-southeast-2.compute.amazonaws.com:8080");
        server1.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("admin");
        myContact.setEmail("admin@hawa.com");

        Info information = new Info()
                .title("Delivery Gateway API")
                .version("1.0")
                .description(
                        "This API exposes endpoints to manage configurations.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server, server1));

    }

}
