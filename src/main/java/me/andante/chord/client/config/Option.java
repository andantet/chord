package me.andante.chord.client.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * A configuration option.
 */
@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class Option<T> {
    protected final String id;

    public T value;
    protected final T defaultValue;

    /**
     * Instantiates a new configuration option.
     *
     * @param id The option's identifier.
     * @param defaultVal The option's default value.
     */
    public Option(String id, T defaultVal) {
        this.id = id;
        this.defaultValue = defaultVal;
        this.value = this.defaultValue;
    }

    public T getDefault() {
        return this.defaultValue;
    }
    public void setValue(T value) {
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public String getValueForSave() {
        return String.valueOf(this.value);
    }
}
