package me.sshcrack.mc_talking_context.zombie_apocalypse;

/*? if forge {*/
/*import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*/
/*?}*/

import me.sshcrack.mc_talking.api.prompt.CitizenPromptService;

/*? if neoforge {*/
import net.neoforged.fml.ModContainer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
/*?}*/


@Mod("mc_talking_context_zombie_apocalypse")
public class ContextMod {

    /*? if forge {*/
    /*public ContextMod(final FMLJavaModLoadingContext context) {
        initialize();
    }*/
    /*?}*/

    /*? if neoforge {*/
    public ContextMod(IEventBus modEventBus, ModContainer modContainer) {
        initialize();
    }
    /*?}*/

    private void initialize() {
        CitizenPromptService.setProvider(new ZombieApocalypsePromptProvider());
    }
}
