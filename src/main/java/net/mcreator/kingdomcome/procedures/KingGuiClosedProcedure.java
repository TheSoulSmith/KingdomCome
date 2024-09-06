package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.HashMap;
import java.util.ArrayList;

public class KingGuiClosedProcedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		String oldkingdomname = "";
		oldkingdomname = (entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((oldkingdomname).equals((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)) {
				{
					String _setval = guistate.containsKey("text:KingdomName") ? ((EditBox) guistate.get("text:KingdomName")).getValue() : "";
					entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Kingdom = _setval;
						capability.syncPlayerVariables(entityiterator);
					});
				}
				{
					boolean _setval = guistate.containsKey("checkbox:FriendlyFire") ? ((Checkbox) guistate.get("checkbox:FriendlyFire")).selected() : false;
					entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.FriendlyFire = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		{
			boolean _setval = guistate.containsKey("checkbox:FreeEntry") ? ((Checkbox) guistate.get("checkbox:FreeEntry")).selected() : false;
			entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.FreeEntry = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
