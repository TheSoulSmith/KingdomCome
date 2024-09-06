
package net.mcreator.kingdomcome.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.kingdomcome.world.inventory.StatelessGuiMenu;
import net.mcreator.kingdomcome.procedures.RequestEntryProcedure;
import net.mcreator.kingdomcome.procedures.JoinFirstKingdomSearchResultProcedure;
import net.mcreator.kingdomcome.procedures.DeclineInviteProcedure;
import net.mcreator.kingdomcome.procedures.AcceptInviteProcedure;
import net.mcreator.kingdomcome.KingdomComeMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatelessGuiButtonMessage {
	private final int buttonID, x, y, z;

	public StatelessGuiButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public StatelessGuiButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(StatelessGuiButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(StatelessGuiButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level;
		HashMap guistate = StatelessGuiMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			JoinFirstKingdomSearchResultProcedure.execute(world, entity, guistate);
		}
		if (buttonID == 1) {

			RequestEntryProcedure.execute(world, entity, guistate);
		}
		if (buttonID == 2) {

			AcceptInviteProcedure.execute(world, entity);
		}
		if (buttonID == 3) {

			DeclineInviteProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		KingdomComeMod.addNetworkMessage(StatelessGuiButtonMessage.class, StatelessGuiButtonMessage::buffer, StatelessGuiButtonMessage::new, StatelessGuiButtonMessage::handler);
	}
}
