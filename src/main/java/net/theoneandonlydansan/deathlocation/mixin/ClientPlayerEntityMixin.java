package net.theoneandonlydansan.deathlocation.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.theoneandonlydansan.deathlocation.DeathLocation.client;
import static net.theoneandonlydansan.deathlocation.DeathLocation.getMessage;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "requestRespawn", at = @At("HEAD"))
    public void requestRespawn(CallbackInfo ci) {

        client.player.sendMessage(Text.literal(getMessage()).formatted(Formatting.GOLD), false);
    }
}
