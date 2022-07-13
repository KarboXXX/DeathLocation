package net.theoneandonlydansan.deathlocation.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.theoneandonlydansan.deathlocation.DeathLocation.*;
import static net.theoneandonlydansan.deathlocation.DeathLocation.message;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "requestRespawn", at = @At("HEAD"))
    public void requestRespawn(CallbackInfo ci) {
        try {
            if(!client.world.getGameRules().getBoolean(GameRules.DO_IMMEDIATE_RESPAWN))
                setMessage();

            if(!message.equals("") || !client.world.getGameRules().getBoolean(GameRules.DO_IMMEDIATE_RESPAWN)) {
                //wait(500);
                client.player.sendMessage(Text.literal(message).formatted(Formatting.GOLD), false);
                message = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
