package net.theoneandonlydansan.deathlocation.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.theoneandonlydansan.deathlocation.DeathLocation.*;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "requestRespawn", at = @At("HEAD"))
    public void requestRespawn(CallbackInfo ci) {
        client.player.sendMessage(getMessage().formatted(Formatting.GOLD), false);
    }
}
