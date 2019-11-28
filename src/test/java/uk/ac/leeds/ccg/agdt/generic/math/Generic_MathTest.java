/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.leeds.ccg.agdt.generic.math;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author geoagdt
 */
public class Generic_MathTest {

    public Generic_MathTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of add2 method, of class Generic_Math.
     */
    @Test
    public void testAdd2() {
        System.out.println("add2");
        Number x;
        Number y;
        Number expResult;
        Number result;
        // Test 1
        x = BigDecimal.ONE;
        y = BigDecimal.ONE;
        expResult = BigDecimal.valueOf(2);
        result = Generic_Math.add2(x, y);
        assertEquals(expResult, result);
        // Test 2
        x = BigDecimal.ONE;
        y = 100L;
        expResult = BigDecimal.valueOf(101L);
        result = Generic_Math.add2(x, y);
        assertEquals(expResult, result);
        // Test 3
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Generic_Math.add2(Integer.MAX_VALUE, BigDecimal.ONE);
        });
        // Test 4
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Generic_Math.add2(Long.MAX_VALUE, BigDecimal.ONE);
        });
        // Test 5
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Generic_Math.add2(Double.MAX_VALUE, BigDecimal.ONE);
        });
    }

    /**
     * Test of add method, of class Generic_Math.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Number x;
        Number y;
        Number expResult;
        Number result;
        // Test 1
        x = BigDecimal.ONE;
        y = BigDecimal.ONE;
        expResult = BigDecimal.valueOf(2);
        result = Generic_Math.add(x, y);
        assertEquals(expResult, result);
        // Test 2
        Assertions.assertThrows(ClassCastException.class, () -> {
            Generic_Math.add(BigDecimal.ONE, 100L);
        });
        // Test 3
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Generic_Math.add2(Integer.MAX_VALUE, 1);
        });
        // Test 4
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Generic_Math.add2(Long.MAX_VALUE, 1L);
        });

    }

}
