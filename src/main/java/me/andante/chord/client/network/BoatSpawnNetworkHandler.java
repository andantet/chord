package me.andante.chord.client.network;

import me.andante.chord.Chord;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class BoatSpawnNetworkHandler {
    static {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Chord.MOD_ID, "spawn_boat"), BoatSpawnNetworkHandler::accept);
    }

    public static void accept(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        final MinecraftClient client = MinecraftClient.getInstance();

        int id = buf.readVarInt();
        UUID uuid = buf.readUuid();
        EntityType<?> type = Registry.ENTITY_TYPE.get(buf.readVarInt());
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        byte pitch = buf.readByte();
        byte yaw = buf.readByte();

        if (client.isOnThread()) {
            spawn(client, id, uuid, type, x, y, z, pitch, yaw);
        } else {
            client.execute(() -> spawn(client, id, uuid, type, x, y, z, pitch, yaw));
        }
    }

    private static void spawn(MinecraftClient client, int id, UUID uuid, EntityType<?> type, double x, double y, double z, byte pitch, byte yaw) {
        ClientWorld world = client.world;

        if (world != null) {
            Entity entity = type.create(world);
            if (entity != null) {
                entity.setEntityId(id);
                entity.setUuid(uuid);
                entity.updatePosition(x, y, z);
                entity.updateTrackedPosition(x, y, z);
                entity.pitch = pitch * 360 / 256F;
                entity.yaw = yaw * 360 / 256F;

                world.addEntity(entity.getEntityId(), entity);
            }
        }
    }
}
