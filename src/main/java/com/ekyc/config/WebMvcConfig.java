package com.ekyc.config;
import java.util.*;
import java.util.stream.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.function.*;
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
  @Override
  public void addCorsMappings(CorsRegistry registry) {
      WebMvcConfigurer.super.addCorsMappings(registry);
      registry.addMapping("*").allowedOrigins("*");
  }
}
