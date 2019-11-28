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
import java.math.BigInteger;

/**
 *
 * @author Andy Turner
 */
public class Generic_Math {

    /**
     * Used for testing. If values are greater than this then storing them as
     * Double values is dubious. The t0String() conversion is necessary
     * otherwise the number is too large.
     */
    public static final BigDecimal DOUBLE_MAXVALUE = new BigDecimal(
            Double.toString(Double.MAX_VALUE));

    /**
     * Used for testing. If values are less than this then storing them as
     * Double values is dubious.
     */
    public static final BigDecimal DOUBLE_MAXVALUE_NEG = DOUBLE_MAXVALUE.negate();

    /**
     * Used for testing. If values are greater than this then storing them as
     * Float values is dubious. The tString() conversion is necessary otherwise
     * the number is too large.
     */
    public static final BigDecimal FLOAT_MAXVALUE = new BigDecimal(
            Float.toString(Float.MAX_VALUE));

    /**
     * Used for testing. If values are less than this then storing them as Float
     * values is dubious.
     */
    public static final BigDecimal FLOAT_MAXVALUE_NEG = FLOAT_MAXVALUE.negate();

    /**
     * For adding two generic Numbers of the same type. The types of number are
     * converted as appropriate into BigDecimals or BigIntegers (for integer
     * type numbers and floating point number respectively - if they are not
     * already of these types) which are then added and the result cast into the
     * type T. If the result of adding the two numbers cannot be stored exactly
     * in the case of integer type numerical addition then an
     * ArithmeticException is thrown.
     *
     * If the result of adding the two numbers is beyond the range in T1 then an
     * ArithmeticExcpetion is thrown unless infinities are involved (except when
     * the two numbers being added are opposite infinities - in which case an
     * ArithmeticException is thrown). If one number is positive or negative
     * infinity then that infinity is returned. In cases where the values being
     * added are represented in floating point precision the results are a
     * consequence of a narrowing digital conversion. (N.B. If desirable then if
     * numbers do not increase or decrease as expected then there could be an
     * implementation that throws an ArithmeticException in cases where a
     * narrowing digital conversion does not result in a larger or smaller
     * number as is typically mathematically expected in a sum.
     *
     * @param <T1> The type of the Number x and the result returned.
     * @param <T2> The type of the Number y.
     * @param x A Number to add.
     * @param y A Number to add.
     * @return The result of adding x and y expressed in the same type as x.
     */
    public static <T1 extends Number, T2 extends Number> T1 add2(T1 x, T2 y) {
        if (x == null || y == null) {
            return null;
        }
        if (x instanceof BigDecimal) {
            BigDecimal x0 = (BigDecimal) x;
            if (y instanceof BigDecimal) {
                return (T1) x0.add((BigDecimal) y);
            } else if (y instanceof BigInteger) {
                return (T1) x0.add(new BigDecimal((BigInteger) y));
            } else if (y instanceof Double) {
                return (T1) x0.add(new BigDecimal((Double) y));
            } else if (y instanceof Float) {
                return (T1) x0.add(new BigDecimal((Float) y));
            } else if (y instanceof Long) {
                return (T1) x0.add(new BigDecimal((Long) y));
            } else if (y instanceof Integer) {
                return (T1) x0.add(new BigDecimal((Integer) y));
            } else if (y instanceof Short) {
                return (T1) x0.add(new BigDecimal((Short) y));
            } else if (y instanceof Byte) {
                return (T1) x0.add(new BigDecimal((Byte) y));
            } else {
                throw new IllegalArgumentException("Type T2=" + y.getClass()
                        + " is not supported in Generic_Math.add(T1, T2)");
            }
        } else if (x instanceof BigInteger) {
            BigInteger x0 = (BigInteger) x;
            if (y instanceof BigDecimal) {
                return (T1) x0.add(((BigDecimal) y).toBigInteger());
            } else if (y instanceof BigInteger) {
                return (T1) x0.add((BigInteger) y);
            } else if (y instanceof Double) {
                return (T1) x0.add(BigInteger.valueOf(((Double) y).longValue()));
            } else if (y instanceof Float) {
                return (T1) x0.add(BigInteger.valueOf(((Float) y).longValue()));
            } else if (y instanceof Long) {
                return (T1) x0.add(BigInteger.valueOf((Long) y));
            } else if (y instanceof Integer) {
                return (T1) x0.add(BigInteger.valueOf((Integer) y));
            } else if (y instanceof Short) {
                return (T1) x0.add(BigInteger.valueOf((Short) y));
            } else if (y instanceof Byte) {
                return (T1) x0.add(BigInteger.valueOf((Byte) y));
            } else {
                throw new IllegalArgumentException("Type T2=" + y.getClass()
                        + " is not supported in Generic_Math.add(T1, T2)");
            }
        } else if (x instanceof Double) {
            Double x0 = (Double) x;
            // Deal with special cases.
            if (x0.isNaN()) {
                return (T1) x0;
            }
            if (y instanceof Double) {
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    return (T1) (Double) Double.NaN;
                }
            } else if (y instanceof Float) {
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    return (T1) (Double) Double.NaN;
                }
            }
            if (x0 == Double.POSITIVE_INFINITY) {
                if (y instanceof Double) {
                    Double y0 = (Double) y;
                    if (y0 == Double.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Double) Double.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else if (y instanceof Float) {
                    Float y0 = (Float) y;
                    if (y0 == Float.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Double) Double.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else {
                    return (T1) x0;
                }
            } else if (x0 == Double.NEGATIVE_INFINITY) {
                if (y instanceof Double) {
                    Double y0 = (Double) y;
                    if (y0 == Double.POSITIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Double) Double.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else if (y instanceof Float) {
                    Float y0 = (Float) y;
                    if (y0 == Float.POSITIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Double) Double.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else {
                    return (T1) x0;
                }
            } else {
                BigDecimal x1 = BigDecimal.valueOf(x0);
                if (y instanceof BigDecimal) {
                    BigDecimal sum = x1.add((BigDecimal) y);
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof BigInteger) {
                    BigDecimal sum = x1.add(new BigDecimal((BigInteger) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof Double) {
                    BigDecimal sum = x1.add(new BigDecimal((Double) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof Float) {
                    BigDecimal sum = x1.add(new BigDecimal((Float) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof Long) {
                    BigDecimal sum = x1.add(new BigDecimal((Long) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof Integer) {
                    BigDecimal sum = x1.add(new BigDecimal((Integer) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof Short) {
                    BigDecimal sum = x1.add(new BigDecimal((Short) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else if (y instanceof Byte) {
                    BigDecimal sum = x1.add(new BigDecimal((Byte) y));
                    testDouble(sum);
                    return (T1) (Double) sum.doubleValue();
                } else {
                    throw new IllegalArgumentException("Type T2=" + y.getClass()
                            + " is not supported in Generic_Math.add(T1, T2)");
                }
            }
        } else if (x instanceof Float) {
            Float x0 = (Float) x;
            // Deal with special cases.
            if (x0.isNaN()) {
                return (T1) x0;
            }
            if (y instanceof Double) {
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    return (T1) (Float) Float.NaN;
                }
            } else if (y instanceof Float) {
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    return (T1) (Float) Float.NaN;
                }
            }
            if (x0 == Float.POSITIVE_INFINITY) {
                if (y instanceof Double) {
                    Double y0 = (Double) y;
                    if (y0 == Double.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Float) Float.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else if (y instanceof Float) {
                    Float y0 = (Float) y;
                    if (y0 == Float.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Float) Float.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else {
                    return (T1) x0;
                }
            } else if (x0 == Float.NEGATIVE_INFINITY) {
                if (y instanceof Double) {
                    Double y0 = (Double) y;
                    if (y0 == Double.POSITIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Float) Float.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else if (y instanceof Float) {
                    Float y0 = (Float) y;
                    if (y0 == Float.POSITIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T1) (Float) Float.NaN;
                    } else {
                        return (T1) x0;
                    }
                } else {
                    return (T1) x0;
                }
            } else {
                BigDecimal x1 = BigDecimal.valueOf(x0);
                if (y instanceof BigDecimal) {
                    BigDecimal sum = x1.add((BigDecimal) y);
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof BigInteger) {
                    BigDecimal sum = x1.add(new BigDecimal((BigInteger) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof Double) {
                    BigDecimal sum = x1.add(new BigDecimal((Double) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof Float) {
                    BigDecimal sum = x1.add(new BigDecimal((Float) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof Long) {
                    BigDecimal sum = x1.add(new BigDecimal((Long) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof Integer) {
                    BigDecimal sum = x1.add(new BigDecimal((Integer) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof Short) {
                    BigDecimal sum = x1.add(new BigDecimal((Short) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else if (y instanceof Byte) {
                    BigDecimal sum = x1.add(new BigDecimal((Byte) y));
                    testFloat(sum);
                    return (T1) (Float) sum.floatValue();
                } else {
                    throw new IllegalArgumentException("Type T2=" + y.getClass()
                            + " is not supported in Generic_Math.add(T1, T2)");
                }
            }
        } else if (x instanceof Long) {
            Long x0 = (Long) x;
            // Deal with special cases.
            if (y instanceof Double) {
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Long and T2 is "
                            + "Double and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Long) x1.add((new BigDecimal((Double) y))
                            .toBigInteger()).longValueExact();
                }
            } else if (y instanceof Float) {
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Long and T2 is "
                            + "Float and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Long) x1.add((new BigDecimal((Float) y))
                            .toBigInteger()).longValueExact();
                }
            } else {
                BigInteger x1 = BigInteger.valueOf(x0);
                if (y instanceof BigDecimal) {
                    return (T1) (Long) x1.add(((BigDecimal) y).toBigInteger())
                            .longValueExact();
                } else if (y instanceof BigInteger) {
                    return (T1) (Long) x1.add((BigInteger) y).longValueExact();
                } else if (y instanceof Long) {
                    return (T1) (Long) x1.add(BigInteger.valueOf((Long) y))
                            .longValueExact();
                } else if (y instanceof Integer) {
                    return (T1) (Long) x1.add(BigInteger.valueOf((Integer) y))
                            .longValueExact();
                } else if (y instanceof Short) {
                    return (T1) (Long) x1.add(BigInteger.valueOf((Short) y))
                            .longValueExact();
                } else if (y instanceof Byte) {
                    return (T1) (Long) x1.add(BigInteger.valueOf((Byte) y))
                            .longValueExact();
                } else {
                    throw new IllegalArgumentException("Type T2=" + y.getClass()
                            + " is not supported in Generic_Math.add(T1, T2)");
                }
            }
        } else if (x instanceof Integer) {
            Integer x0 = (Integer) x;
            // Deal with special cases.
            if (y instanceof Double) {
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Integer and T2 is"
                            + " Double and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Integer) x1.add((new BigDecimal((Double) y))
                            .toBigInteger()).intValueExact();
                }
            } else if (y instanceof Float) {
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Integer and T2 is"
                            + " Float and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Integer) x1.add((new BigDecimal((Float) y))
                            .toBigInteger()).intValueExact();
                }
            } else {
                BigInteger x1 = BigInteger.valueOf(x0);
                if (y instanceof BigDecimal) {
                    return (T1) (Integer) x1.add(((BigDecimal) y).
                            toBigInteger()).intValueExact();
                } else if (y instanceof BigInteger) {
                    return (T1) (Integer) x1.add((BigInteger) y).intValueExact();
                } else if (y instanceof Long) {
                    return (T1) (Integer) x1.add(BigInteger.valueOf((Long) y))
                            .intValueExact();
                } else if (y instanceof Integer) {
                    return (T1) (Integer) x1.add(BigInteger.valueOf((Integer) y))
                            .intValueExact();
                } else if (y instanceof Short) {
                    return (T1) (Integer) x1.add(BigInteger.valueOf((Short) y))
                            .intValueExact();
                } else if (y instanceof Byte) {
                    return (T1) (Integer) x1.add(BigInteger.valueOf((Byte) y))
                            .intValueExact();
                } else {
                    throw new IllegalArgumentException("Type T2=" + y.getClass()
                            + " is not supported in Generic_Math.add(T1, T2)");
                }
            }
        } else if (x instanceof Short) {
            Short x0 = (Short) x;
            // Deal with special cases.
            if (y instanceof Double) {
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Short and T2 is"
                            + " Double and NaN in Generic_Math.add(T1, T2)");
                } else if (y instanceof Double) {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Short) x1.add((new BigDecimal((Double) y))
                            .toBigInteger()).shortValueExact();
                }
            } else if (y instanceof Float) {
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Short and T2 is"
                            + " Float and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Short) x1.add((new BigDecimal((Float) y))
                            .toBigInteger()).shortValueExact();
                }
            } else {
                BigInteger x1 = BigInteger.valueOf(x0);
                if (y instanceof BigDecimal) {
                    return (T1) (Short) x1.add(((BigDecimal) y).toBigInteger())
                            .shortValueExact();
                } else if (y instanceof BigInteger) {
                    return (T1) (Short) x1.add((BigInteger) y).shortValueExact();
                } else if (y instanceof Long) {
                    return (T1) (Short) x1.add(BigInteger.valueOf((Long) y))
                            .shortValueExact();
                } else if (y instanceof Integer) {
                    return (T1) (Short) x1.add(BigInteger.valueOf((Integer) y))
                            .shortValueExact();
                } else if (y instanceof Short) {
                    return (T1) (Short) x1.add(BigInteger.valueOf((Short) y))
                            .shortValueExact();
                } else if (y instanceof Byte) {
                    return (T1) (Short) x1.add(BigInteger.valueOf((Byte) y))
                            .shortValueExact();
                } else {
                    throw new IllegalArgumentException("Type T2=" + y.getClass()
                            + " is not supported in Generic_Math.add(T1, T2)");
                }
            }
        } else if (x instanceof Byte) {
            Byte x0 = (Byte) x;
            // Deal with special cases.
            if (y instanceof Double) {
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Byte and T2 is"
                            + " Double and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Byte) x1.add((new BigDecimal((Double) y))
                            .toBigInteger()).byteValueExact();
                }
            } else if (y instanceof Float) {
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    throw new ArithmeticException("T1 is type Byte and T2 is"
                            + " Float and NaN in Generic_Math.add(T1, T2)");
                } else {
                    BigInteger x1 = BigInteger.valueOf(x0);
                    return (T1) (Byte) x1.add((new BigDecimal((Float) y))
                            .toBigInteger()).byteValueExact();
                }
            } else {
                BigInteger x1 = BigInteger.valueOf(x0);
                if (y instanceof BigDecimal) {
                    return (T1) (Byte) x1.add(((BigDecimal) y).toBigInteger())
                            .byteValueExact();
                } else if (y instanceof BigInteger) {
                    return (T1) (Byte) x1.add((BigInteger) y).byteValueExact();
                } else if (y instanceof Long) {
                    return (T1) (Byte) x1.add(BigInteger.valueOf((Long) y))
                            .byteValueExact();
                } else if (y instanceof Integer) {
                    return (T1) (Byte) x1.add(BigInteger.valueOf((Integer) y))
                            .byteValueExact();
                } else if (y instanceof Short) {
                    return (T1) (Byte) x1.add(BigInteger.valueOf((Short) y))
                            .byteValueExact();
                } else if (y instanceof Byte) {
                    return (T1) (Byte) x1.add(BigInteger.valueOf((Byte) y))
                            .byteValueExact();
                } else {
                    throw new IllegalArgumentException("Type T2=" + y.getClass()
                            + " is not supported in Generic_Math.add(T1, T2)");
                }
            }
        } else {
            throw new IllegalArgumentException("Type T=" + x.getClass()
                    + " is not supported in Generic_Collections.add(T, T)");
        }
        // This line should never be reached!
        return null;
    }

    /**
     * @param x Value to test.
     * @throws ArithmeticException if x is greater than {@link #DOUBLE_MAXVALUE}
     * or less than {@link #DOUBLE_MAXVALUE_NEG}.
     */
    public static void testDouble(BigDecimal x) {
        if (x.compareTo(DOUBLE_MAXVALUE) == 1) {
            throw new ArithmeticException("x " + x.toString() + " is greater "
                    + "than Double.Max_Value.");
        }
        if (x.compareTo(DOUBLE_MAXVALUE_NEG) == -1) {
            throw new ArithmeticException("x " + x.toString() + " is less "
                    + "than Double.Max_Value.");
        }
    }

    /**
     * This first calls {@link #testDouble(java.math.BigDecimal) on x.
     *
     * @param x Value to test.
     * @param epsilon If x cannot be stored as a Double within this distance of
     * x then an ArithmeticException is thrown.
     * @throws ArithmeticException if x cannot be stored as a Double within
     * epsilon distance of x.
     */
    public static void testDouble(BigDecimal x, BigDecimal epsilon) {
        testDouble(x);
        Double xd = x.doubleValue();
        BigDecimal x2 = new BigDecimal(xd);
        if (x2.compareTo(x.subtract(epsilon)) == -1) {
            throw new ArithmeticException("x " + x.toString() + " when "
                    + "converted to a Double is more than " + epsilon.toString()
                    + " less than x.");
        }
        if (x2.compareTo(x.add(epsilon)) == 1) {
            throw new ArithmeticException("x " + x.toString() + " when "
                    + "converted to a Double is more than " + epsilon.toString()
                    + " more than x.");
        }
    }

    /**
     * @param x Value to test.
     * @throws ArithmeticException if x is greater than {@link #FLOAT_MAXVALUE}
     * or less than {@link #FLOAT_MAXVALUE_NEG}.
     */
    public static void testFloat(BigDecimal x) {
        if (x.compareTo(FLOAT_MAXVALUE) == 1) {
            throw new ArithmeticException("x " + x.toString() + " is greater "
                    + "than Double.Max_Value.");
        }
        if (x.compareTo(FLOAT_MAXVALUE_NEG) == -1) {
            throw new ArithmeticException("x " + x.toString() + " is less "
                    + "than Double.Max_Value.");
        }
    }

    /**
     * This first calls {@link #testFloat(java.math.BigDecimal) on x.
     *
     * @param x Value to test.
     * @param epsilon If x cannot be stored as a Float within this distance of x
     * then an ArithmeticException is thrown.
     * @throws ArithmeticException if x cannot be stored as a Float within
     * epsilon distance of x.
     */
    public static void testFloat(BigDecimal x, BigDecimal epsilon) {
        testFloat(x);
        Float xd = x.floatValue();
        BigDecimal x2 = new BigDecimal(xd);
        if (x2.compareTo(x.subtract(epsilon)) == -1) {
            throw new ArithmeticException("x " + x.toString() + " when "
                    + "converted to a Float is more than " + epsilon.toString()
                    + " less than x.");
        }
        if (x2.compareTo(x.add(epsilon)) == 1) {
            throw new ArithmeticException("x " + x.toString() + " when "
                    + "converted to a Float is more than " + epsilon.toString()
                    + " more than x.");
        }
    }

    /**
     * For adding two generic Numbers x and y of the same type. The numbers x
     * and y are converted as appropriate into BigInteger or BigDecimal (for
     * integer type or floating point numbers respectively - if they are not
     * already of these types). In the general case, the converted numbers are
     * then added and the result cast into the type T and returned. If the
     * result of adding the two numbers cannot be stored exactly in the case of
     * integer type numerical addition then an ArithmeticException is thrown. If
     * the result of adding the two numbers is beyond the range in the type of
     * numbers being added then an ArithmeticExcpetion is thrown unless
     * infinities are involved (except when the two numbers being added are
     * opposite infinities - in which case an ArithmeticException is thrown). If
     * one number is positive or negative infinity then that infinity is
     * returned. In cases where the values being added are represented in
     * floating point precision the results are a consequence of a narrowing
     * digital conversion. (N.B. If desirable then if numbers do not increase or
     * decrease as expected then there could be an implementation that throws an
     * ArithmeticException in cases where a narrowing digital conversion does
     * not result in a larger or smaller number as is typically mathematically
     * expected in a sum.
     *
     * @param <T> The type T of the Numbers x and y.
     * @param x A Number to add.
     * @param y A Number to add.
     * @return The result of adding x and y expressed in the same type as x and
     * y.
     */
    public static <T extends Number> T add(T x, T y) {
        if (x == null || y == null) {
            return null;
        }
        if (x instanceof BigDecimal) {
            return (T) ((BigDecimal) x).add((BigDecimal) y);
        } else if (x instanceof BigInteger) {
            return (T) ((BigInteger) x).add((BigInteger) y);
        } else {
            if (x instanceof Double) {
                Double x0 = (Double) x;
                // Deal with special cases.
                if (x0.isNaN()) {
                    return (T) x0;
                }
                Double y0 = (Double) y;
                if (y0.isNaN()) {
                    return (T) y0;
                }
                if (x0 == Double.POSITIVE_INFINITY) {
                    if (y0 == Double.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T) (Double) Double.NaN;
                    } else {
                        return (T) x0;
                    }
                } else if (x0 == Double.NEGATIVE_INFINITY) {
                    if (y0 == Double.POSITIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T) (Double) Double.NaN;
                    } else {
                        return (T) x0;
                    }
                } else {
                    BigDecimal x1 = BigDecimal.valueOf(x0);
                    BigDecimal y1 = BigDecimal.valueOf(y0);
                    BigDecimal sum = x1.add(y1);
                    testDouble(sum);
                    return (T) (Double) sum.doubleValue();
                }
            } else if (x instanceof Float) {
                Float x0 = (Float) x;
                // Deal with special cases.
                if (x0.isNaN()) {
                    return (T) x0;
                }
                Float y0 = (Float) y;
                if (y0.isNaN()) {
                    return (T) y0;
                }
                if (x0 == Float.POSITIVE_INFINITY) {
                    if (y0 == Float.NEGATIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T) (Float) Float.NaN;
                    } else {
                        return (T) x0;
                    }
                } else if (x0 == Float.NEGATIVE_INFINITY) {
                    if (y0 == Float.POSITIVE_INFINITY) {
                        throw new ArithmeticException("Attempting to add "
                                + "positive and negative infinity.");
                        //return (T) (Float) Float.NaN;
                    } else {
                        return (T) x0;
                    }
                } else {
                    BigDecimal x1 = BigDecimal.valueOf(x0);
                    BigDecimal y1 = BigDecimal.valueOf(y0);
                    BigDecimal sum = x1.add(y1);
                    testFloat(sum);
                    return (T) (Float) sum.floatValue();
                }
            } else if (x instanceof Long) {
                BigInteger x0 = BigInteger.valueOf((Long) x);
                BigInteger y0 = BigInteger.valueOf((Long) y);
                return (T) (Long) x0.add(y0).longValueExact();
            } else if (x instanceof Integer) {
                BigInteger x0 = BigInteger.valueOf((Integer) x);
                BigInteger y0 = BigInteger.valueOf((Integer) y);
                return (T) (Integer) x0.add(y0).intValueExact();
            } else if (x instanceof Short) {
                BigInteger x0 = BigInteger.valueOf((Short) x);
                BigInteger y0 = BigInteger.valueOf((Short) y);
                return (T) (Short) x0.add(y0).shortValueExact();
            } else if (x instanceof Byte) {
                BigInteger x0 = BigInteger.valueOf((Byte) x);
                BigInteger y0 = BigInteger.valueOf((Byte) y);
                return (T) (Byte) x0.add(y0).byteValueExact();
            } else {
                throw new IllegalArgumentException("Type T=" + x.getClass()
                        + " is not supported in Generic_Collections.add(T, T)");
            }
        }
    }

}
