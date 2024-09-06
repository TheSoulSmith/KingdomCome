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
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.Minecraft;

import net.mcreator.kingdomcome.world.inventory.StatelessGuiMenu;
import net.mcreator.kingdomcome.procedures.InvitedToProcedure;
import net.mcreator.kingdomcome.procedures.HasInviteProcedure;
import net.mcreator.kingdomcome.procedures.FoundKingdomSearchResultProcedure;
import net.mcreator.kingdomcome.procedures.FirstKingdomSearchResultProcedure;
import net.mcreator.kingdomcome.procedures.FirstKingdomSearchResultNoFreeEntryProcedure;
import net.mcreator.kingdomcome.procedures.FirstKingdomSearchResultFreeEntryProcedure;
import net.mcreator.kingdomcome.procedures.FirstKingSearchResultProcedure;
import net.mcreator.kingdomcome.procedures.FirstKingSearchResultNameProcedure;
import net.mcreator.kingdomcome.network.StatelessGuiButtonMessage;
import net.mcreator.kingdomcome.KingdomComeMod;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

public class StatelessGuiScreen extends AbstractContainerScreen<StatelessGuiMenu> {
	private final static HashMap<String, Object> guistate = StatelessGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox KingdomSearchBox;
	Button button_join;
	Button button_empty;
	Button button_accept;
	Button button_decline;

	public StatelessGuiScreen(StatelessGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("kingdom_come:textures/screens/stateless_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		KingdomSearchBox.render(ms, mouseX, mouseY, partialTicks);
		if (FirstKingSearchResultProcedure.execute(world, guistate) instanceof LivingEntity livingEntity) {
			if (FoundKingdomSearchResultProcedure.execute(world, guistate))
				InventoryScreen.renderEntityInInventoryRaw(this.leftPos + 42, this.topPos + 163, 30, 0f, 0, livingEntity);
		}
		this.renderTooltip(ms, mouseX, mouseY);
		if (mouseX > leftPos + 2 && mouseX < leftPos + 172 && mouseY > topPos + 70 && mouseY < topPos + 90)
			this.renderTooltip(ms, Component.translatable("gui.kingdom_come.stateless_gui.tooltip_the_name_and_king_of_the_first_k"), mouseX, mouseY);
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
		if (KingdomSearchBox.isFocused())
			return KingdomSearchBox.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		KingdomSearchBox.tick();
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String KingdomSearchBoxValue = KingdomSearchBox.getValue();
		super.resize(minecraft, width, height);
		KingdomSearchBox.setValue(KingdomSearchBoxValue);
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack,

				FirstKingdomSearchResultProcedure.execute(world, guistate), 3, 69, -12829636);
		this.font.draw(poseStack, Component.translatable("gui.kingdom_come.stateless_gui.label_king"), 3, 79, -12829636);
		this.font.draw(poseStack,

				FirstKingSearchResultNameProcedure.execute(world, guistate), 32, 79, -12829636);
		if (HasInviteProcedure.execute(entity))
			this.font.draw(poseStack, Component.translatable("gui.kingdom_come.stateless_gui.label_you_are_currently_invited_to"), 3, 24, -12829636);
		if (HasInviteProcedure.execute(entity))
			this.font.draw(poseStack,

					InvitedToProcedure.execute(entity), 3, 34, -12829636);
	}

	@Override
	public void init() {
		super.init();
		KingdomSearchBox = new EditBox(this.font, this.leftPos + 4, this.topPos + 4, 167, 18, Component.translatable("gui.kingdom_come.stateless_gui.KingdomSearchBox"));
		KingdomSearchBox.setMaxLength(32767);
		guistate.put("text:KingdomSearchBox", KingdomSearchBox);
		this.addWidget(this.KingdomSearchBox);
		button_join = new Button(this.leftPos + 74, this.topPos + 143, 99, 20, Component.translatable("gui.kingdom_come.stateless_gui.button_join"), e -> {
			if (FirstKingdomSearchResultFreeEntryProcedure.execute(world, entity, guistate)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new StatelessGuiButtonMessage(0, x, y, z));
				StatelessGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (FirstKingdomSearchResultFreeEntryProcedure.execute(world, entity, guistate))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_join", button_join);
		this.addRenderableWidget(button_join);
		button_empty = new Button(this.leftPos + 74, this.topPos + 90, 99, 20, Component.translatable("gui.kingdom_come.stateless_gui.button_empty"), e -> {
			if (FirstKingdomSearchResultNoFreeEntryProcedure.execute(world, entity, guistate)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new StatelessGuiButtonMessage(1, x, y, z));
				StatelessGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (FirstKingdomSearchResultNoFreeEntryProcedure.execute(world, entity, guistate))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_accept = new Button(this.leftPos + 2, this.topPos + 44, 56, 20, Component.translatable("gui.kingdom_come.stateless_gui.button_accept"), e -> {
			if (HasInviteProcedure.execute(entity)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new StatelessGuiButtonMessage(2, x, y, z));
				StatelessGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (HasInviteProcedure.execute(entity))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_accept", button_accept);
		this.addRenderableWidget(button_accept);
		button_decline = new Button(this.leftPos + 112, this.topPos + 44, 61, 20, Component.translatable("gui.kingdom_come.stateless_gui.button_decline"), e -> {
			if (HasInviteProcedure.execute(entity)) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new StatelessGuiButtonMessage(3, x, y, z));
				StatelessGuiButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void render(PoseStack ms, int gx, int gy, float ticks) {
				if (HasInviteProcedure.execute(entity))
					super.render(ms, gx, gy, ticks);
			}
		};
		guistate.put("button:button_decline", button_decline);
		this.addRenderableWidget(button_decline);
	}
}
