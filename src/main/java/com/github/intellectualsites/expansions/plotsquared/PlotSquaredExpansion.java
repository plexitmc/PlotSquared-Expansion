package com.github.intellectualsites.expansions.plotsquared;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public final class PlotSquaredExpansion extends PlaceholderExpansion {
    private PlotSquaredApiInterface api;
    
    public PlotSquaredExpansion() {
        this.api = null;
    }
    
    @NotNull
    @Override
    public String getAuthor() {
        return "IronApollo, SirBlobman";
    }
    
    @NotNull
    @Override
    public String getIdentifier() {
        return "plotsquared";
    }
    
    @NotNull
    @Override
    public String getRequiredPlugin() {
        return "PlotSquared";
    }
    
    @NotNull
    @Override
    public String getVersion() {
        return "3.0";
    }
    
    @Override
    public boolean canRegister() {
        this.api = determineApi();
        return (this.api != null);
    }
    
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        return this.api.onPlaceHolderRequest(player, placeholder);
    }
    
    private PlotSquaredApiInterface determineApi() {
        try {
            return new PlotSquaredApiNew();
        } catch(NoClassDefFoundError e) {
            try {
                return new PlotSquaredApiOld();
            } catch(NoClassDefFoundError e1) {
                return null;
            }
        }
    }
}
