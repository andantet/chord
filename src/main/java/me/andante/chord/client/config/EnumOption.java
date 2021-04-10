package me.andante.chord.client.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class EnumOption<T extends Enum<?>> extends Option<T> {
    protected final Class<T> clazz;

    /**
     * Instantiates a new ranged configuration option.
     *
     * @param id         The option's identifier.
     * @param defaultVal The option's default value.
     */
    public EnumOption(String id, Class<T> clazz, T defaultVal) {
        super(id, defaultVal);
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return this.clazz;
    }

    @Override
    public String getValueForSave() {
        return this.value.toString();
    }
}
