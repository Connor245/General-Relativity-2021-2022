package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Autonomous(name="Drive Encoder 1", group="Exercises")
public class DucksAndParkRedEncoders extends LinearOpMode {
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

    //Function to run motors to position
    public void mecanumDrive(String driveType, double value1, double power) {
        //Forward Function
        if (driveType.equals("forward")) {
            FrontLeft.setTargetPosition((int) (42.78 * value1 + FrontLeft.getCurrentPosition()));
            BackLeft.setTargetPosition((int) (42.78 * value1 + BackLeft.getCurrentPosition()));
            FrontRight.setTargetPosition((int) (42.78 * value1 + FrontRight.getCurrentPosition()));
            BackRight.setTargetPosition((int) (42.78 * value1 + BackRight.getCurrentPosition()));
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //Reverse Function
        else if (driveType.equals("reverse")) {
            FrontLeft.setTargetPosition((int) (-42.78 * value1 + FrontLeft.getCurrentPosition()));
            BackLeft.setTargetPosition((int) (-42.78 * value1 + BackLeft.getCurrentPosition()));
            FrontRight.setTargetPosition((int) (-42.78 * value1 + FrontRight.getCurrentPosition()));
            BackRight.setTargetPosition((int) (-42.78 * value1 + BackRight.getCurrentPosition()));
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //Strafe Right Function
        else if (driveType.equals("strafeRight")) {
            FrontLeft.setTargetPosition((int) (-47.53 * value1 + FrontLeft.getCurrentPosition()));
            BackLeft.setTargetPosition((int) (47.53 * value1 + BackLeft.getCurrentPosition()));
            FrontRight.setTargetPosition((int) (47.53 * value1 + FrontRight.getCurrentPosition()));
            BackRight.setTargetPosition((int) (-47.53 * value1 + BackRight.getCurrentPosition()));
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //Strafe Left Function
        else if (driveType.equals("strafeLeft")) {
            FrontLeft.setTargetPosition((int) (47.53 * value1 + FrontLeft.getCurrentPosition()));
            BackLeft.setTargetPosition((int) (-47.53 * value1 + BackLeft.getCurrentPosition()));
            FrontRight.setTargetPosition((int) (-47.53 * value1 + FrontRight.getCurrentPosition()));
            BackRight.setTargetPosition((int) (47.53 * value1 + BackRight.getCurrentPosition()));
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        } else if (driveType.equals("turnRight")) {
            FrontLeft.setTargetPosition((int) (10.12 * value1)); //enter value in degrees
            BackLeft.setTargetPosition((int) (10.12 * value1));
            FrontRight.setTargetPosition((int) (-10.12 * value1));
            BackRight.setTargetPosition((int) (-10.12 * value1));
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        } else if (driveType.equals("turnLeft")) {
            FrontLeft.setTargetPosition((int) (-10.12 * value1)); //enter value in degrees
            BackLeft.setTargetPosition((int) (-10.12 * value1));
            FrontRight.setTargetPosition((int) (10.12 * value1));
            BackRight.setTargetPosition((int) (10.12 * value1));
            FrontLeft.setPower(power);
            BackLeft.setPower(power);
            FrontRight.setPower(power);
            BackRight.setPower(power);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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

        //add telemetry info to driver hub
        telemetry.addData("Mode", "running");

        // reset encoder counts kept by motors.
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        resetStartTime();
        waitForStart();

        //call mecaunum drive
        mecanumDrive("strafeRight", 1.2, .5);

        mecanumDrive("forward", 3, 1);

        //There are no encoders on the carousel spinner, so this spins the carousel for aa given period of time
        double duckTime = runtime.seconds();
        while (opModeIsActive() && runtime.seconds() < duckTime + 5.5) {
            Spinner.setPower(-.5);
        }

        //Stop using spinner
        Spinner.setPower(0);

        //Set target positions and run to given positions
        mecanumDrive("reverse", 5, 1);

        mecanumDrive("strafeRight", 5, 1);

        mecanumDrive("reverse", 93, 1);

        Slide.setTargetPosition(-500);
        Slide.setPower(0.2);
        Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Spinner.setPower(0);

        mecanumDrive("reverse", 22, 1);

        mecanumDrive("strafeRight", 42, 1);


        Slide.setTargetPosition(0);
        Slide.setPower(0.2);
        Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
