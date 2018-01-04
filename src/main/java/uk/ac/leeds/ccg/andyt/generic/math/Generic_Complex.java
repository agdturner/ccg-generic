/**
 * Copyright 2017 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.math;

/**
 * https://en.wikipedia.org/wiki/Complex_number
 *
 * @author geoagdt
 */
public class Generic_Complex {

    private final double Real;
    private final double Imaginary;

    public Generic_Complex(
            double real,
            double imaginary) {
        Real = real;
        Imaginary = imaginary;
    }

    public Generic_Complex(
            double magnitude,
            double phase,
            boolean irrelevantFlag) {
        Real = magnitude * Math.cos(phase);
        Imaginary = magnitude * Math.sin(phase);
    }

    public double getReal() {
        return Real;
    }

    public double getImaginary() {
        return Imaginary;
    }

    @Override
    public String toString() {
        return Real + " " + Imaginary + "i";
    }

    /**
     * @param c
     * @return this + c.
     */
    public Generic_Complex add(Generic_Complex c) {
        Generic_Complex result;
        double real;
        real = this.Real + c.Real;
        double imaginary;
        imaginary = this.Imaginary + c.Imaginary;
        result = new Generic_Complex(real, imaginary);
        return result;
    }

    /**
     * @param c
     * @return this - c.
     */
    public Generic_Complex subtract(Generic_Complex c) {
        Generic_Complex result;
        double real;
        real = this.Real - c.Real;
        double imaginary;
        imaginary = this.Imaginary - c.Imaginary;
        result = new Generic_Complex(real, imaginary);
        return result;
    }

    /**
     * @param c
     * @return this / c.
     */
    public Generic_Complex multiply(Generic_Complex c) {
        Generic_Complex result;
        double magnitude;
        magnitude = this.magnitude() * c.magnitude();
        double phase;
        phase = this.phase() + c.phase();
        result = new Generic_Complex(magnitude, phase, true);
        return result;
    }

    /**
     * @param c
     * @return this * c.
     */
    public Generic_Complex multiply2(Generic_Complex c) {
        Generic_Complex result;
        double real;
        real = (this.Real * c.Real) - (this.Imaginary * c.Imaginary);
        double imaginary;
        imaginary = (this.Real * c.Imaginary) + (this.Imaginary * c.Real);
        result = new Generic_Complex(real, imaginary);
        return result;
    }
    
    /**
     * @return The magnitude of this.
     */
    public double magnitude() {
        return Math.hypot(Real, Imaginary);
    }

    /**
     * @return AKA the argument, this is the angle from the Origin to the Real
     * axis.
     */
    public double phase() {
        return Math.atan2(Imaginary, Real);
    }

    /**
     * @return Conjugate (new GenericComplex(Real, -Imaginary)).
     */
    public Generic_Complex conjugate() {
        return new Generic_Complex(Real, -Imaginary);
    }

    /**
     * @return 1 / this.
     */
    public Generic_Complex reciprocal() {
        Generic_Complex result;
        double scale;
        scale = Real * Real + Imaginary * Imaginary;
        double real;
        real = Real / scale;
        double imaginary = - Imaginary / scale;
        result = new Generic_Complex(real, imaginary);
        return result;
    }

    
    /**
     * @param c
     * @return this / c.
     */
    public Generic_Complex divide(Generic_Complex c) {
        Generic_Complex result;
        double magnitude;
        magnitude = this.magnitude() / c.magnitude();
        double phase;
        phase = this.phase() - c.phase();
        result = new Generic_Complex(magnitude, phase, true);
        return result;
    }
    
    /**
     * @param c
     * @return this / c.
     */
    public Generic_Complex divide2(Generic_Complex c) {
        Generic_Complex a = this;
        return a.multiply(c.reciprocal());
    }

    /**
     *  @return exponent of this.
     */
    public Generic_Complex exp() {
        Generic_Complex result;
        double real;
        real = Math.exp(Real) * Math.cos(Imaginary);
        double imaginary;
        imaginary = Math.exp(Real) * Math.sin(Imaginary);
        result = new Generic_Complex(real, imaginary);
        return result;
    }

    /**
     * @return The complex sine of this.
     */
    public Generic_Complex sin() {
        Generic_Complex result;
        double real;
        real = Math.sin(Real) * Math.cosh(Imaginary);
        double imaginary;
        imaginary = Math.cos(Real) * Math.sinh(Imaginary);
        result = new Generic_Complex(real, imaginary);
        return result;
    }

    /**
     * @return The complex cosine of this.
     */
    public Generic_Complex cos() {
        Generic_Complex result;
        double real;
        real = Math.cos(Real) * Math.cosh(Imaginary);
        double imaginary;
        imaginary = -Math.sin(Real) * Math.sinh(Imaginary);
        result = new Generic_Complex(real, imaginary);
        return result;
    }

    /**
     * @return The complex tangent of this.
     */
    public Generic_Complex tan() {
        return sin().divide(cos());
    }

    /**
     * @return The complex rescaling of this.
     */
    public Generic_Complex rescale(double alpha) {
        return new Generic_Complex(alpha * Real, alpha * Imaginary);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Generic_Complex that = (Generic_Complex) o;
        return (this.Real == that.Real) && (this.Imaginary == that.Imaginary);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.Real) ^ (Double.doubleToLongBits(this.Real) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.Imaginary) ^ (Double.doubleToLongBits(this.Imaginary) >>> 32));
        return hash;
    }
}
