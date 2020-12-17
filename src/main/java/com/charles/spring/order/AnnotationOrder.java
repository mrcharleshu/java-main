package com.charles.spring.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AnnotationOrder implements CommandLineRunner {
    private final List<Rating> ratingList;

    public AnnotationOrder(List<Rating> ratingList) {
        this.ratingList = ratingList;
        for (Rating rating : ratingList) {
            log.info("{} >> {}", rating.getClass().getSimpleName(), rating.getRating());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("running");
    }

    public interface Rating {
        int getRating();
    }

    @Component
    @Order(2)
    public static class Excellent implements Rating {
        @Override
        public int getRating() {
            return 1;
        }
    }

    @Component
    @Order(1)
    public static class Good implements Rating {
        @Override
        public int getRating() {
            return 2;
        }
    }

    @Component
    @Order(Ordered.LOWEST_PRECEDENCE)
    public static class Average implements Rating {
        @Override
        public int getRating() {
            return 3;
        }
    }
}
