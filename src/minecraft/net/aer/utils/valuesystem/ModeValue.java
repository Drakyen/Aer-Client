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

import java.util.Properties;
import java.util.function.Predicate;

public class ModeValue extends Value<String> {
    private String[] modes;

    public ModeValue(String name, String defaultVal, String... modes) {
        this(name, defaultVal, null, modes);
    }

    public ModeValue(String name, String defaultVal, Predicate<String> validator, String... modes) {
        super(name, defaultVal, validator);
        this.modes = modes;

        setObject(defaultVal);
    }

    public String[] getModes() {
        return modes;
    }

    @Override
    public boolean setObject(String s) {
        String value = "*";

        for (int i = 0; i < modes.length; i++) {
            String mode = modes[i];

            if (mode.equalsIgnoreCase(s)) value = mode;
        }
        if (value == "*") throw new IllegalArgumentException("Value '" + value + "' wasn't found");

        object = value;
        return true;
    }

    @Override
    public void addToProperties(Properties props) {
        props.setProperty(this.getName(), "" + this.getObject());

    }

    @Override
    public void fromProperties(Properties props) {
        this.setObject(props.getProperty(this.getName(), "" + this.getDefault()));
        System.out.println(this.getValue());
    }

    public String getValue() {
        return (String) getObject();
    }
}
