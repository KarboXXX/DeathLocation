package net.theoneandonlydansan.deathlocation;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class DeathLocation {
    public static MinecraftClient client = MinecraftClient.getInstance();

    public static MutableText getMessage() {
        Vec3d pos = client.player.getPos();
        DimensionType dimensionType = client.world.getDimension();
        Text dimension;

        if (dimensionType.respawnAnchorWorks()) {
            dimension = Text.translatable("deathlocation.nether");
        } else if (dimensionType.bedWorks()) {
            dimension = Text.translatable("deathlocation.overworld");
        } else {
            dimension = Text.translatable("deathlocation.end");
        }

        return Text.translatable("deathlocation.deathmessage", (int) pos.x, (int) pos.y, (int) pos.z, dimension);
    }
}
