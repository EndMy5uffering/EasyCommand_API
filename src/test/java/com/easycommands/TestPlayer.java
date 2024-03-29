package com.easycommands;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.endmysuffering.easycommands.CMDArgs;

public class TestPlayer implements Player{

    public Set<String> permissions = new HashSet<>();
    public ArrayList<String> messages = new ArrayList<>();
    public CMDArgs lastArgs = null;

    @Override
    public void closeInventory() {
        
    }

    @Override
    public boolean discoverRecipe(NamespacedKey arg0) {
        return false;
    }

    @Override
    public int discoverRecipes(Collection<NamespacedKey> arg0) {
        return 0;
    }

    @Override
    public boolean dropItem(boolean arg0) {
        return false;
    }

    @Override
    public float getAttackCooldown() {
        return 0;
    }

    @Override
    public Location getBedLocation() {
        return null;
    }

    @Override
    public int getCooldown(Material arg0) {
        return 0;
    }

    @Override
    public Set<NamespacedKey> getDiscoveredRecipes() {
        return null;
    }

    @Override
    public Inventory getEnderChest() {
        return null;
    }

    @Override
    public float getExhaustion() {
        return 0;
    }

    @Override
    public int getExpToLevel() {
        return 0;
    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    @Override
    public ItemStack getItemInHand() {
        return null;
    }

    @Override
    public ItemStack getItemInUse() {
        return null;
    }

    @Override
    public ItemStack getItemOnCursor() {
        return null;
    }

    @Override
    public MainHand getMainHand() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public InventoryView getOpenInventory() {
        return null;
    }

    @Override
    public int getSaturatedRegenRate() {
        return 0;
    }

    @Override
    public float getSaturation() {
        return 0;
    }

    @Override
    public Entity getShoulderEntityLeft() {
        return null;
    }

    @Override
    public Entity getShoulderEntityRight() {
        return null;
    }

    @Override
    public int getSleepTicks() {
        return 0;
    }

    @Override
    public int getStarvationRate() {
        return 0;
    }

    @Override
    public int getUnsaturatedRegenRate() {
        return 0;
    }

    @Override
    public boolean hasCooldown(Material arg0) {
        return false;
    }

    @Override
    public boolean hasDiscoveredRecipe(NamespacedKey arg0) {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }

    @Override
    public boolean isHandRaised() {
        return false;
    }

    @Override
    public InventoryView openEnchanting(Location arg0, boolean arg1) {
        return null;
    }

    @Override
    public InventoryView openInventory(Inventory arg0) {
        return null;
    }

    @Override
    public void openInventory(InventoryView arg0) {
        
    }

    @Override
    public InventoryView openMerchant(Villager arg0, boolean arg1) {
        return null;
    }

    @Override
    public InventoryView openMerchant(Merchant arg0, boolean arg1) {
        return null;
    }

    @Override
    public InventoryView openWorkbench(Location arg0, boolean arg1) {
        return null;
    }

    @Override
    public void setCooldown(Material arg0, int arg1) {
        
    }

    @Override
    public void setExhaustion(float arg0) {
        
    }

    @Override
    public void setFoodLevel(int arg0) {
        
    }

    @Override
    public void setGameMode(GameMode arg0) {
        
    }

    @Override
    public void setItemInHand(ItemStack arg0) {
        
    }

    @Override
    public void setItemOnCursor(ItemStack arg0) {
        
    }

    @Override
    public void setSaturatedRegenRate(int arg0) {
        
    }

    @Override
    public void setSaturation(float arg0) {
        
    }

    @Override
    public void setShoulderEntityLeft(Entity arg0) {
        
    }

    @Override
    public void setShoulderEntityRight(Entity arg0) {
        
    }

    @Override
    public void setStarvationRate(int arg0) {
        
    }

    @Override
    public void setUnsaturatedRegenRate(int arg0) {
        
    }

    @Override
    public boolean setWindowProperty(Property arg0, int arg1) {
        return false;
    }

    @Override
    public boolean sleep(Location arg0, boolean arg1) {
        return false;
    }

    @Override
    public boolean undiscoverRecipe(NamespacedKey arg0) {
        return false;
    }

    @Override
    public int undiscoverRecipes(Collection<NamespacedKey> arg0) {
        return 0;
    }

    @Override
    public void wakeup(boolean arg0) {
        
    }

    @Override
    public boolean addPotionEffect(PotionEffect arg0) {
        return false;
    }

    @Override
    public boolean addPotionEffect(PotionEffect arg0, boolean arg1) {
        return false;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> arg0) {
        return false;
    }

    @Override
    public void attack(Entity arg0) {
        
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return null;
    }

    @Override
    public int getArrowCooldown() {
        return 0;
    }

    @Override
    public int getArrowsInBody() {
        return 0;
    }

    @Override
    public boolean getCanPickupItems() {
        return false;
    }

    @Override
    public EntityCategory getCategory() {
        return null;
    }

    @Override
    public Set<UUID> getCollidableExemptions() {
        return null;
    }

    @Override
    public EntityEquipment getEquipment() {
        return null;
    }

    @Override
    public double getEyeHeight() {
        return 0;
    }

    @Override
    public double getEyeHeight(boolean arg0) {
        return 0;
    }

    @Override
    public Location getEyeLocation() {
        return null;
    }

    @Override
    public Player getKiller() {
        return null;
    }

    @Override
    public double getLastDamage() {
        return 0;
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> arg0, int arg1) {
        return null;
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return null;
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> arg0, int arg1) {
        return null;
    }

    @Override
    public int getMaximumAir() {
        return 0;
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return 0;
    }

    @Override
    public <T> T getMemory(MemoryKey<T> arg0) {
        return null;
    }

    @Override
    public int getNoDamageTicks() {
        return 0;
    }

    @Override
    public PotionEffect getPotionEffect(PotionEffectType arg0) {
        return null;
    }

    @Override
    public int getRemainingAir() {
        return 0;
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return false;
    }

    @Override
    public Block getTargetBlock(Set<Material> arg0, int arg1) {
        return null;
    }

    @Override
    public Block getTargetBlockExact(int arg0) {
        return null;
    }

    @Override
    public Block getTargetBlockExact(int arg0, FluidCollisionMode arg1) {
        
        return null;
    }

    @Override
    public boolean hasAI() {
        
        return false;
    }

    @Override
    public boolean hasLineOfSight(Entity arg0) {
        
        return false;
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType arg0) {
        
        return false;
    }

    @Override
    public boolean isClimbing() {
        
        return false;
    }

    @Override
    public boolean isCollidable() {
        
        return false;
    }

    @Override
    public boolean isGliding() {
        
        return false;
    }

    @Override
    public boolean isInvisible() {
        
        return false;
    }

    @Override
    public boolean isLeashed() {
        
        return false;
    }

    @Override
    public boolean isRiptiding() {
        
        return false;
    }

    @Override
    public boolean isSleeping() {
        
        return false;
    }

    @Override
    public boolean isSwimming() {
        
        return false;
    }

    @Override
    public RayTraceResult rayTraceBlocks(double arg0) {
        
        return null;
    }

    @Override
    public RayTraceResult rayTraceBlocks(double arg0, FluidCollisionMode arg1) {
        
        return null;
    }

    @Override
    public void removePotionEffect(PotionEffectType arg0) {
        
        
    }

    @Override
    public void setAI(boolean arg0) {
        
        
    }

    @Override
    public void setArrowCooldown(int arg0) {
        
        
    }

    @Override
    public void setArrowsInBody(int arg0) {
        
        
    }

    @Override
    public void setCanPickupItems(boolean arg0) {
        
        
    }

    @Override
    public void setCollidable(boolean arg0) {
        
        
    }

    @Override
    public void setGliding(boolean arg0) {
        
        
    }

    @Override
    public void setInvisible(boolean arg0) {
        
        
    }

    @Override
    public void setLastDamage(double arg0) {
        
        
    }

    @Override
    public boolean setLeashHolder(Entity arg0) {
        
        return false;
    }

    @Override
    public void setMaximumAir(int arg0) {
        
        
    }

    @Override
    public void setMaximumNoDamageTicks(int arg0) {
        
        
    }

    @Override
    public <T> void setMemory(MemoryKey<T> arg0, T arg1) {
        
        
    }

    @Override
    public void setNoDamageTicks(int arg0) {
        
        
    }

    @Override
    public void setRemainingAir(int arg0) {
        
        
    }

    @Override
    public void setRemoveWhenFarAway(boolean arg0) {
        
        
    }

    @Override
    public void setSwimming(boolean arg0) {
        
        
    }

    @Override
    public void swingMainHand() {
        
        
    }

    @Override
    public void swingOffHand() {
        
        
    }

    @Override
    public AttributeInstance getAttribute(Attribute arg0) {
        
        return null;
    }

    @Override
    public void damage(double arg0) {
        
        
    }

    @Override
    public void damage(double arg0, Entity arg1) {
        
        
    }

    @Override
    public double getAbsorptionAmount() {
        
        return 0;
    }

    @Override
    public double getHealth() {
        
        return 0;
    }

    @Override
    public double getMaxHealth() {
        
        return 0;
    }

    @Override
    public void resetMaxHealth() {
        
        
    }

    @Override
    public void setAbsorptionAmount(double arg0) {
        
        
    }

    @Override
    public void setHealth(double arg0) {
        
        
    }

    @Override
    public void setMaxHealth(double arg0) {
        
        
    }

    @Override
    public boolean addPassenger(Entity arg0) {
        
        return false;
    }

    @Override
    public boolean addScoreboardTag(String arg0) {
        
        return false;
    }

    @Override
    public boolean eject() {
        
        return false;
    }

    @Override
    public BoundingBox getBoundingBox() {
        
        return null;
    }

    @Override
    public int getEntityId() {
        
        return 0;
    }

    @Override
    public BlockFace getFacing() {
        
        return null;
    }

    @Override
    public float getFallDistance() {
        
        return 0;
    }

    @Override
    public int getFireTicks() {
        
        return 0;
    }

    @Override
    public int getFreezeTicks() {
        
        return 0;
    }

    @Override
    public double getHeight() {
        
        return 0;
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        
        return null;
    }

    @Override
    public Location getLocation() {
        
        return null;
    }

    @Override
    public Location getLocation(Location arg0) {
        
        return null;
    }

    @Override
    public int getMaxFireTicks() {
        
        return 0;
    }

    @Override
    public int getMaxFreezeTicks() {
        
        return 0;
    }

    @Override
    public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
        
        return null;
    }

    @Override
    public Entity getPassenger() {
        
        return null;
    }

    @Override
    public List<Entity> getPassengers() {
        
        return null;
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        
        return null;
    }

    @Override
    public int getPortalCooldown() {
        
        return 0;
    }

    @Override
    public Pose getPose() {
        
        return null;
    }

    @Override
    public Set<String> getScoreboardTags() {
        
        return null;
    }

    @Override
    public Server getServer() {
        
        return null;
    }

    @Override
    public SpawnCategory getSpawnCategory() {
        
        return null;
    }

    @Override
    public int getTicksLived() {
        
        return 0;
    }

    @Override
    public EntityType getType() {
        
        return null;
    }

    @Override
    public UUID getUniqueId() {
        
        return null;
    }

    @Override
    public Entity getVehicle() {
        
        return null;
    }

    @Override
    public Vector getVelocity() {
        
        return null;
    }

    @Override
    public double getWidth() {
        
        return 0;
    }

    @Override
    public World getWorld() {
        
        return null;
    }

    @Override
    public boolean hasGravity() {
        
        return false;
    }

    @Override
    public boolean isCustomNameVisible() {
        
        return false;
    }

    @Override
    public boolean isDead() {
        
        return false;
    }

    @Override
    public boolean isEmpty() {
        
        return false;
    }

    @Override
    public boolean isFrozen() {
        
        return false;
    }

    @Override
    public boolean isGlowing() {
        
        return false;
    }

    @Override
    public boolean isInWater() {
        
        return false;
    }

    @Override
    public boolean isInsideVehicle() {
        
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        
        return false;
    }

    @Override
    public boolean isPersistent() {
        
        return false;
    }

    @Override
    public boolean isSilent() {
        
        return false;
    }

    @Override
    public boolean isValid() {
        
        return false;
    }

    @Override
    public boolean isVisualFire() {
        
        return false;
    }

    @Override
    public boolean leaveVehicle() {
        
        return false;
    }

    @Override
    public void playEffect(EntityEffect arg0) {
        
        
    }

    @Override
    public void remove() {
        
        
    }

    @Override
    public boolean removePassenger(Entity arg0) {
        
        return false;
    }

    @Override
    public boolean removeScoreboardTag(String arg0) {
        
        return false;
    }

    @Override
    public void setCustomNameVisible(boolean arg0) {
        
        
    }

    @Override
    public void setFallDistance(float arg0) {
        
        
    }

    @Override
    public void setFireTicks(int arg0) {
        
        
    }

    @Override
    public void setFreezeTicks(int arg0) {
        
        
    }

    @Override
    public void setGlowing(boolean arg0) {
        
        
    }

    @Override
    public void setGravity(boolean arg0) {
        
        
    }

    @Override
    public void setInvulnerable(boolean arg0) {
        
        
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent arg0) {
        
        
    }

    @Override
    public boolean setPassenger(Entity arg0) {
        
        return false;
    }

    @Override
    public void setPersistent(boolean arg0) {
        
        
    }

    @Override
    public void setPortalCooldown(int arg0) {
        
        
    }

    @Override
    public void setRotation(float arg0, float arg1) {
        
        
    }

    @Override
    public void setSilent(boolean arg0) {
        
        
    }

    @Override
    public void setTicksLived(int arg0) {
        
        
    }

    @Override
    public void setVelocity(Vector arg0) {
        
        
    }

    @Override
    public void setVisualFire(boolean arg0) {
        
        
    }

    @Override
    public boolean teleport(Location arg0) {
        
        return false;
    }

    @Override
    public boolean teleport(Entity arg0) {
        
        return false;
    }

    @Override
    public boolean teleport(Location arg0, TeleportCause arg1) {
        
        return false;
    }

    @Override
    public boolean teleport(Entity arg0, TeleportCause arg1) {
        
        return false;
    }

    @Override
    public List<MetadataValue> getMetadata(String arg0) {
        
        return null;
    }

    @Override
    public boolean hasMetadata(String arg0) {
        
        return false;
    }

    @Override
    public void removeMetadata(String arg0, Plugin arg1) {
        
        
    }

    @Override
    public void setMetadata(String arg0, MetadataValue arg1) {
        
        
    }

    @Override
    public void sendMessage(String arg0) {
        this.messages.add(arg0);
    }

    @Override
    public void sendMessage(String... arg0) {
        for(String s: arg0){
            this.messages.add(s);
        }
    }

    @Override
    public void sendMessage(UUID arg0, String arg1) {
        this.messages.add(arg0.toString() + " " + arg1);        
    }

    @Override
    public void sendMessage(UUID arg0, String... arg1) {
        for(String s: arg1){
            this.messages.add(arg0.toString() + " | " + s);
        }
        
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0) {
        
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
        
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
        
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
        
        return null;
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        
        return null;
    }

    @Override
    public boolean hasPermission(String arg0) {
        
        return this.permissions.contains(arg0);
    }

    @Override
    public boolean hasPermission(Permission arg0) {
        
        return false;
    }

    @Override
    public boolean isPermissionSet(String arg0) {
        
        return false;
    }

    @Override
    public boolean isPermissionSet(Permission arg0) {
        
        return false;
    }

    @Override
    public void recalculatePermissions() {
        
        
    }

    @Override
    public void removeAttachment(PermissionAttachment arg0) {
        
        
    }

    @Override
    public boolean isOp() {
        
        return false;
    }

    @Override
    public void setOp(boolean arg0) {
        
        
    }

    @Override
    public String getCustomName() {
        
        return null;
    }

    @Override
    public void setCustomName(String arg0) {
        
        
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> arg0) {
        
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> arg0, Vector arg1) {
        
        return null;
    }

    @Override
    public void abandonConversation(Conversation arg0) {
        
        
    }

    @Override
    public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1) {
        
        
    }

    @Override
    public void acceptConversationInput(String arg0) {
        
        
    }

    @Override
    public boolean beginConversation(Conversation arg0) {
        
        return false;
    }

    @Override
    public boolean isConversing() {
        
        return false;
    }

    @Override
    public void sendRawMessage(UUID arg0, String arg1) {
        
        
    }

    @Override
    public void decrementStatistic(Statistic arg0) throws IllegalArgumentException {
        
        
    }

    @Override
    public void decrementStatistic(Statistic arg0, int arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void decrementStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void decrementStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void decrementStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException {
        
        
    }

    @Override
    public void decrementStatistic(Statistic arg0, EntityType arg1, int arg2) {
        
        
    }

    @Override
    public long getFirstPlayed() {
        
        return 0;
    }

    @Override
    public long getLastPlayed() {
        
        return 0;
    }

    @Override
    public Player getPlayer() {
        
        return this;
    }

    @Override
    public PlayerProfile getPlayerProfile() {
        
        return null;
    }

    @Override
    public int getStatistic(Statistic arg0) throws IllegalArgumentException {
        
        return 0;
    }

    @Override
    public int getStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException {
        
        return 0;
    }

    @Override
    public int getStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException {
        
        return 0;
    }

    @Override
    public boolean hasPlayedBefore() {
        
        return false;
    }

    @Override
    public void incrementStatistic(Statistic arg0) throws IllegalArgumentException {
        
        
    }

    @Override
    public void incrementStatistic(Statistic arg0, int arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void incrementStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void incrementStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void incrementStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException {
        
        
    }

    @Override
    public void incrementStatistic(Statistic arg0, EntityType arg1, int arg2) throws IllegalArgumentException {
        
        
    }

    @Override
    public boolean isBanned() {
        
        return false;
    }

    @Override
    public boolean isOnline() {
        
        return false;
    }

    @Override
    public boolean isWhitelisted() {
        
        return false;
    }

    @Override
    public void setStatistic(Statistic arg0, int arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void setStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException {
        
        
    }

    @Override
    public void setStatistic(Statistic arg0, EntityType arg1, int arg2) {
        
        
    }

    @Override
    public void setWhitelisted(boolean arg0) {
        
        
    }

    @Override
    public Map<String, Object> serialize() {
        
        return null;
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        
        return null;
    }

    @Override
    public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2) {
        
        
    }

    @Override
    public boolean breakBlock(Block arg0) {
        
        return false;
    }

    @Override
    public boolean canSee(Player arg0) {
        
        return false;
    }

    @Override
    public boolean canSee(Entity arg0) {
        
        return false;
    }

    @Override
    public void chat(String arg0) {
        
        
    }

    @Override
    public InetSocketAddress getAddress() {
        
        return null;
    }

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement arg0) {
        
        return null;
    }

    @Override
    public boolean getAllowFlight() {
        
        return false;
    }

    @Override
    public Location getBedSpawnLocation() {
        
        return null;
    }

    @Override
    public int getClientViewDistance() {
        
        return 0;
    }

    @Override
    public Location getCompassTarget() {
        
        return null;
    }

    @Override
    public String getDisplayName() {
        
        return null;
    }

    @Override
    public float getExp() {
        
        return 0;
    }

    @Override
    public float getFlySpeed() {
        
        return 0;
    }

    @Override
    public double getHealthScale() {
        
        return 0;
    }

    @Override
    public int getLevel() {
        
        return 0;
    }

    @Override
    public String getLocale() {
        
        return null;
    }

    @Override
    public int getPing() {
        
        return 0;
    }

    @Override
    public String getPlayerListFooter() {
        
        return null;
    }

    @Override
    public String getPlayerListHeader() {
        
        return null;
    }

    @Override
    public String getPlayerListName() {
        
        return null;
    }

    @Override
    public long getPlayerTime() {
        
        return 0;
    }

    @Override
    public long getPlayerTimeOffset() {
        
        return 0;
    }

    @Override
    public WeatherType getPlayerWeather() {
        
        return null;
    }

    @Override
    public GameMode getPreviousGameMode() {
        
        return null;
    }

    @Override
    public Scoreboard getScoreboard() {
        
        return null;
    }

    @Override
    public Entity getSpectatorTarget() {
        
        return null;
    }

    @Override
    public int getTotalExperience() {
        
        return 0;
    }

    @Override
    public float getWalkSpeed() {
        
        return 0;
    }

    @Override
    public void giveExp(int arg0) {
        
        
    }

    @Override
    public void giveExpLevels(int arg0) {
        
        
    }

    @Override
    public void hideEntity(Plugin arg0, Entity arg1) {
        
        
    }

    @Override
    public void hidePlayer(Player arg0) {
        
        
    }

    @Override
    public void hidePlayer(Plugin arg0, Player arg1) {
        
        
    }

    @Override
    public boolean isAllowingServerListings() {
        
        return false;
    }

    @Override
    public boolean isFlying() {
        
        return false;
    }

    @Override
    public boolean isHealthScaled() {
        
        return false;
    }

    @Override
    public boolean isOnGround() {
        
        return false;
    }

    @Override
    public boolean isPlayerTimeRelative() {
        
        return false;
    }

    @Override
    public boolean isSleepingIgnored() {
        
        return false;
    }

    @Override
    public boolean isSneaking() {
        
        return false;
    }

    @Override
    public boolean isSprinting() {
        
        return false;
    }

    @Override
    public void kickPlayer(String arg0) {
        
        
    }

    @Override
    public void loadData() {
        
        
    }

    @Override
    public void openBook(ItemStack arg0) {
        
        
    }

    @Override
    public void openSign(Sign arg0) {
        
        
    }

    @Override
    public boolean performCommand(String arg0) {
        
        return false;
    }

    @Override
    public void playEffect(Location arg0, Effect arg1, int arg2) {
        
        
    }

    @Override
    public <T> void playEffect(Location arg0, Effect arg1, T arg2) {
        
        
    }

    @Override
    public void playNote(Location arg0, byte arg1, byte arg2) {
        
        
    }

    @Override
    public void playNote(Location arg0, Instrument arg1, Note arg2) {
        
        
    }

    @Override
    public void playSound(Location arg0, Sound arg1, float arg2, float arg3) {
        
        
    }

    @Override
    public void playSound(Location arg0, String arg1, float arg2, float arg3) {
        
        
    }

    @Override
    public void playSound(Entity arg0, Sound arg1, float arg2, float arg3) {
        
        
    }

    @Override
    public void playSound(Location arg0, Sound arg1, SoundCategory arg2, float arg3, float arg4) {
        
        
    }

    @Override
    public void playSound(Location arg0, String arg1, SoundCategory arg2, float arg3, float arg4) {
        
        
    }

    @Override
    public void playSound(Entity arg0, Sound arg1, SoundCategory arg2, float arg3, float arg4) {
        
        
    }

    @Override
    public void resetPlayerTime() {
        
        
    }

    @Override
    public void resetPlayerWeather() {
        
        
    }

    @Override
    public void resetTitle() {
        
        
    }

    @Override
    public void saveData() {
        
        
    }

    @Override
    public void sendBlockChange(Location arg0, BlockData arg1) {
        
        
    }

    @Override
    public void sendBlockChange(Location arg0, Material arg1, byte arg2) {
        
        
    }

    @Override
    public void sendBlockDamage(Location arg0, float arg1) {
        
        
    }

    @Override
    public void sendEquipmentChange(LivingEntity arg0, EquipmentSlot arg1, ItemStack arg2) {
        
        
    }

    @Override
    public void sendExperienceChange(float arg0) {
        
        
    }

    @Override
    public void sendExperienceChange(float arg0, int arg1) {
        
        
    }

    @Override
    public void sendMap(MapView arg0) {
        
        
    }

    @Override
    public void sendRawMessage(String arg0) {
        
        
    }

    @Override
    public void sendSignChange(Location arg0, String[] arg1) throws IllegalArgumentException {
        
        
    }

    @Override
    public void sendSignChange(Location arg0, String[] arg1, DyeColor arg2) throws IllegalArgumentException {
        
        
    }

    @Override
    public void sendSignChange(Location arg0, String[] arg1, DyeColor arg2, boolean arg3)
            throws IllegalArgumentException {
        
        
    }

    @Override
    public void sendTitle(String arg0, String arg1) {
        
        
    }

    @Override
    public void sendTitle(String arg0, String arg1, int arg2, int arg3, int arg4) {
        
        
    }

    @Override
    public void setAllowFlight(boolean arg0) {
        
        
    }

    @Override
    public void setBedSpawnLocation(Location arg0) {
        
        
    }

    @Override
    public void setBedSpawnLocation(Location arg0, boolean arg1) {
        
        
    }

    @Override
    public void setCompassTarget(Location arg0) {
        
        
    }

    @Override
    public void setDisplayName(String arg0) {
        
        
    }

    @Override
    public void setExp(float arg0) {
        
        
    }

    @Override
    public void setFlySpeed(float arg0) throws IllegalArgumentException {
        
        
    }

    @Override
    public void setFlying(boolean arg0) {
        
        
    }

    @Override
    public void setHealthScale(double arg0) throws IllegalArgumentException {
        
        
    }

    @Override
    public void setHealthScaled(boolean arg0) {
        
        
    }

    @Override
    public void setLevel(int arg0) {
        
        
    }

    @Override
    public void setPlayerListFooter(String arg0) {
        
        
    }

    @Override
    public void setPlayerListHeader(String arg0) {
        
        
    }

    @Override
    public void setPlayerListHeaderFooter(String arg0, String arg1) {
        
        
    }

    @Override
    public void setPlayerListName(String arg0) {
        
        
    }

    @Override
    public void setPlayerTime(long arg0, boolean arg1) {
        
        
    }

    @Override
    public void setPlayerWeather(WeatherType arg0) {
        
        
    }

    @Override
    public void setResourcePack(String arg0) {
        
        
    }

    @Override
    public void setResourcePack(String arg0, byte[] arg1) {
        
        
    }

    @Override
    public void setResourcePack(String arg0, byte[] arg1, String arg2) {
        
        
    }

    @Override
    public void setResourcePack(String arg0, byte[] arg1, boolean arg2) {
        
        
    }

    @Override
    public void setResourcePack(String arg0, byte[] arg1, String arg2, boolean arg3) {
        
        
    }

    @Override
    public void setScoreboard(Scoreboard arg0) throws IllegalArgumentException, IllegalStateException {
        
        
    }

    @Override
    public void setSleepingIgnored(boolean arg0) {
        
        
    }

    @Override
    public void setSneaking(boolean arg0) {
        
        
    }

    @Override
    public void setSpectatorTarget(Entity arg0) {
        
        
    }

    @Override
    public void setSprinting(boolean arg0) {
        
        
    }

    @Override
    public void setTexturePack(String arg0) {
        
        
    }

    @Override
    public void setTotalExperience(int arg0) {
        
        
    }

    @Override
    public void setWalkSpeed(float arg0) throws IllegalArgumentException {
        
        
    }

    @Override
    public void showDemoScreen() {
        
        
    }

    @Override
    public void showEntity(Plugin arg0, Entity arg1) {
        
        
    }

    @Override
    public void showPlayer(Player arg0) {
        
        
    }

    @Override
    public void showPlayer(Plugin arg0, Player arg1) {
        
        
    }

    @Override
    public void spawnParticle(Particle arg0, Location arg1, int arg2) {
        
        
    }

    @Override
    public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, T arg3) {
        
        
    }

    @Override
    public void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4) {
        
        
    }

    @Override
    public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, T arg5) {
        
        
    }

    @Override
    public void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5) {
        
        
    }

    @Override
    public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5,
            T arg6) {
        
        
    }

    @Override
    public void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5,
            double arg6) {
        
        
    }

    @Override
    public void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6,
            double arg7) {
        
        
    }

    @Override
    public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5,
            double arg6, T arg7) {
        
        
    }

    @Override
    public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5,
            double arg6, double arg7, T arg8) {
        
        
    }

    @Override
    public void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6,
            double arg7, double arg8) {
        
        
    }

    @Override
    public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5,
            double arg6, double arg7, double arg8, T arg9) {
        
        
    }

    @Override
    public Spigot spigot() {
        
        return null;
    }

    @Override
    public void stopAllSounds() {
        
        
    }

    @Override
    public void stopSound(Sound arg0) {
        
        
    }

    @Override
    public void stopSound(String arg0) {
        
        
    }

    @Override
    public void stopSound(Sound arg0, SoundCategory arg1) {
        
        
    }

    @Override
    public void stopSound(String arg0, SoundCategory arg1) {
        
        
    }

    @Override
    public void updateCommands() {
        
        
    }

    @Override
    public void updateInventory() {
        
        
    }

    @Override
    public WorldBorder getWorldBorder() {
        
        return null;
    }

    @Override
    public void setWorldBorder(WorldBorder arg0) {
        
        
    }

    
}
