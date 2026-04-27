package me.sshcrack.mc_talking_context.zombie_apocalypse;

import me.sshcrack.mc_talking.api.prompt.CitizenPromptService;
/*? if forge {*/
/*import net.minecraftforge.fml.common.Mod;
*//*?}*/
/*? if neoforge {*/
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
/*?}*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main mod class for McTalking - a mod that enables citizens in MineColonies
 * to talk using AI voice chat.
 */
@Mod(McTalkingZombieApocalypseContext.MOD_ID)
public class McTalkingZombieApocalypseContext {
	public static final String MOD_ID = /*$ mod_id*/ "mc_talking_context_zombie_apocalypse";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/*? if forge {*/
    /*public McTalkingZombieApocalypseContext() {
        initialize();
    }
    *//*?}*/


	/*? if neoforge {*/
	public McTalkingZombieApocalypseContext(IEventBus modEventBus, ModContainer modContainer) {
		initialize();
	}
	/*?}*/

	private void initialize() {
		CitizenPromptService.setProvider(new ZombieApocalypsePromptProvider());
	}
}
