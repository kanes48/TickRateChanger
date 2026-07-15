package mc.mod.mixin;

import mc.mod.TickRateManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

import static mc.mod.TickRateChangerV3.LOGGER;

@Mixin(MinecraftServer.class)
public abstract class TickRateMixin {

	@Shadow
	private long nextTickTime;

	@Shadow
	private boolean running;

	/**
	 * Replace the vanilla 50ms tick length with a configurable one.
	 */
	@ModifyConstant(
			method = "runServer",
			constant = @Constant(longValue = 50L)
	)
	private long modifyTickLength(long original) {
		return Math.max(1L, 1000L / TickRateManager.getTPS());
	}

	private static long lastTick = System.nanoTime();
	private static long lastPrint = lastTick;
	private static int ticks = 0;

	@Inject(method = "tickServer", at = @At("HEAD"))
	private void logTPS(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		ticks++;

		long now = System.nanoTime();

		if (now - lastPrint >= 1_000_000_000L) {
			double seconds = (now - lastPrint) / 1_000_000_000.0;
			double tps = ticks / seconds;

			LOGGER.info("Current TPS: {}", String.format("%.1f", tps));

			ticks = 0;
			lastPrint = now;
		}

		lastTick = now;
	}
}