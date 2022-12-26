package com.laijava.aspect;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving.AUTODETECT;

@Configuration
@EnableLoadTimeWeaving(aspectjWeaving = AUTODETECT)
@EnableAsync(mode = AdviceMode.ASPECTJ)
public class LoadTimeWeavingConfig {
}
