/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.aer.config.valuesystem;

import com.darkmagician6.eventapi.EventManager;
import me.aer.injection.events.client.EventValueChanged;

import java.util.Properties;

public abstract class Value<T> {
    private String name;
    private String description;
    protected T object;
    private T defaultVal;

    Value(String name, String description, T defaultVal) {
        this.name = name;
        this.object = defaultVal;
        this.defaultVal = defaultVal;
        this.description = description;
    }

    public abstract void addToProperties(Properties props);

    public abstract void fromProperties(Properties props);

    public String getName() {
        return name;
    }

    public T getObject() {
        return object;
    }

    public boolean setObject(T object) {

        this.object = object;
        EventManager.call(new EventValueChanged(this));
        return true;
    }

    public Object getDefault() {
        return defaultVal;
    }

    public String getDescription() {
        return description;
    }

}
