// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// much of the code is adapted from the code on Limelight Docs
//https://docs.limelightvision.io/en/latest/ 

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/** Add your docs here. */
public class Limelight {
    // TO DO change the height to actual retroreflective tape height
    double limelightMountA = 27.55;
    double limelightLensH = 85;
    
    //double goalH = 264.16; //actual goal height
    double goalH = 261.5;
    //double goalH = 162; // human height

    NetworkTable nt;

    public Limelight() {
        nt = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double getTX() {
        /* tx is horizontal offset from crosshair to target(Limelight Docs) */
        NetworkTableEntry tx = nt.getEntry("tx");
        double x = tx.getDouble(0.0);
        return x;
    }

    public double getTY() {
        // vertical offset from crosshair to target
        NetworkTableEntry ty = nt.getEntry("ty");
        double y = ty.getDouble(0.0);
        return y;

    }

    public double getTA() {
        // percentage of screen that the target fills
        NetworkTableEntry ta = nt.getEntry("ta");
        double a = ta.getDouble(0.0);
        return a;
    }
    /*public double getTL(){
        //latency
        NetworkTableEntry tl = nt.getEntry("tl");
        double l = tl.getDouble(0.0);
        return l;
    }*/

    /*
     * public double getTV(){
     * 
     * NetworkTableEntry tv = nt.getEntry("tv");
     * 
     * double v = tv.getDouble(0.0);
     * 
     * return v;
     * 
     * }
     */ // TV does not work
    public double calcDistance() {
        double angleToGoalRad = (limelightMountA + getTY()) * (Math.PI / 180.0);
        return (goalH - limelightLensH) / Math.tan(angleToGoalRad);
    }

}
