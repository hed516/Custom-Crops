package net.momirealms.customcrops.bukkit.integration.custom.nexo_r1;

import com.nexomc.nexo.api.events.custom_block.noteblock.NexoNoteBlockBreakEvent;
import com.nexomc.nexo.api.events.custom_block.noteblock.NexoNoteBlockInteractEvent;
import com.nexomc.nexo.api.events.custom_block.noteblock.NexoNoteBlockPlaceEvent;
import com.nexomc.nexo.api.events.custom_block.stringblock.NexoStringBlockBreakEvent;
import com.nexomc.nexo.api.events.custom_block.stringblock.NexoStringBlockInteractEvent;
import com.nexomc.nexo.api.events.custom_block.stringblock.NexoStringBlockPlaceEvent;
import com.nexomc.nexo.api.events.furniture.NexoFurnitureBreakEvent;
import com.nexomc.nexo.api.events.furniture.NexoFurnitureInteractEvent;
import com.nexomc.nexo.api.events.furniture.NexoFurniturePlaceEvent;
import net.momirealms.customcrops.api.core.AbstractCustomEventListener;
import net.momirealms.customcrops.api.core.AbstractItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NexoListener extends AbstractCustomEventListener {

	private static final Set<Material> IGNORED = new HashSet<>(
			List.of(
					Material.NOTE_BLOCK,
					Material.TRIPWIRE
			)
	);

	@Override
	protected Set<Material> ignoredMaterials() {
		return IGNORED;
	}

	public NexoListener(AbstractItemManager itemManager) {
		super(itemManager);
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteractFurniture(NexoFurnitureInteractEvent event) {
		itemManager.handlePlayerInteractFurniture(
				event.getPlayer(),
				event.getBaseEntity().getLocation(), event.getMechanic().getItemID(),
				event.getHand(), event.getItemInHand(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteractCustomBlock(NexoNoteBlockInteractEvent event) {
		itemManager.handlePlayerInteractBlock(
				event.getPlayer(),
				event.getBlock(),
				event.getMechanic().getItemID(), event.getBlockFace(),
				event.getHand(),
				event.getItemInHand(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteractCustomBlock(NexoStringBlockInteractEvent event) {
		itemManager.handlePlayerInteractBlock(
				event.getPlayer(),
				event.getBlock(),
				event.getMechanic().getItemID(), event.getBlockFace(),
				event.getHand(),
				event.getItemInHand(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBreakFurniture(NexoFurnitureBreakEvent event) {
		itemManager.handlePlayerBreak(
				event.getPlayer(),
				event.getBaseEntity().getLocation(), event.getPlayer().getInventory().getItemInMainHand(), event.getMechanic().getItemID(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBreakCustomBlock(NexoNoteBlockBreakEvent event) {
		itemManager.handlePlayerBreak(
				event.getPlayer(),
				event.getBlock().getLocation(), event.getPlayer().getInventory().getItemInMainHand(), event.getMechanic().getItemID(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBreakCustomBlock(NexoStringBlockBreakEvent event) {
		itemManager.handlePlayerBreak(
				event.getPlayer(),
				event.getBlock().getLocation(), event.getPlayer().getInventory().getItemInMainHand(), event.getMechanic().getItemID(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlaceFurniture(NexoFurniturePlaceEvent event) {
		itemManager.handlePlayerPlace(
				event.getPlayer(),
				event.getBaseEntity().getLocation(),
				event.getMechanic().getItemID(),
				event.getHand(),
				event.getItemInHand(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlaceCustomBlock(NexoNoteBlockPlaceEvent event) {
		itemManager.handlePlayerPlace(
				event.getPlayer(),
				event.getBlock().getLocation(),
				event.getMechanic().getItemID(),
				event.getHand(),
				event.getItemInHand(),
				event
		);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlaceCustomBlock(NexoStringBlockPlaceEvent event) {
		itemManager.handlePlayerPlace(
				event.getPlayer(),
				event.getBlock().getLocation(),
				event.getMechanic().getItemID(),
				event.getHand(),
				event.getItemInHand(),
				event
		);
	}
}
