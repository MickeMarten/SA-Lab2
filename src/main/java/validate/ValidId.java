package validate;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NotNull(message = "id cannot be null")
@Min(value = 1, message = "Id must be greater than 0")
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidId {
    String message() default  "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
