package ru.finex.core.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import ru.finex.core.pool.impl.SimplePooledObjectFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация указывает, что для аннотированного класса будет создан пул.
 * Класс должен иметь стандартный конструктор.
 *
 * @author m0nster.mind
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PooledObject {

    /**
     * Начальный размер пула.
     * <p>
     * Supports expression.
     * @see ru.finex.core.placeholder.PlaceholderService
     */
    String minSize() default "0";

    /**
     * Максимальный размер пула, для хранения объектов. 0 - не ограничен.
     * <p>
     * Supports expression.
     * @see ru.finex.core.placeholder.PlaceholderService
     */
    String maxSize() default "0";

    /**
     * Автоматическое создание элементов в пуле.
     */
    boolean autoCreate() default true;

    Class<? extends PooledObjectFactory> factory() default SimplePooledObjectFactory.class;

}
