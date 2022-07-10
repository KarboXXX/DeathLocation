package net.theoneandonlydansan.deathlocation.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class DeathScreenMixin {

    MinecraftClient client = MinecraftClient.getInstance();

    @Inject(method = "requestRespawn", at = @At("HEAD"))
    public void requestRespawn(CallbackInfo ci) {
        Vec3d pos = client.player.getPos();
        DimensionType dimensionType = client.world.getDimension();
        String message;
        String dimension;

        if (dimensionType.respawnAnchorWorks()) {
            dimension = "Nether";
        } else if (dimensionType.bedWorks()) {
            dimension = "Overworld";
        } else {
            dimension = "End";
        }

        message = String.format("You died at X: %d, Y: %d, Z: %d in the %s.", (int) pos.x, (int) pos.y, (int) pos.z, dimension);
        MinecraftClient.getInstance().player.sendMessage(Text.literal(message), false);
    }
}
