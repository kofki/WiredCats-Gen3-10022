package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TeleOpTwoPlayers extends LinearOpMode {
    private DcMotor bL;
    private DcMotor bR;
    private DcMotor fR;
    private DcMotor fL;
    private DcMotor lArm;
    private DcMotor rArm;
    private Servo claw;
    private Servo wrist;
    private Servo launcher;

    @Override
    public void runOpMode(){
        // initialize drivetrain motors
        bL = hardwareMap.dcMotor.get("backLeftMotor");
        bR = hardwareMap.dcMotor.get("backRightMotor");
        fR = hardwareMap.dcMotor.get("frontRightMotor");
        fL = hardwareMap.dcMotor.get("frontLeftMotor");

        // initialize arm motors and servo
        lArm = hardwareMap.dcMotor.get("leftArmMotor");
        rArm = hardwareMap.dcMotor.get("rightArmMotor");
        claw = hardwareMap.servo.get("servoClaw");
        wrist = hardwareMap.servo.get("servoWrist");
        launcher = hardwareMap.servo.get("servoLauncher");

        // reverse motors
        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);
        lArm.setDirection(DcMotorSimple.Direction.REVERSE);

        // encoders setup
        lArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // setting positions, mode and behavior
        lArm.setTargetPosition(0);
        rArm.setTargetPosition(0);
        lArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // waits
        waitForStart();

        // stop button
        if (isStopRequested()) return;

        // declare variables
        double yPower;
        double xPower;
        double xRotation;
        boolean slow;
        double armPower;
        boolean servoClose = false;
        boolean servoRotate = false;
        boolean launch = false;

        //loop
        while (opModeIsActive()){
            //joystick
            yPower = -gamepad1.left_stick_y;
            xPower = gamepad1.left_stick_x;
            xRotation = gamepad1.right_stick_x;

            // power apply to the arm
            // left Trigger down || right trigger up
            armPower = gamepad2.right_trigger - gamepad2.left_trigger;

            // slow power of driving pressing bumper
            slow = gamepad1.left_bumper;

            // make servo wrist rotate
            if (gamepad2.a){
                servoRotate = true;
            } else if (gamepad2.b){
                servoRotate = false;
            }
            // make servo claw close or open
            if (gamepad2.right_bumper) {
                servoClose = true;
            } else if (gamepad2.left_bumper){
                servoClose = false;
            }
            // set launcher go
            if (gamepad1.x){
                launch = true;
            } else if(gamepad1.y){
                launch = false;
            }

            // call functions with all the commands
            clawMovement(servoRotate, servoClose, launch);

            armMovement(armPower);

            driveTrain(yPower, xPower, xRotation, slow);
        }
    }
    public void driveTrain(double y, double x, double rx, boolean slow){
        // lowers the speed of turning
        rx /= 2;

        // makes sure values are capped at 1
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        // reduces general speed to 91% of max or to 33%
        double slowness = !slow ? 1.1 : 3.0;

        // wheels math :P
        fL.setPower(((y + x + rx) / denominator) / slowness);
        fR.setPower(((y - x - rx) / denominator) / slowness);
        bL.setPower(((y - x + rx) / denominator) / slowness);
        bR.setPower(((y + x - rx) / denominator) / slowness);
    }

    public void clawMovement(boolean rotate, boolean close, boolean launch){
        // sets the rotation and closed servo angle
        double k = rotate ? 0.85 : 0.35;
        double g = close ? 1.0 : 0.0;
        double l = !launch ? 1.0 : 0.0;

        // tells servos
        wrist.setPosition(k);
        claw.setPosition(g);
        launcher.setPosition(l);
    }

    public void armMovement(double power){
        // caps power at 40%
        power *= 0.4;

        // if power positive then make arm target 1200
        // if not then set target to 0
        int armTarget = (power > 0) ? 1200 : 0;

        // set target
        lArm.setTargetPosition(armTarget);
        rArm.setTargetPosition(armTarget);

        // set power
        lArm.setPower(power);
        rArm.setPower(power);
    }
}
