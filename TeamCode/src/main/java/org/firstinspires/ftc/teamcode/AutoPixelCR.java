package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class AutoPixelCR extends LinearOpMode {
    private DcMotor bL;
    private DcMotor bR;
    private DcMotor fR;
    private DcMotor fL;
    private Servo wrist;
    private Servo claw;
    private DcMotor lArm;
    private DcMotor rArm;

    @Override
    public void runOpMode(){
        bL = hardwareMap.dcMotor.get("backLeftMotor");
        bR = hardwareMap.dcMotor.get("backRightMotor");
        fR = hardwareMap.dcMotor.get("frontRightMotor");
        fL = hardwareMap.dcMotor.get("frontLeftMotor");

        lArm = hardwareMap.dcMotor.get("leftArmMotor");
        rArm = hardwareMap.dcMotor.get("rightArmMotor");
        claw = hardwareMap.servo.get("servoClaw");
        wrist = hardwareMap.servo.get("servoWrist");


        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);

        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        waitForStart();

        if (isStopRequested()) return;
        claw.setPosition(0.0);
        sleep(1000);
        claw.setPosition(1.0);
        move(0, 0.3, 0);
        sleep(3000);
        brake();
        move(-0.3,0,0);
        sleep(3000);
        brake();
        wrist.setPosition(0.85);
        lArm.setTargetPosition(1000);
        rArm.setTargetPosition(1000);
        lArm.setPower(0.3);
        rArm.setPower(0.3);
        sleep(2500);
        wrist.setPosition(0.75);
        lArm.setTargetPosition(1400);
        rArm.setTargetPosition(1400);
        lArm.setPower(0.2);
        rArm.setPower(0.2);
        sleep(1000);
        claw.setPosition(0.0);
        sleep(1000);
        lArm.setPower(0);
        rArm.setPower(0);
        lArm.setTargetPosition(0);
        rArm.setTargetPosition(0);
        brake();
        lArm.setPower(-0.2);
        rArm.setPower(-0.2);
        sleep(2000);
        move(0.2,0,0);
        sleep(500);
        brake();
        move(0,0.3,0);
        sleep(2500);
        brake();
        move(-0.3,0,0);
        sleep(1500);
        brake();

    }

    public void move(double y, double x, double rx){
        fL.setPower(y + x + rx);
        fR.setPower(y - x - rx);
        bL.setPower(y - x + rx);
        bR.setPower(y + x - rx);
    }
    public void brake(){
        move(0,0,0);
        sleep(1000);
    }
}
