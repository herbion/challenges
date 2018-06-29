package com.goevro.challenge.busroute;


import com.goevro.challenge.busroute.storage.RefreshableStorage;
import com.goevro.challenge.busroute.storage.RouteStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Component
  @ConditionalOnProperty(name = "app.import.routes.strategy", havingValue = "auto")
  public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private RefreshableStorage routeStorage;

    public DataLoader(RouteStorage routeStorage) {
      this.routeStorage = routeStorage;
    }

    @Override
    public void run(String... args) {
      if (Objects.isNull(args) || args.length == 0) {
        throw new IllegalStateException("Missing arguments, please run application with path to file");
      }

      logger.info("Loading data using arguments: " + Arrays.toString(args));

      routeStorage.load(new File(args[0]));
    }

  }
}
