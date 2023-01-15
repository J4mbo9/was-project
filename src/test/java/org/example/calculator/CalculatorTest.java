package org.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class CalculatorTest {

    @DisplayName("모든 연산 수행")
    @ParameterizedTest
    @MethodSource("formulaAndResult")
    void calculatorTest(int operand1,String operator, int operand2, int result) {

        int result1 = Calculator.calculate(new PositiveNumber(operand1), operator,new PositiveNumber(operand2));

        assertThat(result1).isEqualTo(result);
    }

    private static Stream<Arguments> formulaAndResult(){
        return Stream.of(
                arguments(1, "+", 2, 3),
                arguments(1, "-", 2, -1),
                arguments(4, "*", 2, 8),
                arguments(4, "/", 2, 2)
        );
    }
}