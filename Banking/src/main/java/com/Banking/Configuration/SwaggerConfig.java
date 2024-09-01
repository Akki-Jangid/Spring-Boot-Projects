package com.Banking.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "State Bank of India Banking Application",
                description = "Implementing the Banking Functionalities",
                summary = "Multiple Entites (USER, BRANCH, ACCOUNT, TRANSACTION, LOAN AND LOAN_PAYMENT)",
                contact = @Contact(
                        name = "Aakash Jangid",
                        email = "akkijangid7678@gmail.com"
                ),
                version = "v3"
        ), tags = {
                @Tag(name = "User Authentication API", description = "Operations related to Users Authentication"),
                @Tag(name = "User API", description = "Operations related to Users"),
                @Tag(name = "Bank Branch API", description = "Operations related to Bank Branch"),
                @Tag(name = "Account API", description = "Operations related to Accounts"),
                @Tag(name = "Transaction API", description = "Operations related to Transactions"),
                @Tag(name = "Loan API", description = "Operations related to Loan"),
                @Tag(name = "Loan Payment API", description = "Operations related to Loan Payments")
        }, security = {@SecurityRequirement(name = "bearerToken")})
            @SecuritySchemes({
            @SecurityScheme(name = "bearerToken",
                    type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")}
)
public class SwaggerConfig {
}
