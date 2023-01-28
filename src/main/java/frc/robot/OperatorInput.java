
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.Commands.LimelightSafeAim;
// import frc.robot.Commands.LimelightFollowCommand;
//import frc.robot.Commands.LimelightSafeAim;
import frc.robot.Commands.Shoot;

public class OperatorInput {
    XboxController movementController;
    XboxController shooterController;
    

    Button limeB;
    Button shootB;

    public OperatorInput() {
        movementController = new XboxController(0);
        shooterController = new XboxController(2);

        // LimelightFollowCommand lc = new LimelightFollowCommand(); //DO NOT USE //IF YOU USE IT, YOU MIGHT BREAK THE INTAKE

        limeB = new Button(shooterController::getAButton);
        LimelightSafeAim lc = new LimelightSafeAim();
        limeB.toggleWhenPressed(lc);

        shootB = new Button(shooterController::getStartButton);
        shootB.toggleWhenPressed(new Shoot());

    }

   

    public boolean getRightBumper() {
        //rotate turret
        return movementController.getRightBumper();
    }
    public boolean getShootMax(){
        //right bumper shooter shoot max speed
        return shooterController.getYButton();
    }

    public boolean getLeftBumper() {
        //rotate turret
        return movementController.getLeftBumper();
    }

    public boolean getBButton() {
        return shooterController.getBButton();
        //run the intake motors
    }

    public boolean getXButton() {
        //rev the shooter motor
        return shooterController.getXButton();    
    }

    

    public double getSpeed() {
        // move forward
        return movementController.getLeftY(); 
    } 

    public double getTurn() {
        return movementController.getRightX();
        // turning
    }

    public boolean magazineForward() {
        return shooterController.getRightTriggerAxis() > 0.3;
        // down
    }

    public boolean reverseMagazine() {
        // magazine up
        return shooterController.getLeftTriggerAxis() > 0.3;
    }

    public boolean getUp() {
        // make intake pistons go up
        return movementController.getXButton();
    }

    public boolean switchMode(){
        return movementController.getStartButtonPressed();
    }

    public boolean getDown() {
        // drop the intake pistons
        return movementController.getBButton();
    }

    public boolean getBackButton() {
        return movementController.getBackButton();
    }

    public boolean rescheduleCommands() {
        return (movementController.getRightStickButton() && movementController.getLeftStickButton());
    }

    public boolean toggleClimb(){
        // toggle pistons
        return movementController.getYButton();
    }
    public boolean pullWinch(){

        return movementController.getRightTriggerAxis() > 0.3;
    }
    public boolean winchSlow() {
        return movementController.getLeftTriggerAxis() > 0.3;
    }
    public boolean unwinch() {
        return movementController.getAButton();
    }

}
