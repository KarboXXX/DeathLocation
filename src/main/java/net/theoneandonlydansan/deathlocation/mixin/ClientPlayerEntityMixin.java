package net.theoneandonlydansan.deathlocation.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.theoneandonlydansan.deathlocation.DeathLocation.*;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "requestRespawn", at = @At("HEAD"))
    public void requestRespawn(CallbackInfo ci) {

        if(message.equals("")) {
            setMessage();
        }

        MutableText text = Text.literal(message);
        Vec3d pos = client.player.getPos();

        text.setStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            Text.translatable("deathlocation.copy"))));
            
        text.setStyle(text.getStyle().withClickEvent(
            new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, 
            copyDimension ? message.substring(message.indexOf("X:"), 
            message.length() -1) : String.format("X: %d, Y: %d, Z: %d", 
            (int) pos.x, (int) pos.y, (int) pos.z))));
        text.formatted(formatting);

        client.player.sendMessage(text, false);
    }
}
