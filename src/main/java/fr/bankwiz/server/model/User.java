package fr.bankwiz.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    protected Integer userId;
    private String firstName;
    private String lastName;
    private String email;

}
