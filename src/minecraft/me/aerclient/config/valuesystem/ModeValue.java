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

import com.darkmagician6.eventapi.EventManager;
import me.aerclient.injection.events.client.EventValueChanged;

import java.util.Properties;

public class ModeValue extends Value<String> {
    private String[] modes;

    public ModeValue(String name, String description, String defaultVal, String... modes) {
        super(name, description, defaultVal);
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
        EventManager.call(new EventValueChanged(this));
        return true;
    }

    @Override
    public void addToProperties(Properties props) {
        props.setProperty(this.getName(), "" + this.getObject());

    }

    @Override
    public void fromProperties(Properties props) {
        this.setObject(props.getProperty(this.getName(), "" + this.getDefault()));
    }

    public String getValue() {
        return getObject();
    }
}
