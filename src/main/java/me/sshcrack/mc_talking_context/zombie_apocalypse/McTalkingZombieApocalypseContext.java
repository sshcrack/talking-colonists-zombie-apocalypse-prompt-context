package me.sshcrack.mc_talking_context.zombie_apocalypse;

import com.mojang.logging.LogUtils;
/*? if forge {*/
/*import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*//*?}*/
/*? if neoforge {*/
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
/*?}*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main mod class for McTalking - a mod that enables citizens in MineColonies
 * to talk using AI voice chat.
 */
@Mod(McTalkingZombieApocalypseContext.MOD_ID)
public class McTalkingZombieApocalypseContext {
	public static final String MOD_ID = /*$ mod_id*/ "modtemplate";
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
    }
}
