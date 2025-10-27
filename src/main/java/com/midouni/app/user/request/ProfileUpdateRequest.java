package com.midouni.app.user.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfile {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
