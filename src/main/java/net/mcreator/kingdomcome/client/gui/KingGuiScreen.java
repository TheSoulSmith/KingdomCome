package net.mcreator.kingdomcome.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.Minecraft;

import net.mcreator.kingdomcome.world.inventory.KingGuiMenu;
import net.mcreator.kingdomcome.procedures.PlayerValidToInviteProcedure;
import net.mcreator.kingdomcome.procedures.IsInvitedToProcedure;
import net.mcreator.kingdomcome.procedures.IsInKingdomProcedure;
import net.mcreator.kingdomcome.procedures.HasRequestedEntryProcedure;
import net.mcreator.kingdomcome.procedures.FoundPlayerSearchResultProcedure;
import net.mcreator.kingdomcome.procedures.FirstPlayerSearchResultProcedure;
import net.mcreator.kingdomcome.procedures.FirstPlayerSearchResultNameProcedure;
import net.mcreator.kingdomcome.procedures.CurrentKingdomFreeEntryProcedure;
import net.mcreator.kingdomcome.network.KingGuiButtonMessage;
import net.mcreator.kingdomcome.KingdomComeMod;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

public class KingGuiScreen extends AbstractContainerScreen<KingGuiMenu> {
	private final static HashMap<String, Object> guistate = KingGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox PlayerSearchBox;
	EditBox KingdomName;
	Checkbox FreeEntry;
	Checkbox FriendlyFire;
	Button button_invite;
	Button button_revoke_invite;
	Button button_accept_citizenship_request;
	Button button_banish;

	public KingGuiScreen(KingGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 255;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("kingdom_come:textures/screens/king_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		PlayerSearchBox.render(ms, mouseX, mouseY, partialTicks);
		KingdomName.render(ms, mouseX, mouseY, partialTicks);
		if (FirstPlayerSearchResultProcedure.execute(world, guistate) instanceof LivingEntity livingEntity) {
			if (FoundPlayerSearchResultProcedure.execute(world, guistate))
				InventoryScreen.renderEntityInInventoryRaw(this.leftPos + 56, this.topPos + 196, 30, 0f + (float) Math.atan((this.leftPos + 56 - mouseX) / 40.0), (float) Math.atan((this.topPos + 147 - mouseY) / 40.0), livingEntity);
		}
		this.renderTooltip(ms, mouseX, mouseY);
		if (mouseX > leftPos + 3 && mouseX < leftPos + 114 && mouseY > topPos + 45 && mouseY < topPos + 65)
			this.renderTooltip(ms, Component.translatable("gui.kingdom_come.king_gui.tooltip_allow_players_to_join_without_an"), mouseX, mouseY);
		if (mouseX > leftPos + 3 && mouseX < leftPos + 250 && mouseY > topPos + 3 && mouseY < topPos + 23)
			this.renderTooltip(ms, Component.translatable("gui.kingdom_come.king_gui.tooltip_the_name_of_your_kingdom_you_ca"), mouseX, mouseY);
		if (mouseX > leftPos + 130 && mouseX < leftPos + 226 && mouseY > topPos + 44 && mouseY < topPos + 65)
			this.renderTooltip(ms, Component.translatable("gui.kingdom_come.king_gui.tooltip_allow_citizens_of_your_kingdom_t"), mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		if (PlayerSearchBox.isFocused())
			return PlayerSearchBox.keyPressed(key, b, c);
		if (KingdomName.isFocused())
			return KingdomName.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		PlayerSearchBox.tick();
		KingdomName.tick();
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String PlayerSearchBoxValue = PlayerSearchBox.getValue();
		String KingdomNameValue = KingdomName.getValue();
		super.resize(minecraft, width, height);
		PlayerSearchBox.setValue(PlayerSearchBoxValue);
		KingdomName.setValue(KingdomNameValue);
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack,

				FirstPlayerSearchResultNameProcedure.execute(world, guistate), 4, 65, -12829636);
	}

	@Override
	public void init() {
		super.init();
		PlayerSearchBox = new EditBox(this.font, this.leftPos + 4, this.topPos + 26, 246, 18, Component.translatable("gui.kingdom_come.king_gui.PlayerSearchBox"));
		PlayerSearchBox.setMaxLength(32767);
		guistate.put("text:PlayerSearchBox", PlayerSearchBox);
		this.addWidget(this.PlayerSearchBox);
		KingdomName = new EditBox(this.font, this.leftPos + 4, this.topPos + 4, 246, 18, Component.translatable("gui.kingdom_come.king_gui.KingdomName"));
		KingdomName.setMaxLength(32767);
		guistate.put("text:KingdomName", KingdomName);
		this.addWidget(this.KingdomName);
		button_invite = new Button(this.leftPos + 111, this.topPos + 177, 141, 20, Component.translatable("gui.kingdom_come.king_gui.button_invite"), e -> {
			if (PlayerValidToInviteProcedure.execute(world, guistate)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new KingGuiButtonMessage(0, x, y, z));
				KingGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (PlayerValidToInviteProcedure.execute(world, guistate))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_invite", button_invite);
		this.addRenderableWidget(button_invite);
		button_revoke_invite = new Button(this.leftPos + 111, this.topPos + 157, 141, 20, Component.translatable("gui.kingdom_come.king_gui.button_revoke_invite"), e -> {
			if (IsInvitedToProcedure.execute(world, entity, guistate)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new KingGuiButtonMessage(1, x, y, z));
				KingGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (IsInvitedToProcedure.execute(world, entity, guistate))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_revoke_invite", button_revoke_invite);
		this.addRenderableWidget(button_revoke_invite);
		button_accept_citizenship_request = new Button(this.leftPos + 111, this.topPos + 137, 141, 20, Component.translatable("gui.kingdom_come.king_gui.button_accept_citizenship_request"), e -> {
			if (HasRequestedEntryProcedure.execute(world, entity, guistate)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new KingGuiButtonMessage(2, x, y, z));
				KingGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (HasRequestedEntryProcedure.execute(world, entity, guistate))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_accept_citizenship_request", button_accept_citizenship_request);
		this.addRenderableWidget(button_accept_citizenship_request);
		button_banish = new Button(this.leftPos + 111, this.topPos + 117, 141, 20, Component.translatable("gui.kingdom_come.king_gui.button_banish"), e -> {
			if (IsInKingdomProcedure.execute(world, entity, guistate)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new KingGuiButtonMessage(3, x, y, z));
				KingGuiButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (IsInKingdomProcedure.execute(world, entity, guistate))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_banish", button_banish);
		this.addRenderableWidget(button_banish);
		FreeEntry = new Checkbox(this.leftPos + 4, this.topPos + 45, 20, 20, Component.translatable("gui.kingdom_come.king_gui.FreeEntry"),

				CurrentKingdomFreeEntryProcedure.execute(entity));
		guistate.put("checkbox:FreeEntry", FreeEntry);
		this.addRenderableWidget(FreeEntry);
		FriendlyFire = new Checkbox(this.leftPos + 131, this.topPos + 45, 20, 20, Component.translatable("gui.kingdom_come.king_gui.FriendlyFire"), false);
		guistate.put("checkbox:FriendlyFire", FriendlyFire);
		this.addRenderableWidget(FriendlyFire);
	}
}
