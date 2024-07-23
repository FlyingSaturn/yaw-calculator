package com.flyingsaturn.yawcalc;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;    
import org.bukkit.plugin.java.JavaPlugin;

/*
 * yawcalc java plugin
 */
public class YawCalculator extends JavaPlugin
{
    private static final Logger LOGGER=Logger.getLogger("yawcalc");

    @Override
    public void onEnable() {
        getLogger().info("YawCalculator has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("YawCalculator has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
        if (command.getName().equalsIgnoreCase("calcyaw")) 
        {
            if (args.length != 4) {
                sender.sendMessage("Usage: /calcyaw <CurrentX> <CurrentZ> <DestX> <DestZ>");
                return false;
            }

            try {
                int x_current = Integer.parseInt(args[0]);
                int z_current = Integer.parseInt(args[1]);
                int x_dest = Integer.parseInt(args[2]);
                int z_dest = Integer.parseInt(args[3]);
                String yaw = calculateyaw(x_current, z_current, x_dest, z_dest);
                sender.sendMessage("The yaw angle is " + yaw + " :)");
                return true;

            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid number format. Please enter integer values.");
                return false;
            }
        }

        return false;
    }

    private String calculateyaw(int xCurrent, int zCurrent, int xDest, int zDest) // Values of z are already negated
    {
        // Both zCurrent and zDest are to be negated before calculating the slope,
        // so written zCurrent - zDest instead of zDest - zCurrent
        // atan2 is for finding the angle of a line in radians, which is converted to degrees
        double slope = Math.atan2(zCurrent - zDest, xDest - xCurrent) * (180 / Math.PI);
        slope += 90; // Rotating the whole Minecraft yaw table 90 degrees anticlockwise
        slope *= -1; // On rotation, we get negative values in the positive quadrants of 'y' (or -Z axis)
        if (slope < -180)
            slope = -360 - slope; // -180 + (-180 - slope)
        slope = Math.round(slope * 10.0) / 10.0; // To get the angles correct to 1 d.p. for discrepancies between radians and degrees
        if (slope == 0)
            return "-0.0";
        else if (slope == -180)
            return "180.0";
        else
            return Double.toString(slope);
    }
}

