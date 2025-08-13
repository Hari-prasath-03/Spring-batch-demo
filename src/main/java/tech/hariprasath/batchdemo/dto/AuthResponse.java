package tech.hariprasath.batchdemo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class AuthResponse {

    private boolean success;
    private String token;
}
