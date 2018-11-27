package hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	/*
	 * Creates a standalone application. Package everything in a single, executable JAR file.
	 * Use Spring’s support for embedding the Tomcat servlet container as the HTTP runtime, 
	 * instead of deploying to an external instance.
	 */

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}