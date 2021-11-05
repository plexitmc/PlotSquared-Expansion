package com.github.intellectualsites.expansions.plotsquared;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.UUIDHandler;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

public class PlotSquaredApiOld implements PlotSquaredApiInterface {
    private final PS api;
    
    public PlotSquaredApiOld() {
        this.api = PS.get();
    }

    @Override
    public String onPlaceHolderRequest(Player player, String placeholder) {
        if (this.api == null || player == null) {
            return "";
        }
        
        String playerName = player.getName();
        PlotPlayer plotPlayer = PlotPlayer.get(playerName);
        if (plotPlayer == null) {
            return "";
        }

        if (placeholder.startsWith("has_plot_")) {
            if (placeholder.split("has_plot_").length != 2) {
                return null;
            }

            placeholder = placeholder.split("has_plot_")[1];
            return plotPlayer.getPlotCount(placeholder) > 0 ? PlaceholderAPIPlugin.booleanTrue() :
                    PlaceholderAPIPlugin.booleanFalse();
        }

        if (placeholder.startsWith("plot_count_")) {
            if (placeholder.split("plot_count_").length != 2) {
                return null;
            }

            placeholder = placeholder.split("plot_count_")[1];
            return String.valueOf(plotPlayer.getPlotCount(placeholder));
        }

        switch (placeholder) {
            case "currentplot_alias": {
                return (plotPlayer.getCurrentPlot() != null) ? plotPlayer.getCurrentPlot().getAlias() : "";
            }
            
            case "currentplot_owner": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                Set<UUID> ownerSet = plotPlayer.getCurrentPlot().getOwners();
                if (ownerSet == null || ownerSet.isEmpty()) {
                    return "";
                }
                
                UUID firstOwnerId = (UUID) ownerSet.toArray()[0];
                if (firstOwnerId == null) {
                    return "";
                }
                
                String ownerName = UUIDHandler.getName(firstOwnerId);
                return (ownerName != null) ? ownerName : ((Bukkit.getOfflinePlayer(firstOwnerId) != null) ?
                        Bukkit.getOfflinePlayer(firstOwnerId).getName() : "unknown");
            }
            
            case "currentplot_world": {
                return player.getWorld().getName();
            }
            
            case "has_plot": {
                return (plotPlayer.getPlotCount() > 0) ? PlaceholderAPIPlugin.booleanTrue() :
                        PlaceholderAPIPlugin.booleanFalse();
            }
            
            case "allowed_plot_count": {
                return String.valueOf(plotPlayer.getAllowedPlots());
            }
            
            case "plot_count": {
                return String.valueOf(plotPlayer.getPlotCount());
            }
            
            case "currentplot_members": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                if (plotPlayer.getCurrentPlot().getMembers() == null &&
                        plotPlayer.getCurrentPlot().getTrusted() == null) {
                    return "0";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getMembers().size() +
                        plotPlayer.getCurrentPlot().getTrusted().size());
            }
            
            case "currentplot_members_added": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                if (plotPlayer.getCurrentPlot().getMembers() == null) {
                    return "0";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getMembers().size());
            }
            
            case "currentplot_members_trusted": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                if (plotPlayer.getCurrentPlot().getTrusted() == null) {
                    return "0";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getTrusted().size());
            }
            
            case "currentplot_members_denied": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                if (plotPlayer.getCurrentPlot().getDenied() == null) {
                    return "0";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getDenied().size());
            }
            
            case "has_build_rights": {
                return (plotPlayer.getCurrentPlot() != null) ?
                        ((plotPlayer.getCurrentPlot().isAdded(plotPlayer.getUUID())) ?
                                PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse()) : "";
            }
            
            case "currentplot_x": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getId().x);
            }
            
            case "currentplot_y": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getId().y);
            }
            
            case "currentplot_xy": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                return plotPlayer.getCurrentPlot().getId().x + ";" + plotPlayer.getCurrentPlot().getId().y;
            }
            
            case "currentplot_rating": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getAverageRating());
            }
            
            case "currentplot_biome": {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "";
                }
                
                return String.valueOf(plotPlayer.getCurrentPlot().getBiome());
            }
            
            default: break;
        }
        
        return null;
    }
}
