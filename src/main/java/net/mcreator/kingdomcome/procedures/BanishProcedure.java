package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.HashMap;
import java.util.ArrayList;

public class BanishProcedure {
	public static void execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getDisplayName().getString()).contains(guistate.containsKey("text:PlayerSearchBox") ? ((EditBox) guistate.get("text:PlayerSearchBox")).getValue() : "")) {
				{
					boolean _setval = false;
					entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Accepted = _setval;
						capability.syncPlayerVariables(entityiterator);
					});
				}
				{
					String _setval = "";
					entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Kingdom = _setval;
						capability.syncPlayerVariables(entityiterator);
					});
				}
				break;
			}
		}
	}
}
