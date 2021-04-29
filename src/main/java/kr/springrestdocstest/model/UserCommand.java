package kr.springrestdocstest.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCommand {

    private String account;
    private String phoneNumber;
    private String email;

}
