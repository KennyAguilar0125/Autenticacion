package co.com.pragma.api.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
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

    public <T> Mono<List<T>> validateList(List<T> list) {
        // Validamos cada elemento de la lista
        for (T item : list) {
            Set<ConstraintViolation<T>> violations = validator.validate(item);
            if (!violations.isEmpty()) {
                // Si hay violaciones, lanzamos la excepción
                return Mono.error(new ConstraintViolationException(violations));
            }
        }
        return Mono.just(list);  // Si todos son válidos, retornamos la lista original
    }
}
