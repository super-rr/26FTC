package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public final class Robot {
    /// Variables
    private final LinearOpMode opMode;
    private boolean initialized = false;

    /// Motors
    public DcMotorEx IntakeMotor;
    public DcMotorEx Flywheel;
    public DcMotorEx LeftDrive;
    public DcMotorEx RightDrive;
    /// Servos
    public Servo PollenServo;
    public CRServo CRServo;

    public Robot(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init() {
        // Safeguard to avoid being run twice.
        if (this.initialized) {
            return;
        }
        this.initialized = true;

        /// Wheels
        // Get Wheel Motors
        this.LeftDrive = this.getMotor("leftDrive");
        this.RightDrive = this.getMotor("rightDrive");
        // Set Direction of Right Wheel Motor to REVERSE
        this.RightDrive.setDirection(DcMotor.Direction.REVERSE);

        /// Intake
        // Get Intake Motor
        this.IntakeMotor = this.getMotor("intakeMotor");

        /// Flywheel
        // Get Flywheel Motor
        this.Flywheel = this.getMotor("flywheel");
        // Set Flywheel Encoder Mode
        this.Flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /// Servos
        // Get Servos
        this.PollenServo = this.getServo("pollenServo");
        this.CRServo = this.getHardware(CRServo.class, "crServo");
    }

    public void update() {
        // Safeguard to make sure we're initialized before running any possible updates.
        if (!this.initialized) {
            return;
        }

        Gamepad gamepad1 = this.opMode.gamepad1;

        /// Arcade Drive
        if ( (this.LeftDrive != null) && (this.RightDrive != null) ) {
            float x = gamepad1.left_stick_x;
            float y = -gamepad1.left_stick_y;
            this.LeftDrive.setPower(y + x);
            this.RightDrive.setPower(y - x);
        } else {
            this.opMode.telemetry.addLine("Failed to use drive motors.");
        }
    }

    /**
     * Function to check if the robot was initialized.
     */
    public boolean isInitialized() {
        return this.initialized;
    }

    /**
     * Helper function to get a servo.
     * @return {@link Servo}
     */
    private Servo getServo(String name) {
        return this.getHardware(Servo.class, name);
    }

    /**
     * Helper function to get a motor.
     * @return {@link DcMotorEx}
     */
    private DcMotorEx getMotor(String name) {
        return this.getHardware(DcMotorEx.class, name);
    }

    /**
     * Helper function to get a particular hardware.
     */
    private <T> T getHardware(Class<? extends T> hardwareClass, String name) {
        if (this.opMode == null) {
            return null;
        }
        return this.opMode.hardwareMap.get(hardwareClass, name);
    }
}
