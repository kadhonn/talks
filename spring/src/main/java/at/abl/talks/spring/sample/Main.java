package at.abl.talks.spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class);
    }
}

@SpringBootApplication
class SpringApp {

}

@RestController
class Controller {
    @GetMapping("/")
    String doGet() {
        return "hello world";
    }
}
