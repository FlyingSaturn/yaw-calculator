import java.util.*;
class Prototype
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the X and Z coordinates of your current location: ");
        int x1 = sc.nextInt();
        int z1 = -(sc.nextInt());
        System.out.print("Enter the X and Z coordinates of your destination: ");
        int x2 = sc.nextInt();
        int z2 = -(sc.nextInt());  
	String yaw = calculateyaw(x1, z1, x2, z2);
        System.out.println("The yaw angle is " + yaw + " :)");          
    }
	
    // the exact same function
    private String calculateyaw(int xcurrent, int zcurrent, int xdest, int zdest)
    {
        // Both zcurrent and zdest are to be negated before calculating the slope,
        // so written zcurrent - zdest instead of zdest - zcurrent
        // atan2 is for finding the angle of a line in radians, which is converted to degrees
        double slope = Math.atan2(zcurrent - zdest, xdest - xcurrent) * (180 / Math.PI);
        slope = Math.round(slope * 10.0) / 10.0; // To get the angles correct to 1 d.p. for discrepancies between radians and degrees
        slope += 90; // Rotating the whole minecraft yaw table 90 degrees anticlockwise
        slope *= -1; // On rotation, we get negative values in the positive quadrants of 'y' (or -Z axis)
        if (slope == 0)
            return "-0.0";
        else if (slope == -180)
            return "180.0";
        else
            return Double.toString(slope);
    }
}