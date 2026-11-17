package com.adri1711.randomevents.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Event;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;
import java.lang.reflect.Method;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.compat.XMaterial;
import com.adri1711.randomevents.compat.XSound;

public class WeaponShoot implements Listener {

	private RandomEvents plugin;

	public WeaponShoot(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void weaponDamage(Object ev) // Listens to
															// EntityDamageEvent
	{
		if (get(ev, "getVictim") instanceof Player) {

			Player player = (Player) get(ev, "getVictim");
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
				if (!plugin.getMatchActive().getPlaying()) {
					set(ev, "setCancelled", true);
				} else {
					if (get(ev, "getPlayer") instanceof Player) {
						Player damager = (Player) get(ev, "getPlayer");
						if (plugin.getMatchActive().getEquipo(player) != null
								&& plugin.getMatchActive().getEquipo(damager) != null && plugin.getMatchActive()
										.getEquipo(player).equals(plugin.getMatchActive().getEquipo(damager))) {
							set(ev, "setCancelled", true);
						} else {
							if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())) {

								if (((player.getHealth() - number(ev, "getDamage")) <= 0)) {

									set(ev, "setCancelled", true);
									switch (plugin.getMatchActive().getMatch().getMinigame()) {
									case SG:
									case SW:
									case TSG:
									case TSW:
									case TSG_REAL:
									case TSW_REAL:
										if (!plugin.getMatchActive().getAllowDamage()) {
											set(ev, "setCancelled", true);
										} else {
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%killer%", damager.getName()),
													false);
											UtilsRandomEvents.doCommandsKill(damager,plugin);
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
											plugin.getMatchActive().hazComandosDeMuerte(damager,player);
											plugin.getMatchActive().echaDePartida(player, true, true, false, true,
													true,false);
											player.setHealth(player.getMaxHealth());

										}
										break;
									case BATTLE_ROYALE:
									case BATTLE_ROYALE_CABALLO:
									case BATTLE_ROYALE_TEAM_2:
									case BATTLE_ROYALE_TEAMS:
									case HIDE_AND_SEEK:
									case PAINTBALL:
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.doCommandsKill(damager,plugin);
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
										plugin.getMatchActive().hazComandosDeMuerte(damager,player);
										plugin.getMatchActive().echaDePartida(player, true, true, false);
										player.setHealth(player.getMaxHealth());

										break;
									case GLASS_WALK:
									case KNOCKBACK_DUEL:
									case ESCAPE_ARROW:
									case BLOCK_PARTY:

										set(ev, "setDamage", 0);
										break;
									case ANVIL_SPLEEF:
									case BOMBARDMENT:
									case TNT_RUN:
									case WDROP:
									case SPLEEF:
									case SPLEGG:
										set(ev, "setCancelled", true);
										break;
									case OITC:
										plugin.getMatchActive().reiniciaPlayer(player);
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
										if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(),
													plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

										} else {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
										}
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.doCommandsKill(damager,plugin);
										damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
												+ plugin.getLanguage().getNowPoints().replace("%points%",
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																.toString()));
										damager.getInventory().addItem(XMaterial.ARROW.parseItem());
										if (plugin.getReventConfig().isOitcHealAfterKill()) {
											damager.setHealth(damager.getMaxHealth());
										}
										break;

									case TOP_KILLER:
									case TOP_KILLER_TEAM_2:
									case TOP_KILLER_TEAMS:
										plugin.getMatchActive().reiniciaPlayer(player);
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
										if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(),
													plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

										} else {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
										}
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.doCommandsKill(damager,plugin);
										damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
												+ plugin.getLanguage().getNowPoints().replace("%points%",
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																.toString()));

										if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
											damager.setHealth(damager.getMaxHealth());
										}

										break;
									case KOTH:
										plugin.getMatchActive().reiniciaPlayer(player);
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);

										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.doCommandsKill(damager,plugin);

										break;

									case GEM_CRAWLER:
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.doCommandsKill(damager,plugin);
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
										plugin.getMatchActive().reiniciaPlayer(player);
										break;
									case BOMB_TAG:
										set(ev, "setDamage", 0);
										if (damager.equals(
												plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
											UtilsRandomEvents.borraInventario(damager, plugin);
											UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_HURT);
											plugin.getMatchActive().ponInventarioMatch(player,false);
											plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);

											if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
												if (player.hasPotionEffect(PotionEffectType.SPEED)) {
													player.removePotionEffect(PotionEffectType.SPEED);
												}
												player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getReventConfig().getSpeedDuration(),
														plugin.getReventConfig().getTntTagSpeedHolder() - 1));
											}
											if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
												if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
													damager.removePotionEffect(PotionEffectType.SPEED);
												}
												damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getReventConfig().getSpeedDuration(),
														plugin.getReventConfig().getTntTagSpeedRunners() - 1));
											}

										}
										break;
									case BOAT_RUN:
									case HORSE_RUN:
									case RACE:
									case RED_GREEN_LIGHT:
										set(ev, "setCancelled", true);
										break;
									case ESCAPE_FROM_BEAST:
										if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
											set(ev, "setCancelled", true);

										} else {
											if (damager == null
													|| damager.getName()
															.equals(plugin.getMatchActive().getPlayerHandler()
																	.getBeast().getName())
													|| player.getName().equals(plugin.getMatchActive()
															.getPlayerHandler().getBeast().getName())) {
												if (player.getName().equals(plugin.getMatchActive().getPlayerHandler()
														.getBeast().getName())) {
													plugin.getMatchActive().getPlayerHandler()
															.setPlayerContador(damager);
												}
												UtilsRandomEvents.mandaMensaje(plugin,
														plugin.getMatchActive().getPlayerHandler()
																.getPlayersSpectators(),
														plugin.getLanguage().getPvpKill()
																.replaceAll("%victim%", player.getName())
																.replaceAll("%killer%", damager.getName()),
														false);
												UtilsRandomEvents.doCommandsKill(damager,plugin);
												UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
												UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
												plugin.getMatchActive().hazComandosDeMuerte(damager,player);
												plugin.getMatchActive().echaDePartida(player, true, true, false);
												player.setHealth(player.getMaxHealth());

											} else {
												set(ev, "setCancelled", true);
											}
										}

										break;
									case QUAKECRAFT:
									case HOEHOEHOE:
									case SPLATOON:
										set(ev, "setCancelled", true);
										break;
									default:
										break;
									}
								} else {
									switch (plugin.getMatchActive().getMatch().getMinigame()) {
									case SG:
									case SW:
									case TSG:
									case TSW:
									case TSG_REAL:
									case TSW_REAL:
										if (!plugin.getMatchActive().getAllowDamage()) {
											set(ev, "setCancelled", true);
										} else {
											if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
												set(ev, "setCancelled", false);
											}
										}
										break;
									case BATTLE_ROYALE:
									case BATTLE_ROYALE_CABALLO:
									case BATTLE_ROYALE_TEAM_2:
									case BATTLE_ROYALE_TEAMS:
									case HIDE_AND_SEEK:
									case PAINTBALL:
									case TOP_KILLER:
									case TOP_KILLER_TEAM_2:
									case TOP_KILLER_TEAMS:
									case OITC:
									case KOTH:
										if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
											set(ev, "setCancelled", false);
										}
										break;
									case ESCAPE_FROM_BEAST:
										if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
											set(ev, "setCancelled", true);

										} else {

											if (damager == null
													|| damager.getName()
															.equals(plugin.getMatchActive().getPlayerHandler()
																	.getBeast().getName())
													|| player.getName().equals(plugin.getMatchActive()
															.getPlayerHandler().getBeast().getName())) {
												if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
													set(ev, "setCancelled", false);
												}

											} else {
												set(ev, "setCancelled", true);
											}
										}

										break;
									case GLASS_WALK:
									case KNOCKBACK_DUEL:
									case ESCAPE_ARROW:
									case BLOCK_PARTY:
										set(ev, "setDamage", 0);
										if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
											set(ev, "setCancelled", false);
										}
										break;
									case ANVIL_SPLEEF:
									case BOMBARDMENT:
									case TNT_RUN:
									case WDROP:
									case SPLEEF:
									case SPLEGG:
										set(ev, "setCancelled", true);
										break;
									case BOMB_TAG:
										set(ev, "setDamage", 0);
										if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
											set(ev, "setCancelled", false);
										}
										if (damager.equals(
												plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
											UtilsRandomEvents.borraInventario(damager, plugin);
											UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_HURT);
											plugin.getMatchActive().ponInventarioMatch(player,false);
											plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);
											if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
												if (player.hasPotionEffect(PotionEffectType.SPEED)) {
													player.removePotionEffect(PotionEffectType.SPEED);
												}
												player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getReventConfig().getSpeedDuration(),
														plugin.getReventConfig().getTntTagSpeedHolder() - 1));
											}
											if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
												if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
													damager.removePotionEffect(PotionEffectType.SPEED);
												}
												damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getReventConfig().getSpeedDuration(),
														plugin.getReventConfig().getTntTagSpeedRunners() - 1));
											}

										}
										break;
									case BOAT_RUN:
									case HORSE_RUN:
									case RACE:
									case RED_GREEN_LIGHT:
										set(ev, "setCancelled", true);
										break;
									case QUAKECRAFT:
									case HOEHOEHOE:
									case SPLATOON:
										set(ev, "setCancelled", true);
										break;

									default:
										if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
											set(ev, "setCancelled", false);
										}
										break;
									}
								}
							} else {
								if (plugin.getMatchActive().getPlayerHandler().getPlayersSpectators()
										.contains(damager)) {
									set(ev, "setCancelled", true);
								}
							}
						}

					} else {

						if (((player.getHealth() - number(ev, "getDamage")) <= 0)) {

							set(ev, "setCancelled", true);
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case SG:
							case SW:
							case TSG:
							case TSW:
							case TSG_REAL:
							case TSW_REAL:
								if (!plugin.getMatchActive().getAllowDamage()) {
									set(ev, "setCancelled", true);
								} else {
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);

									UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
									plugin.getMatchActive().hazComandosDeMuerte(null,player);
									plugin.getMatchActive().echaDePartida(player, true, true, false, true, true,false);
									player.setHealth(player.getMaxHealth());
								}

								break;
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
							case BATTLE_ROYALE_TEAMS:
							case HIDE_AND_SEEK:
							case PAINTBALL:
							case ESCAPE_ARROW:
							case ANVIL_SPLEEF:
							case BOMBARDMENT:

								UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
										false);
								plugin.getMatchActive().hazComandosDeMuerte(null,player);
								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(player.getMaxHealth());

								break;
							case ESCAPE_FROM_BEAST:

								UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
										false);
								plugin.getMatchActive().hazComandosDeMuerte(null,player);
								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(player.getMaxHealth());
								break;
							case GLASS_WALK:
							case KNOCKBACK_DUEL:
							case BOMB_TAG:
							case BLOCK_PARTY:
								set(ev, "setDamage", 0);
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									set(ev, "setCancelled", false);
								}
								break;
							case TNT_RUN:
							case WDROP:
							case SPLEEF:
							case SPLEGG:
								set(ev, "setCancelled", true);
								break;
							case OITC:
								if (get(ev, "getDamager") instanceof Arrow) {
									Arrow arrow = (Arrow) get(ev, "getDamager");

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player p = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers()
												.contains(p.getName()) && !player.getName().equals(p.getName())) {
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
											Location pLoc = p.getLocation();
											Location playerLoc = player.getLocation();
											pLoc.setWorld(playerLoc.getWorld());
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getBowKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%distance%",
																	"" + Double.valueOf(pLoc.distance(playerLoc))
																			.intValue())
															.replaceAll("%killer%", p.getName()),
													false);
											UtilsRandomEvents.doCommandsKill(p,plugin);
											UtilsRandomEvents.playSound(plugin,p, XSound.ENTITY_PLAYER_LEVELUP);
											plugin.getMatchActive().reiniciaPlayer(player);
											if (plugin.getMatchActive().getPuntuacion().containsKey(p.getName())) {
												plugin.getMatchActive().getPuntuacion().put(p.getName(),
														plugin.getMatchActive().getPuntuacion().get(p.getName()) + 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(p.getName(), 1);
											}

											p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion().get(p.getName())
																	.toString()));
											p.getInventory().addItem(XMaterial.ARROW.parseItem());
											if (plugin.getReventConfig().isOitcHealAfterKill()) {
												p.setHealth(p.getMaxHealth());
											}
										}
									} else {
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false);
									}
									plugin.getMatchActive().reiniciaPlayer(player);
								} else {
									UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
								}
								plugin.getMatchActive().reiniciaPlayer(player);

								break;
							case TOP_KILLER:
							case TOP_KILLER_TEAM_2:
							case TOP_KILLER_TEAMS:

								if (get(ev, "getDamager") instanceof Arrow) {
									Arrow arrow = (Arrow) get(ev, "getDamager");

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player damager = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(
												damager.getName()) && !player.getName().equals(damager.getName())) {
											plugin.getMatchActive().reiniciaPlayer(player);
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);

											UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);
											if (plugin.getMatchActive().getPuntuacion()
													.containsKey(damager.getName())) {
												plugin.getMatchActive().getPuntuacion().put(damager.getName(),
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																+ 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
											}
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%killer%", damager.getName()),
													false);
											UtilsRandomEvents.doCommandsKill(damager,plugin);
											damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion()
																	.get(damager.getName()).toString()));
											if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
												damager.setHealth(damager.getMaxHealth());
											}
										} else {
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
															player.getName()),
													false);
											plugin.getMatchActive().reiniciaPlayer(player);
										}
									} else {
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false);
										plugin.getMatchActive().reiniciaPlayer(player);
									}
								} else {
									UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
									plugin.getMatchActive().reiniciaPlayer(player);

								}
								break;
							case KOTH:

								if (get(ev, "getDamager") instanceof Arrow) {
									Arrow arrow = (Arrow) get(ev, "getDamager");

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player damager = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(
												damager.getName()) && !player.getName().equals(damager.getName())) {
											plugin.getMatchActive().reiniciaPlayer(player);
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);

											UtilsRandomEvents.playSound(plugin,damager, XSound.ENTITY_PLAYER_LEVELUP);

											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%killer%", damager.getName()),
													false);
											UtilsRandomEvents.doCommandsKill(damager,plugin);

										} else {
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
															player.getName()),
													false);
											plugin.getMatchActive().reiniciaPlayer(player);
										}
									} else {
										UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false);
										plugin.getMatchActive().reiniciaPlayer(player);
									}
								} else {
									UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
									plugin.getMatchActive().reiniciaPlayer(player);

								}
								break;
							case GEM_CRAWLER:
								UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
										false);
								plugin.getMatchActive().reiniciaPlayer(player);
								break;
							case BOAT_RUN:
							case HORSE_RUN:
							case RACE:
							case RED_GREEN_LIGHT:
								set(ev, "setCancelled", true);
								break;
							case HOEHOEHOE:
							case SPLATOON:
							case QUAKECRAFT:
								set(ev, "setCancelled", true);
								break;
							default:
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									set(ev, "setCancelled", false);
								}
								break;
							}

						} else {
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case OITC:
								if (get(ev, "getDamager") instanceof Arrow) {
									Arrow arrow = (Arrow) get(ev, "getDamager");

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player p = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers()
												.contains(p.getName()) && !player.getName().equals(p.getName())) {

											Location pLoc = p.getLocation();
											Location playerLoc = player.getLocation();
											pLoc.setWorld(playerLoc.getWorld());
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getBowKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%distance%",
																	"" + Double.valueOf(pLoc.distance(playerLoc))
																			.intValue())
															.replaceAll("%killer%", p.getName()),
													false);
											UtilsRandomEvents.doCommandsKill(p,plugin);
											plugin.getMatchActive().reiniciaPlayer(player);
											UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.playSound(plugin,p, XSound.ENTITY_PLAYER_LEVELUP);
											if (plugin.getMatchActive().getPuntuacion().containsKey(p.getName())) {
												plugin.getMatchActive().getPuntuacion().put(p.getName(),
														plugin.getMatchActive().getPuntuacion().get(p.getName()) + 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(p.getName(), 1);
											}

											p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion().get(p.getName())
																	.toString()));
											p.getInventory().addItem(XMaterial.ARROW.parseItem());
											if (plugin.getReventConfig().isOitcHealAfterKill()) {
												p.setHealth(p.getMaxHealth());
											}
										}
									} else {
										if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
											set(ev, "setCancelled", false);
										}
									}
								} else {
									if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
										set(ev, "setCancelled", false);
									}
								}

								break;
							case SG:
							case SW:
							case TSG:
							case TSW:
							case TSG_REAL:
							case TSW_REAL:
								if (!plugin.getMatchActive().getAllowDamage()) {
									set(ev, "setCancelled", true);
								} else {
									if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
										set(ev, "setCancelled", false);
									}
								}
								break;
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
							case BATTLE_ROYALE_TEAMS:
							case HIDE_AND_SEEK:
							case PAINTBALL:
							case TOP_KILLER:
							case TOP_KILLER_TEAM_2:
							case TOP_KILLER_TEAMS:
							case ANVIL_SPLEEF:
							case BOMBARDMENT:
							case ESCAPE_FROM_BEAST:
							case KOTH:
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									set(ev, "setCancelled", false);
								}
								break;

							case GLASS_WALK:
							case KNOCKBACK_DUEL:
							case BOMB_TAG:
							case BLOCK_PARTY:
								set(ev, "setDamage", 0);
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									set(ev, "setCancelled", false);
								}
								break;
							case TNT_RUN:
							case WDROP:
							case SPLEEF:
							case SPLEGG:
							case BOAT_RUN:
							case HORSE_RUN:
							case RACE:
							case RED_GREEN_LIGHT:
								set(ev, "setCancelled", true);
								break;

							case ESCAPE_ARROW:
								if (((player.getHealth() - plugin.getReventConfig().getArrowRainDamage()) <= 0)) {

									UtilsRandomEvents.playSound(plugin,player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
									plugin.getMatchActive().hazComandosDeMuerte(null,player);
									plugin.getMatchActive().echaDePartida(player, true, true, false);
									player.setHealth(player.getMaxHealth());
								} else {
									player.damage(plugin.getReventConfig().getArrowRainDamage());
									if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
										set(ev, "setCancelled", false);
									}
								}
								break;
							case HOEHOEHOE:
							case SPLATOON:
							case QUAKECRAFT:
								set(ev, "setCancelled", true);
								break;
							default:
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									set(ev, "setCancelled", false);
								}
								break;
							}

						}
					}
				}
			} else if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
				set(ev, "setCancelled", true);
			}
		} else if (get(ev, "getVictim") instanceof Horse && get(ev, "getPlayer") instanceof Player)

		{
			Player player = (Player) get(ev, "getPlayer");
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
				if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
						.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
					set(ev, "setDamage", 0);
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						set(ev, "setCancelled", false);
					}
				}
			}
		} else if (get(ev, "getVictim") instanceof Boat) {
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getMobs().contains(get(ev, "getVictim"))) {
				set(ev, "setCancelled", true);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void register(RandomEvents plugin) {
		try {
			Class<?> raw = Class.forName("com.shampaggon.crackshot.events.WeaponDamageEntityEvent");
			if (!Event.class.isAssignableFrom(raw)) throw new IllegalStateException("CrackShot event type is invalid");
			WeaponShoot listener = new WeaponShoot(plugin);
			EventExecutor executor = (ignored, event) -> listener.weaponDamage(event);
			plugin.getServer().getPluginManager().registerEvent((Class<? extends Event>) raw, listener,
					EventPriority.MONITOR, executor, plugin);
			plugin.getLogger().info("[RandomEvents] CrackShot hook registered.");
		} catch (ClassNotFoundException e) {
			plugin.getLogger().warning("CrackShot is present but its damage event API could not be found; weapon integration is disabled.");
		}
	}

	private static Object get(Object event, String name) {
		try { return event.getClass().getMethod(name).invoke(event); }
		catch (ReflectiveOperationException e) { throw new IllegalStateException("CrackShot event missing " + name, e); }
	}

	private static double number(Object event, String name) {
		Object value = get(event, name);
		return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
	}

	private static void set(Object event, String name, Object value) {
		try {
			for (Method method : event.getClass().getMethods()) {
				if (!method.getName().equals(name) || method.getParameterCount() != 1) continue;
				Class<?> parameter = method.getParameterTypes()[0];
				Object argument = value;
				if (value instanceof Number && (parameter == double.class || parameter == Double.class)) argument = ((Number) value).doubleValue();
				else if (value instanceof Number && (parameter == float.class || parameter == Float.class)) argument = ((Number) value).floatValue();
				else if (value instanceof Boolean && (parameter == boolean.class || parameter == Boolean.class)) argument = value;
				else if (!parameter.isInstance(value)) continue;
				method.invoke(event, argument);
				return;
			}
			throw new NoSuchMethodException(name);
		} catch (ReflectiveOperationException e) { throw new IllegalStateException("CrackShot event missing " + name, e); }
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

}
