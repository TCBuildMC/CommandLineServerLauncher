package xyz.tcbuildmc.minecraft.server.clsl.annotation;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExtensionDependency {
    String id();
    @ApiStatus.Experimental
    String version() default "";
    boolean optional() default false;
}
