package me.conehead.expbottles;

import org.bukkit.entity.Player;

public class ExpEngine {

    public static void removeExpFromPlayer(Player player, int amount) {
        player.setTotalExperience(player.getTotalExperience() - amount);
        updatePlayerLevel(player);
    }

    private static int getExpToNextLevel(int currentLevel) {
        if (currentLevel < 16) return 17;

        return (currentLevel - 15) * 3 + 17;
    }

    private static void updatePlayerLevel(Player player) {
        int currentExperience = player.getTotalExperience();
        player.setLevel(0);
        while (currentExperience - getExpToNextLevel(player.getLevel()) > 0) {
            currentExperience -= getExpToNextLevel(player.getLevel());
            player.setLevel(player.getLevel() + 1);
        }

        float levelUpPercent = (float) currentExperience / getExpToNextLevel(player.getLevel());
        player.setExp(levelUpPercent);
    }
}
