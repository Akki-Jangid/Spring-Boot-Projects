package com.Irctc.Model.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Tokens")
@Getter
@Setter
@NoArgsConstructor
public class Token {

    @Id
    public String id;

    private String username;

    public String token;

    public Token(String token, String username) {
        this.username = username;
        this.token = token;
    }

}
