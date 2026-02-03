package com.hawa.annotation;

import com.hawa.generator.SnowflakeIdGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@IdGeneratorType(SnowflakeIdGenerator.class)
public @interface SnowflakeId {}