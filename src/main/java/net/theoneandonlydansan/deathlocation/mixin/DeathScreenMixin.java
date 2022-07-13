package net.theoneandonlydansan.deathlocation.mixin;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.theoneandonlydansan.deathlocation.DeathLocation.*;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {

        if(message.equals("")) {
            setMessage();
        }
        client.textRenderer.drawWithShadow(matrices, message, (((DeathScreen) (Object) this).width / 2) - (client.textRenderer.getWidth(message) / 2), 115, 0xFFFFFF);
    }
}
