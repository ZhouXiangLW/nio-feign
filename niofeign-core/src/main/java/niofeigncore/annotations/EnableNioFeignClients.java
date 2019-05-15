package niofeigncore.annotations;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import niofeigncore.register.NioFeignClientRegister;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(NioFeignClientRegister.class)
public @interface EnableNioFeignClients {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] clients() default {};
    
}
