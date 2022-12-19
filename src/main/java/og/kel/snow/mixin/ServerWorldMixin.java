package og.kel.snow.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow
    public abstract void setWeather(int clearDuration, int rainDuration, boolean raining, boolean thundering);

    @Shadow
    public abstract ServerWorld toServerWorld();

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;isRaining()Z"))
    private void weather(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (this.toServerWorld().getRegistryKey() == World.OVERWORLD) {
            if (!this.toServerWorld().isThundering()) {
                this.setWeather(0, 200, true, false);
            }
        }
    }
}