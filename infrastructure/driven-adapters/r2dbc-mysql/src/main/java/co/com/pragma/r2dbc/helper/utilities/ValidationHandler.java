package co.com.pragma.r2dbc.helper.utilities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class ValidationHandler {
    private final Validator validator;

    public ValidationHandler(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validate(T object) {
        //se ejecuta solo cuando se suscriba
        return Mono.defer(() -> {
            //valida el objeto de acuerdo a las restricciones que tiene el objeto
            Set<ConstraintViolation<T>> violations = validator.validate(object);
            if (!violations.isEmpty()) {
                //si hay alguna excepción lo retorna
                return Mono.error(new ConstraintViolationException(violations));
            }
            return Mono.just(object);
        });
    }
}
