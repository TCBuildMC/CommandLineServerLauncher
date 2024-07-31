package xyz.tcbuildmc.minecraft.server.clsl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Extension {
    String id();
    String name() default "";
    String version();
    String description() default "";
    String[] authors() default {};
    String[] contributors() default {};
    String url() default "";
    ExtensionDependency[] dependencies() default {};
}
