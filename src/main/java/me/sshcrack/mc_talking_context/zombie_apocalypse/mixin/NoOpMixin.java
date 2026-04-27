package me.sshcrack.mc_talking_context.zombie_apocalypse.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.sshcrack.mc_talking_context.zombie_apocalypse.McTalkingZombieApocalypseContext;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
@MixinEnvironment(type = MixinEnvironment.Env.MAIN)
public class NoOpMixin {
}
