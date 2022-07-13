package net.theoneandonlydansan.deathlocation;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class DeathLocation {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static String message = "";

    public static void setMessage() {
        Vec3d pos = client.player.getPos();
        DimensionType dimensionType = client.world.getDimension();
        String dimension;

        if (dimensionType.respawnAnchorWorks()) {
            dimension = "Nether";
        } else if (dimensionType.bedWorks()) {
            dimension = "Overworld";
        } else {
            dimension = "End";
        }

        message = String.format("You died at X: %d, Y: %d, Z: %d in the %s.", (int) pos.x, (int) pos.y, (int) pos.z, dimension);
    }
}
