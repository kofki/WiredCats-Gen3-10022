package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class AutoCR extends LinearOpMode {
    private DcMotor bL;
    private DcMotor bR;
    private DcMotor fR;
    private DcMotor fL;

    @Override
    public void runOpMode(){
        bL = hardwareMap.dcMotor.get("backLeftMotor");
        bR = hardwareMap.dcMotor.get("backRightMotor");
        fR = hardwareMap.dcMotor.get("frontRightMotor");
        fL = hardwareMap.dcMotor.get("frontLeftMotor");

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


        waitForStart();

        if (isStopRequested()) return;

        move(0.3, 0, 0);
        sleep(4000);
        move(0, 0, 0);
        sleep(1000);
    }

    public void move(double y, double x, double rx){
        fL.setPower(y + x + rx);
        fR.setPower(y - x - rx);
        bL.setPower(y - x + rx);
        bR.setPower(y + x - rx);
    }
}
