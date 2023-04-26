package tanneroconnor.dev.springdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("demo")
public record SpringDemoConfigProperties(String userName, String password) {

}
