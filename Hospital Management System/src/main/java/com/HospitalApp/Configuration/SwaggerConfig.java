package com.HospitalApp.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hospital Management System",
                description = "Implementing CRUD Operations",
                summary = "Multiple Entities (Hospital, Patient, AppUser, Staff) and Relationship is used between them",
                contact = @Contact(
                        name = "Aakash_Jangid",
                        email = "aakash@gmail.com"
                ),
                version = "v3"
        ),
        servers = {
                @Server(
                        description = "dev",
                        url = "http://localhost:2024"
                ),
                @Server(
                        description = "test",
                        url = "http://localhost:2024"
                )
        },
        security = {@SecurityRequirement(name = "bearerToken")})
        @SecuritySchemes({
        @SecurityScheme(name = "bearerToken",
                type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")}
)
public class SwaggerConfig {
}
