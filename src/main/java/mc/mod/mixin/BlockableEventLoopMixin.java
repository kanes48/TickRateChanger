package mc.mod.mixin;

import mc.mod.TickRateManager;
import net.minecraft.util.thread.BlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.concurrent.locks.LockSupport;

@Mixin(BlockableEventLoop.class)
public abstract class BlockableEventLoopMixin {

    /**
     * Replaces the idle wait used while managedBlock() is waiting for work.
     * Keeps all vanilla scheduling logic intact.
     */
    @Overwrite
    public void waitForTasks() {
        if (TickRateManager.getTPS() <= 1000) {
            Thread.yield();
            LockSupport.parkNanos("waiting for tasks", 100_000L);
        } else {
            Thread.yield();
        }
    }
}