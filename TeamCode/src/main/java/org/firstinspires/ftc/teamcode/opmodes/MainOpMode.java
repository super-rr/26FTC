package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Robot;

@TeleOp(name = "Main OpMode", group = "TeleOp")
public class MainOpMode extends LinearOpMode {
    private final Robot robot = new Robot(this);
    int targetVelocity = 1160;

    @Override
    public void runOpMode() {
        robot.init();
        waitForStart();
        resetRuntime();

        while (opModeIsActive()) {
            /// Control Intake
            if (gamepad1.right_bumper) {
                robot.IntakeMotor.setPower(1.0);
            } else if (gamepad1.right_trigger_pressed) {
                robot.IntakeMotor.setPower(-1.0);
            } else {
                robot.IntakeMotor.setPower(0.0);
            }

            /// Control Pollen Poker
            if (gamepad1.crossWasPressed()) {
                robot.PollenServo.setPosition(0.0);
            } else if (gamepad1.circleWasPressed()) {
                robot.PollenServo.setPosition(1.0);
            }

            /// Auto Robot Updates (Includes Driving)
            robot.update();

            /// Control Flywheel
            if (gamepad1.triangle) {
                robot.Flywheel.setVelocity(targetVelocity);
            } else if (gamepad1.square) {
                // Reverse Flywheel
                robot.Flywheel.setPower(-0.55);
            } else if (gamepad1.left_bumper) {
                robot.Flywheel.setVelocity(targetVelocity);
                // Auto feed into flywheel if within a set velocity range
                if (robot.Flywheel.getVelocity() >= targetVelocity - 20 && robot.Flywheel.getVelocity() <= targetVelocity + 20) {
                    robot.CRServo.setPower(1);
                }
            } else if (gamepad1.dpad_up) {
                // Manual control for feeder servo
                robot.CRServo.setPower(1.0);
            } else if (gamepad1.dpad_down) {
                // Reverse feeder
                robot.CRServo.setPower(-1.0);
            } else {
                // Turn off servo and motor when gamepad is not active
                robot.Flywheel.setPower(0.0);
                robot.CRServo.setPower(0.0);
            }
        }
    }
}
