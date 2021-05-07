package me.andante.chord;

import com.google.common.reflect.Reflection;
import me.andante.chord.client.network.BoatSpawnNetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ChordClient implements ClientModInitializer {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        Reflection.initialize(
            BoatSpawnNetworkHandler.class
        );
    }
}
