package me.andante.chord;

import com.google.common.reflect.Reflection;
import me.andante.chord.client.network.BoatSpawnNetworkHandler;
import net.fabricmc.api.ClientModInitializer;

public class ChordClient implements ClientModInitializer {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        Reflection.initialize(
            BoatSpawnNetworkHandler.class
        );
    }
}
