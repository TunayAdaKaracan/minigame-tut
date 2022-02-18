package com.articfoxdevelopment.minigame.instances;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.kit.Kit;
import com.articfoxdevelopment.minigame.kit.KitType;
import com.articfoxdevelopment.minigame.kit.type.FighterKit;
import com.articfoxdevelopment.minigame.kit.type.MinerKit;
import com.articfoxdevelopment.minigame.managers.ConfigManager;
import com.articfoxdevelopment.minigame.teams.Team;
import com.google.common.collect.TreeMultimap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private HashMap<UUID, Kit> kits;
    private HashMap<UUID, Team> teams;

    private Countdown countdown;
    private Minigame main;
    private Game game;

    public Arena(Minigame main, int id, Location spawn){
        this.main = main;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.kits = new HashMap<>();
        this.teams = new HashMap<>();

        this.countdown = new Countdown(main, this);
        this.game = new Game(this);
    }

    /*
        GET INFO
     */

    public int getId(){return id;}

    public List<UUID> getPlayers(){return players;}

    public GameState getState(){return state;}

    public Game getGame(){return game;}

    public HashMap<UUID, Kit> getKits(){return kits;}

    public HashMap<UUID, Team> getTeams(){return teams;}

    /*
        SET INFO
     */

    public void setState(GameState state){this.state = state;}

    /*
        PLAYER MANAGEMENT
     */

    public void addPlayer(Player p){
        players.add(p.getUniqueId());
        p.teleport(spawn);

        TreeMultimap<Integer, Team> count = TreeMultimap.create();

        for(Team t: Team.values()){
            count.put(getTeamCount(t), t);
        }

        Team lowest = (Team) count.values().toArray()[0];
        setTeam(p, lowest);

        p.sendMessage(ChatColor.AQUA + "You have been automaticly added to " + lowest.getDisplay() + " team");

        p.sendMessage(ChatColor.GOLD + "Choose your kit with /arena kit");

        if(state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player p){
        players.remove(p.getUniqueId());
        p.teleport(ConfigManager.getLobbySpawn());
        p.sendTitle(" ", " ");
        removeKit(p.getUniqueId());
        removeTeam(p);

        if(state.equals(GameState.COUNTDOWN) && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "There is not enough players. Cancelling Countdown.");
            reset(false);
        }

        if(state.equals(GameState.LIVE) && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "Too many players left. Ending Game.");
            reset(true);
        }

    }

    /*
        TOOLS
     */

    public void sendMessage(String message){
        for(UUID target : players){
            Bukkit.getPlayer(target).sendMessage(message);
        }
    }

    public void sendTitle(String message, String submessage){
        for(UUID target : players){
            Bukkit.getPlayer(target).sendTitle(message, submessage);
        }
    }

    /*
        GAME
     */

    public void start(){
        game.start();
    }

    public void reset(boolean kickPlayers){
        if(kickPlayers){
            Location lobby = ConfigManager.getLobbySpawn();
            for(UUID target : players){
                Player player = Bukkit.getPlayer(target);
                player.teleport(lobby);
                removeKit(player.getUniqueId());
            }
            players.clear();
            kits.clear();
            teams.clear();
        }
        sendTitle(" ", " ");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(this.main, this);
        game = new Game(this);
    }

    public void removeKit(UUID uuid){
        if(kits.containsKey(uuid)){
            kits.get(uuid).remove();
            kits.remove(uuid);
        }
    }

    public void setKit(UUID uuid, KitType type){
        removeKit(uuid);
        if(type.equals(KitType.FIGHTER)){
            kits.put(uuid, new FighterKit(this.main, uuid));
        } else if(type.equals(KitType.MINER)){
            kits.put(uuid, new MinerKit(this.main, uuid));
        }

    }

    public KitType getKitType(Player player){
        return kits.containsKey(player.getUniqueId()) ? kits.get(player.getUniqueId()).getType() : null;
    }

    public void setTeam(Player p, Team team){
        removeTeam(p);
        teams.put(p.getUniqueId(), team);
    }

    public void removeTeam(Player p){
        if(teams.containsKey(p.getUniqueId())){
            teams.remove(p.getUniqueId());
        }
    }

    public int getTeamCount(Team team){
        int amount = 0;
        for(Team t : teams.values()){
            if(t.equals(team)){
                amount++;
            }
        }
        return amount;
    }

    public Team getTeam(Player p){
        return teams.get(p.getUniqueId());
    }

}
