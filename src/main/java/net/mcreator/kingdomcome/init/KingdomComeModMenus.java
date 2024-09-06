/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.kingdomcome.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.kingdomcome.world.inventory.StatelessGuiMenu;
import net.mcreator.kingdomcome.world.inventory.KingGuiMenu;
import net.mcreator.kingdomcome.world.inventory.CitizenGuiMenu;
import net.mcreator.kingdomcome.KingdomComeMod;

public class KingdomComeModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, KingdomComeMod.MODID);
	public static final RegistryObject<MenuType<KingGuiMenu>> KING_GUI = REGISTRY.register("king_gui", () -> IForgeMenuType.create(KingGuiMenu::new));
	public static final RegistryObject<MenuType<CitizenGuiMenu>> CITIZEN_GUI = REGISTRY.register("citizen_gui", () -> IForgeMenuType.create(CitizenGuiMenu::new));
	public static final RegistryObject<MenuType<StatelessGuiMenu>> STATELESS_GUI = REGISTRY.register("stateless_gui", () -> IForgeMenuType.create(StatelessGuiMenu::new));
}
