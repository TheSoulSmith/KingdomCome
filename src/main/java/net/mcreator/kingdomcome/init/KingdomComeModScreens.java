/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.kingdomcome.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.kingdomcome.client.gui.StatelessGuiScreen;
import net.mcreator.kingdomcome.client.gui.KingGuiScreen;
import net.mcreator.kingdomcome.client.gui.CitizenGuiScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KingdomComeModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(KingdomComeModMenus.KING_GUI.get(), KingGuiScreen::new);
			MenuScreens.register(KingdomComeModMenus.CITIZEN_GUI.get(), CitizenGuiScreen::new);
			MenuScreens.register(KingdomComeModMenus.STATELESS_GUI.get(), StatelessGuiScreen::new);
		});
	}
}
