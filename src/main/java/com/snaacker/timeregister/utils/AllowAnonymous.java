package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.persistent.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowAnonymous {

  Role roleType() default Role.ANONYMOUS;
}
