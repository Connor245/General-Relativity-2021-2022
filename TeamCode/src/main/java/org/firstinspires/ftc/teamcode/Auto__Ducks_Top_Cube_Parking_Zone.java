//please note: completely expected to crash and burn
//INCOMPLETE DO NOT RUN NEED TO ACTUALLY CODE MOVEMENT
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Disabled
public class Auto__Ducks_Top_Cube_Parking_Zone extends LinearOpMode
{
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
    int intakeSetting;
    int spinnerSetting;
    double intakeFactor;
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
    public double startTime = runtime.milliseconds();
    public void setMecanumPower(){
        FrontLeft.setPower(multiplier * Range.clip(drive - turn - strafe, -1.0, 1.0) * 0.8);
        FrontRight.setPower(multiplier * Range.clip(drive + turn + strafe, -1.0, 1.0) * 0.8);
        BackLeft.setPower(multiplier * Range.clip(drive - turn + strafe, -1.0, 1.0) * 0.8);
        BackRight.setPower(multiplier * Range.clip(drive + turn - strafe, -1.0, 1.0) * 0.8);
    }
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("Status", "Initialized");
        drive = 0.0;
        turn = 0.0;
        strafe = 0.0;
        force = 0.0;
        spin = 0.0;
        slide = 0.0;
        frontLeftPower = 0.0;
        frontRightPower = 0.0;
        backLeftPower = 0.0;
        backRightPower = 0.0;
        intakePower = 0.0;
        spinnerPower = 0.0;
        slidePower = 0.0;
        multiplier = 1.0;
        intakeSetting = 1;
        spinnerSetting = 1;
        intakeFactor = 1.0;
        trackingMode = false;
        spinFactor = 0.0;
        checker = false;
        rotation = false;
        armPos = 0.5;
        clawPos = 0.9;
        holdArm = false;
        clawMode = 1;
        bWasDown = false;
        xWasDown = false;
        dpadWasDown = false;
        armMode = 0;

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Spinner = hardwareMap.get(DcMotor.class, "Spinner");
        Intake2 = hardwareMap.get(DcMotor.class, "Intake2");
        Slide = hardwareMap.get(DcMotor.class, "Slide");
        Bucket = hardwareMap.get(Servo.class, "Bucket");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);
        Spinner.setDirection(DcMotor.Direction.FORWARD);
        Intake2.setDirection(DcMotor.Direction.FORWARD);
        Slide.setDirection(DcMotor.Direction.FORWARD);


        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Spinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        // wait while opmode is active and left motor is busy running to position.
        // set motor power to zero to stop motors.

        resetStartTime();
        waitForStart();

        while (opModeIsActive() && runtime.seconds()<0.5) {
        strafe = 0.5;
        drive = 0;
        setMecanumPower();
    }
        while (opModeIsActive() && (0.5<runtime.seconds()) && runtime.seconds()<1) {
            strafe = 0;
            drive = -0.5;
            setMecanumPower();
        }

        while (opModeIsActive() && (1<runtime.seconds()) && runtime.seconds()<5.0) {
            Spinner.setDirection(DcMotor.Direction.REVERSE);
            spinnerPower = 1;
        }


    }
}
