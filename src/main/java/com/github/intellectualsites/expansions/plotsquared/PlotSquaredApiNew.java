package com.github.intellectualsites.expansions.plotsquared;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.uuid.UUIDPipeline;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public final class PlotSquaredApiNew implements PlotSquaredApiInterface {
    private final PlotAPI api;
    private final UUIDPipeline pipeline;
    
    public PlotSquaredApiNew() {
        this.api = new PlotAPI();
        this.pipeline = this.api.getPlotSquared().getBackgroundUUIDPipeline();
    }
    
    @Override
    public String onPlaceHolderRequest(Player player, String placeholder) {
        if(player == null) {
            return "";
        }

        PlotPlayer<?> plotPlayer = api.wrapPlayer(player.getUniqueId());
        if(plotPlayer == null) {
            return "";
        }
        
        Plot currentPlot = plotPlayer.getCurrentPlot();
        if(placeholder.startsWith("has_plot_")) {
            if(placeholder.split("has_plot_").length != 2) {
                return null;
            }
            
            placeholder = placeholder.split("has_plot_")[1];
            return plotPlayer.getPlotCount(placeholder) > 0 ? PlaceholderAPIPlugin.booleanTrue()
                    : PlaceholderAPIPlugin.booleanFalse();
        }
        
        if(placeholder.startsWith("plot_count_")) {
            if(placeholder.split("plot_count_").length != 2) {
                return null;
            }
            
            placeholder = placeholder.split("plot_count_")[1];
            return String.valueOf(plotPlayer.getPlotCount(placeholder));
        }

        switch (placeholder) {
            case "is_on_plot" -> {
                return String.valueOf(plotPlayer.getCurrentPlot() != null);
            }
            case "currentplot_alias" -> {
                return (plotPlayer.getCurrentPlot() != null) ? plotPlayer.getCurrentPlot().getAlias() : "-";
            }
            case "currentplot_owner" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                Set<UUID> plotOwners = plotPlayer.getCurrentPlot().getOwners();
                if (plotOwners.isEmpty()) {
                    return "-";
                }

                UUID firstOwnerId = (UUID) plotOwners.toArray()[0];
                if (firstOwnerId == null) {
                    return "-";
                }

                String ownerName = pipeline.getSingle(firstOwnerId, 1000);
                return (ownerName != null) ? ownerName
                        : ((Bukkit.getOfflinePlayer(firstOwnerId) != null) ?
                        Bukkit.getOfflinePlayer(firstOwnerId).getName() : "ukendt");
            }
            case "currentplot_world" -> {
                return player.getWorld().getName();
            }
            case "has_plot" -> {
                return (plotPlayer.getPlotCount() > 0) ? "&a&l\u2714" : "&c&l\u2718";
            }
            case "allowed_plot_count" -> {
                return String.valueOf(plotPlayer.getAllowedPlots());
            }
            case "plot_count" -> {
                return String.valueOf(plotPlayer.getPlotCount());
            }
            case "currentplot_members" -> {
                if (plotPlayer.getCurrentPlot() == null)
                    return "-";
                return String.valueOf(plotPlayer.getCurrentPlot().getMembers().size()
                        + plotPlayer.getCurrentPlot().getTrusted().size());
            }
            case "currentplot_members_added" -> {
                if (plotPlayer.getCurrentPlot() == null)
                    return "-";
                return String.valueOf(plotPlayer.getCurrentPlot().getMembers().size());
            }
            case "currentplot_members_trusted" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                return String.valueOf(currentPlot.getTrusted().size());
            }
            case "currentplot_members_denied" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                return String.valueOf(plotPlayer.getCurrentPlot().getDenied().size());
            }
            case "has_build_rights" -> {
                return (plotPlayer.getCurrentPlot() != null) ?
                        ((plotPlayer.getCurrentPlot().isAdded(plotPlayer.getUUID())) ? "&a&l\u2714" : "&c&l\u2718") : "&c&l\u2718";
            }
            case "currentplot_x" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                return String.valueOf(currentPlot.getId().getX());
            }
            case "currentplot_y" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                return String.valueOf(currentPlot.getId().getY());
            }
            case "currentplot_xy" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                return plotPlayer.getCurrentPlot().getId().getX() + ";" + plotPlayer.getCurrentPlot().getId().getY();
            }
            case "currentplot_rating" -> {
                if (plotPlayer.getCurrentPlot() == null) {
                    return "-";
                }

                return String.valueOf(currentPlot.getAverageRating());
            }
            default -> {
            }
        }
        
        return null;
    }
}
