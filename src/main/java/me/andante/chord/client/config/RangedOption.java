package me.andante.chord.client.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class RangedOption<T extends Number> extends Option<T> {
    protected final T min;
    protected final T max;

    /**
     * Instantiates a new ranged configuration option.
     *
     * @param id         The option's identifier.
     * @param defaultVal The option's default value.
     * @param min        The option's minimum value.
     * @param max        The option's maximum value.
     */
    public RangedOption(String id, T defaultVal, T min, T max) {
        super(id, defaultVal);
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return this.min;
    }
    public T getMax() {
        return this.max;
    }
}
