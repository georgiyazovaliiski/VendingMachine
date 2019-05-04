package components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import components.utils.VendingMachine;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private VendingMachine machine;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        machine.start(args);
    }
}
