package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.WebcamExample;

@Autonomous(name="OpenCV Red", group="Exercises")
public class OpenCVAuto extends LinearOpMode {
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

    public void mecanumDrive(String driveType, double value1, double power) {
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (driveType.equals("forward")) {
            FrontLeft.setTargetPosition((int) (42.78 * value1)); //enter value in inches
            BackLeft.setTargetPosition((int) (-42.78 * value1));
            FrontRight.setTargetPosition((int) (42.78 * value1));
            BackRight.setTargetPosition((int) (42.78 * value1));
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
        } else if (driveType.equals("strafe")) {
            FrontLeft.setTargetPosition((int) (47.53 * value1)); //enter value in inches
            BackLeft.setTargetPosition((int) (47.53 * value1));
            FrontRight.setTargetPosition((int) (-47.53 * value1));
            BackRight.setTargetPosition((int) (47.53 * value1));
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
        } else if (driveType.equals("turn")) {
            FrontLeft.setTargetPosition((int) (10.12 * value1)); //enter value in degrees
            BackLeft.setTargetPosition((int) (-10.12 * value1));
            FrontRight.setTargetPosition((int) (-10.12 * value1));
            BackRight.setTargetPosition((int) (-10.12 * value1));
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
        }

        //noinspection StatementWithEmptyBody
        while ((FrontLeft.isBusy() || BackLeft.isBusy() || BackRight.isBusy() || FrontRight.isBusy()) && opModeIsActive()) {
        }
        FrontLeft.setPower(0.0);
        BackLeft.setPower(0.0);
        FrontRight.setPower(0.0);
        BackRight.setPower(0.0);
    }

    public void runOpMode() throws InterruptedException {
        double x = 0; // encoder ticks/foot
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

        webcamExample = new WebcamExample();
        webcamExample.initCV(hardwareMap);
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

        // reset encoder counts kept by motors.
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set motors to run to target encoder position and stop with brakes on.

        //Spinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Mode", "running");
        telemetry.update();
        resetStartTime();
        waitForStart();
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

        mecanumDrive("forward", -3.2, .55);
        mecanumDrive("strafe", -4.2, .5);
        mecanumDrive("forward", -3.5, .5);
        telemetry.addLine("moved");
        double duckTime = runtime.seconds();
        while (opModeIsActive() && runtime.seconds() < duckTime + 6) {
            Spinner.setPower(-0.5);



        }
        Spinner.setPower(0);
        mecanumDrive("strafe", -39, .5);
        mecanumDrive("forward", 28, .5);



        Slide.setTargetPosition(-210-240*level);
        Slide.setPower(0.5);
        Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (Slide.isBusy()) {
        }

        resetStartTime();
        double bucketTime = runtime.seconds();
        while (opModeIsActive() && runtime.seconds() < bucketTime + 3) {
            Bucket.setPosition(0.5);
        }

        resetStartTime();
        double bucketTime2 = runtime.seconds();
        while (opModeIsActive() && runtime.seconds() < bucketTime2 + 1) {
            Bucket.setPosition(1);
        }

        Slide.setTargetPosition(0);
        Slide.setPower(0.5);
        Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (Slide.isBusy()) {
        }
        mecanumDrive("forward", -27, 1);
        mecanumDrive("strafe", 41, 1);
        mecanumDrive("forward", 112, 1);
    }
}