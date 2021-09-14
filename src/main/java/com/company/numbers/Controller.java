package com.company.numbers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @GetMapping("/numbers")
    public Numbers getNumbers(@RequestParam String numbers) {
        return getNumbersObject(numbers);
    }

    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Numbers {
        private final Integer first;
        private final Integer second;
        private final Integer third;
    }

    public Numbers getNumbersObject(String numbers) {
        var numbersList = Arrays.asList(numbers.split(","))
                .stream()
                .map(str -> Integer.parseInt(str))
                .collect(Collectors.toList());

        //initializing variables
        int first, second, third, j=0;
        first = second = third = Integer.MAX_VALUE;

        //first loop
        for (int i=0; i<numbersList.size(); i++) {
            first = Math.min(first, numbersList.get(i));
        }

        //second loop
        try {
            while (j++ <= numbersList.size()) {
                if (first != second) {
                    second = Math.min(second, numbersList.get(j));
                }
            }
        } catch (IndexOutOfBoundsException e) {
        }

        //thirdloop
        var numInterator = numbersList.iterator();
        int num;
        while(numInterator.hasNext()) {
            num = numInterator.next();
            if (third != first || third != second) {
                third = Math.min(num, third);
            }
        }

        //returning values
        return Numbers.builder()
                .first(first)
                .second(second)
                .third(third)
                .build();
    }
}
