package org.example.clothingrecommendationsystem.infrastructure.security;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InternalOnly {
}

