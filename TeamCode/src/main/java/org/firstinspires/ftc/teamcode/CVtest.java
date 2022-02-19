package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CVtest", group="Exercises")
public class CVtest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime(); //Declared AND Initialized
    private DcMotor FrontLeft; //Declared  but not initialized
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor Intake;
    private DcMotor Spinner;
    private DcMotor Intake2;
    private DcMotor Slide;
    private Servo Bucket;
    double armPos;
    double clawPos;
    double drive;
    double turn;
    double strafe;
    double force;
    double spin;
    double slide;
    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    double intakePower;
    double spinnerPower;
    double slidePower;
    double multiplier;
    double timeA; //strafe to carousel
    double timeB; //do carousel
    double timeC; //move back
    double timeD; //turn robot
    double timeE; //strafe left and drive into park
    double tickConversion;
    int intakeSetting;
    int spinnerSetting;
    double intakeFactor;
    int i;
    boolean trackingMode;
    double spinFactor;
    boolean checker;
    boolean rotation;
    boolean holdArm;
    boolean dpadWasDown;
    int clawMode;
    boolean bWasDown;
    boolean xWasDown;
    int armMode;
    double initialposition;
    public double startTime = runtime.milliseconds();

    public WebcamExample webcamExample = null;


    public void runOpMode() throws InterruptedException {
        double x = 0; // encoder ticks/foot
        telemetry.addData("Status", "Initialized");

        webcamExample = new WebcamExample();
        webcamExample.initCV(hardwareMap);

        resetStartTime();
        waitForStart();

        while (opModeIsActive()) {
            int level;
            int[] counts = {0, 0, 0};
            for (int i = 0; i < 50; i++) {
                if (webcamExample.getShippingHubLevel() == 0) {
                    i = 0;
                    continue;
                }
                counts[webcamExample.getShippingHubLevel() - 1]++;
            }
            if (counts[0] > counts[1] && counts[0] > counts[2]) {
                level = 1;
            } else if (counts[1] > counts[0] && counts[1] > counts[2]) {
                level = 2;
            } else {
                level = 3;
            }
            telemetry.addData("Shipping Hub Level", level);
            telemetry.update();

        }
        }
}