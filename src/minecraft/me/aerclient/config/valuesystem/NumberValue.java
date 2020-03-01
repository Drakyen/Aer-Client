/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.aerclient.config.valuesystem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Properties;
import java.util.function.Predicate;

public class NumberValue<T extends Number> extends Value<T> {
    private T min;
    private T max;
    private boolean validate;


    public NumberValue(String name, T defaultVal, @Nonnull T min, @Nonnull T max, boolean validate) {
        super(name, defaultVal);
        this.min = min;
        this.max = max;
        this.validate = validate;
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
    public boolean setObject(T object) {
        if(validate){
            if(object.doubleValue() < min.doubleValue() || object.doubleValue() > max.doubleValue()){
                return false;
            }
        }
//        if(object.doubleValue() < min.doubleValue()){
//            return false;
//        }
        return super.setObject(object);
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
