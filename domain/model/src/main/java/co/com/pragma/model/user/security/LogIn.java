package co.com.pragma.model.user.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LogIn {
    String email;
    String password;
}
