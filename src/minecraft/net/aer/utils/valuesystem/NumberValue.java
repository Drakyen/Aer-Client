/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.aer.utils.valuesystem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Properties;
import java.util.function.Predicate;

public class NumberValue<T extends Number> extends Value<T> {
    private T min;
    private T max;

    public NumberValue(String name, T defaultVal, @Nonnull T min, @Nonnull T max) {
        this(name, defaultVal, min, max, null);
    }

    public NumberValue(String name, T defaultVal, @Nonnull T min, @Nonnull T max, @Nullable Predicate<T> validator) {
        super(name, defaultVal, validator == null ? val -> val.doubleValue() >= min.doubleValue() && val.doubleValue() <= max.doubleValue() : validator.and(val -> val.doubleValue() >= min.doubleValue() && val.doubleValue() <= max.doubleValue()));
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getValue() {
        return (T) this.getObject();
    }

    @Override
    public void addToProperties(Properties props) {
        props.setProperty(this.getName(), "" + this.getObject());

    }

    @Override
    public void fromProperties(Properties props) {
        String value = props.getProperty(this.getName(), "" + this.getDefault());
        if (getObject() instanceof Integer) {
            setObject((T) Integer.valueOf(value));
        }
        if (getObject() instanceof Long) {
            setObject((T) Long.valueOf(value));
        }
        if (getObject() instanceof Float) {
            setObject((T) Float.valueOf(value));
        }
        if (getObject() instanceof Double) {
            setObject((T) Double.valueOf(value));
        }

    }
}
