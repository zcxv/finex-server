package ru.finex.core.repository;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies query name that method use. Query is must exist in mapping resources with specified name.
 * By default, if name is not set, query name generated by pattern: {@code SimpleClassName.methodName}.
 * @author m0nster.mind
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedQuery {

    /**
     * Name of query, saved in mapping resources, that method use.
     * @return query name
     */
    String value() default "";

}
