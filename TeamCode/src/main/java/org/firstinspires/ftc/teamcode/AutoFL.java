package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class AutoFL extends LinearOpMode {
    private DcMotor bL;
    private DcMotor bR;
    private DcMotor fR;
    private DcMotor fL;
    private Servo wrist;
    private DcMotor lArm;
    private DcMotor rArm;
    private Servo claw;

    @Override
    public void runOpMode(){
        bL = hardwareMap.dcMotor.get("backLeftMotor");
        bR = hardwareMap.dcMotor.get("backRightMotor");
        fR = hardwareMap.dcMotor.get("frontRightMotor");
        fL = hardwareMap.dcMotor.get("frontLeftMotor");
        lArm = hardwareMap.dcMotor.get("leftArmMotor");
        rArm = hardwareMap.dcMotor.get("rightArmMotor");

        wrist = hardwareMap.servo.get("servoWrist");
        claw = hardwareMap.servo.get("servoClaw");

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);
        lArm.setDirection(DcMotorSimple.Direction.REVERSE);

        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lArm.setTargetPosition(0);
        rArm.setTargetPosition(0);
        lArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (isStopRequested()) return;
        claw.setPosition(1.0);
        sleep(1000);
        wrist.setPosition(0.85);
        lArm.setTargetPosition(250);
        rArm.setTargetPosition(250);
        lArm.setPower(0.2);
        rArm.setPower(0.2);
        sleep(1000);
        move(0,0.3,-0.015);
        sleep(6700);
        brake();
        move(0.5, 0, -0.008);
        sleep( 3500);
        brake();
        lArm.setTargetPosition(0);
        rArm.setTargetPosition(0);
        wrist.setPosition(0.25);
        claw.setPosition(0.0);
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
