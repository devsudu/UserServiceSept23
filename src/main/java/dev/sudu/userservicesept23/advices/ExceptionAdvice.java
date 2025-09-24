package dev.sudu.userservicesept23.advices;

import dev.sudu.userservicesept23.dtos.CommonResponseDto;
import dev.sudu.userservicesept23.enums.Status;
import dev.sudu.userservicesept23.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponseDto> runtimeExceptionAdvice(Exception e){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setStatus(Status.SUCCESS);
        commonResponseDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
        commonResponseDto.setMessage(e.getMessage());

        return new ResponseEntity<CommonResponseDto>(
                commonResponseDto,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({UserAlreadyRegisteredException.class, InvalidTokenException.class, PasswordMismatchException.class, MandatoryFieldException.class, InvalidRoleException.class})
    public ResponseEntity<CommonResponseDto> customExceptionAdvice(Exception e){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setStatus(Status.SUCCESS);
        commonResponseDto.setCode(HttpStatus.OK);
        commonResponseDto.setMessage(e.getMessage());

        return new ResponseEntity<CommonResponseDto>(
                commonResponseDto,
                HttpStatus.OK
        );
    }
}
