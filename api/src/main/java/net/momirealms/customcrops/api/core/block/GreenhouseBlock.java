package net.momirealms.customcrops.api.core.block;

import net.momirealms.customcrops.api.BukkitCustomCropsPlugin;
import net.momirealms.customcrops.api.core.BuiltInBlockMechanics;
import net.momirealms.customcrops.api.core.ConfigManager;
import net.momirealms.customcrops.api.core.world.CustomCropsBlockState;
import net.momirealms.customcrops.api.core.world.CustomCropsWorld;
import net.momirealms.customcrops.api.core.world.Pos3;
import net.momirealms.customcrops.api.core.wrapper.WrappedBreakEvent;
import net.momirealms.customcrops.api.core.wrapper.WrappedInteractEvent;
import net.momirealms.customcrops.api.core.wrapper.WrappedPlaceEvent;
import net.momirealms.customcrops.api.event.GreenhouseGlassBreakEvent;
import net.momirealms.customcrops.api.event.GreenhouseGlassInteractEvent;
import net.momirealms.customcrops.api.event.GreenhouseGlassPlaceEvent;
import net.momirealms.customcrops.api.util.EventUtils;

import java.util.Optional;

public class GreenhouseBlock extends AbstractCustomCropsBlock {

    public GreenhouseBlock() {
        super(BuiltInBlockMechanics.GREENHOUSE.key());
    }

    @Override
    public void randomTick(CustomCropsBlockState state, CustomCropsWorld<?> world, Pos3 location) {
        //tickGreenhouse(world, location);
    }

    @Override
    public void scheduledTick(CustomCropsBlockState state, CustomCropsWorld<?> world, Pos3 location) {
        tickGreenhouse(world, location);
    }

    private void tickGreenhouse(CustomCropsWorld<?> world, Pos3 location) {
        if (!ConfigManager.doubleCheck()) return;
        String id = BukkitCustomCropsPlugin.getInstance().getItemManager().id(location.toLocation(world.bukkitWorld()), ConfigManager.greenhouseExistenceForm());
        if (ConfigManager.greenhouse().contains(id)) return;
        // remove outdated data
        BukkitCustomCropsPlugin.getInstance().getPluginLogger().warn("Greenhouse is removed at location[" + world.worldName() + "," + location + "] because the id of the block/furniture is [" + id + "]");
        world.removeBlockState(location);
    }

    @Override
    public void onInteract(WrappedInteractEvent event) {
        CustomCropsWorld<?> world = event.world();
        Pos3 pos3 = Pos3.from(event.location());
        CustomCropsBlockState state = getOrFixState(world, pos3);
        GreenhouseGlassInteractEvent interactEvent = new GreenhouseGlassInteractEvent(event.player(), event.itemInHand(), event.location(), event.relatedID(), state, event.hand());
        EventUtils.fireAndForget(interactEvent);
    }

    @Override
    public void onBreak(WrappedBreakEvent event) {
        CustomCropsWorld<?> world = event.world();
        Pos3 pos3 = Pos3.from(event.location());
        CustomCropsBlockState state = getOrFixState(world, pos3);
        GreenhouseGlassBreakEvent breakEvent = new GreenhouseGlassBreakEvent(event.entityBreaker(), event.blockBreaker(), event.location(), event.brokenID(), state, event.reason());
        if (EventUtils.fireAndCheckCancel(breakEvent)) {
            return;
        }
        world.removeBlockState(pos3);
    }

    @Override
    public void onPlace(WrappedPlaceEvent event) {
        CustomCropsBlockState state = createBlockState();
        GreenhouseGlassPlaceEvent placeEvent = new GreenhouseGlassPlaceEvent(event.player(), event.location(), event.itemID(), state);
        if (EventUtils.fireAndCheckCancel(placeEvent)) {
            return;
        }
        Pos3 pos3 = Pos3.from(event.location());
        CustomCropsWorld<?> world = event.world();
        world.addBlockState(pos3, state).ifPresent(previous -> {
            BukkitCustomCropsPlugin.getInstance().debug(
                    "Overwrite old data with " + state.compoundMap().toString() +
                            " at location[" + world.worldName() + "," + pos3 + "] which used to be " + previous.compoundMap().toString()
            );
        });
    }

    public CustomCropsBlockState getOrFixState(CustomCropsWorld<?> world, Pos3 pos3) {
        Optional<CustomCropsBlockState> optional = world.getBlockState(pos3);
        if (optional.isPresent() && optional.get().type() instanceof GreenhouseBlock) {
            return optional.get();
        }
        CustomCropsBlockState state = createBlockState();
        world.addBlockState(pos3, state).ifPresent(previous -> {
            BukkitCustomCropsPlugin.getInstance().debug(
                    "Overwrite old data with " + state.compoundMap().toString() +
                            " at pos3[" + world.worldName() + "," + pos3 + "] which used to be " + previous.compoundMap().toString()
            );
        });
        return state;
    }
}
