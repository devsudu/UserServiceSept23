package dev.sudu.userservicesept23.dtos;

import dev.sudu.userservicesept23.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class CommonResponseDto {
    private String message;
    private HttpStatusCode code;
    private Status status;
    private Object data;
}
