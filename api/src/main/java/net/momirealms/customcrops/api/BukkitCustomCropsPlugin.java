/*
 *  Copyright (C) <2024> <XiaoMoMi>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.momirealms.customcrops.api;

import net.momirealms.customcrops.api.action.ActionManager;
import net.momirealms.customcrops.api.core.AbstractItemManager;
import net.momirealms.customcrops.api.core.ConfigManager;
import net.momirealms.customcrops.api.core.RegistryAccess;
import net.momirealms.customcrops.api.core.world.WorldManager;
import net.momirealms.customcrops.api.integration.IntegrationManager;
import net.momirealms.customcrops.api.misc.cooldown.CoolDownManager;
import net.momirealms.customcrops.api.misc.placeholder.PlaceholderManager;
import net.momirealms.customcrops.api.requirement.RequirementManager;
import net.momirealms.customcrops.common.dependency.DependencyManager;
import net.momirealms.customcrops.common.locale.TranslationManager;
import net.momirealms.customcrops.common.plugin.CustomCropsPlugin;
import net.momirealms.customcrops.common.plugin.scheduler.AbstractJavaScheduler;
import net.momirealms.customcrops.common.plugin.scheduler.SchedulerAdapter;
import net.momirealms.customcrops.common.sender.SenderFactory;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class BukkitCustomCropsPlugin implements CustomCropsPlugin {

    private static BukkitCustomCropsPlugin instance;
    private final Plugin boostrap;

    protected AbstractJavaScheduler<Location, World> scheduler;
    protected DependencyManager dependencyManager;
    protected TranslationManager translationManager;
    protected AbstractItemManager itemManager;
    protected PlaceholderManager placeholderManager;
    protected CoolDownManager coolDownManager;
    protected ConfigManager configManager;
    protected IntegrationManager integrationManager;
    protected WorldManager worldManager;
    protected RegistryAccess registryAccess;
    protected SenderFactory<BukkitCustomCropsPlugin, CommandSender> senderFactory;

    protected final Map<Class<?>, ActionManager<?>> actionManagers = new HashMap<>();
    protected final Map<Class<?>, RequirementManager<?>> requirementManagers = new HashMap<>();

    /**
     * Constructs a new BukkitCustomCropsPlugin instance.
     *
     * @param boostrap the plugin instance used to initialize this class
     */
    public BukkitCustomCropsPlugin(Plugin boostrap) {
        if (!boostrap.getName().equals("CustomCrops")) {
            throw new IllegalArgumentException("CustomCrops plugin requires custom crops plugin");
        }
        this.boostrap = boostrap;
        instance = this;
    }

    /**
     * Retrieves the singleton instance of BukkitCustomFishingPlugin.
     *
     * @return the singleton instance
     * @throws IllegalArgumentException if the plugin is not initialized
     */
    public static BukkitCustomCropsPlugin getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("Plugin not initialized");
        }
        return instance;
    }

    /**
     * Retrieves the plugin instance used to initialize this class.
     *
     * @return the {@link Plugin} instance
     */
    public Plugin getBoostrap() {
        return boostrap;
    }

    /**
     * Retrieves the DependencyManager.
     *
     * @return the {@link DependencyManager}
     */
    @Override
    public DependencyManager getDependencyManager() {
        return dependencyManager;
    }

    /**
     * Retrieves the TranslationManager.
     *
     * @return the {@link TranslationManager}
     */
    @Override
    public TranslationManager getTranslationManager() {
        return translationManager;
    }

    public AbstractItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public SchedulerAdapter<Location, World> getScheduler() {
        return scheduler;
    }

    public SenderFactory<BukkitCustomCropsPlugin, CommandSender> getSenderFactory() {
        return senderFactory;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public IntegrationManager getIntegrationManager() {
        return integrationManager;
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    /**
     * Logs a debug message.
     *
     * @param message the message to log
     */
    public abstract void debug(Object message);

    public File getDataFolder() {
        return boostrap.getDataFolder();
    }

    public RegistryAccess getRegistryAccess() {
        return registryAccess;
    }

    @SuppressWarnings("unchecked")
    public <T> ActionManager<T> getActionManager(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        return (ActionManager<T>) actionManagers.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T> RequirementManager<T> getRequirementManager(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        return (RequirementManager<T>) instance.requirementManagers.get(type);
    }

    public CoolDownManager getCoolDownManager() {
        return coolDownManager;
    }
}
