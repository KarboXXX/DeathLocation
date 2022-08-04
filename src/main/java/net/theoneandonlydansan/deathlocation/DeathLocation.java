package net.theoneandonlydansan.deathlocation;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Language;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class DeathLocation {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static String message = "";

    public static void setMessage() {
        Vec3d pos = client.player.getPos();
        DimensionType dimensionType = client.world.getDimension();
        String dimension;
        Language language = Language.getInstance();

        if (dimensionType.respawnAnchorWorks()) {
            dimension = language.get("deathlocation.nether");
        } else if (dimensionType.bedWorks()) {
            dimension = language.get("deathlocation.overworld");
        } else {
            dimension = language.get("deathlocation.end");
        }

        message = String.format(language.get("deathlocation.deathmessage"), (int) pos.x, (int) pos.y, (int) pos.z, dimension);
    }
}
