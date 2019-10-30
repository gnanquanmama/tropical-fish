package com.mcoding.util.valid;


import org.hibernate.validator.HibernateValidator;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

/**
 * @author wzt on 2019/10/30.
 * @version 1.0
 */
public class Validators {

    private static ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
            .configure()
            .addProperty( "hibernate.validator.fail_fast", "true" )
            .buildValidatorFactory();

    private static Validator getValidator() {
        return validatorFactory.getValidator();
    }

    public static <T> Optional<String> validate(T obj) {
        Set<ConstraintViolation<Object>> validateResult = getValidator().validate(obj);
        if (CollectionUtils.isEmpty(validateResult)) {
            return Optional.empty();
        }

        return validateResult.stream().findFirst().map(ConstraintViolation::getMessage);
    }
}
