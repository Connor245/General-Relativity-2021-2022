package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//@Disabled
@TeleOp(name="TeleOp_Red", group="Iterative Opmode")
public class RedAutopilotOpModePartII extends OpMode {

    // Declare  OpMode members.
    private final ElapsedTime runtime = new ElapsedTime(); //Declared AND Initialized
    private DcMotor FrontLeft; //Declared  but not initialized
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor Intake;
    private DcMotor Spinner;
    private DcMotor Intake2;
    private DcMotor Slide;
    private Servo Bucket;
    double bucketPos;
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
    boolean leftTriggerWasDown;
    double intakeFactor;
    int i;
    boolean trackingMode;
    double spinFactor;
    boolean checker;
    boolean rotation;
    boolean xWasDown;
    boolean runSpinner;
    public double startTime = runtime.seconds();

    @Override
    public void init() {
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
        intakeSetting = 2;
        intakeFactor = 1.0;
        trackingMode = false;
        spinFactor = 1.0;
        checker = false;
        rotation = false;
        bucketPos = 0.5;
        xWasDown = false;
        leftTriggerWasDown = false;
        runSpinner = false;

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


        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
    }


    @Override
    public void init_loop() {
        //Servo1.setPosition(0.3);
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    //key press function
    public void loop() {
        startTime = runtime.seconds();
        //tracking object mode: User input towards object and robot automatically connects
        if (gamepad1.b) {
            if (gamepad1.right_trigger > 0.5) {
                double y_coordinate = -gamepad1.left_stick_x;
                double x_coordinate = -gamepad1.left_stick_y;
                telemetry.addLine("tracking mode on");
                double distance = Math.sqrt(Math.pow(x_coordinate, 2) + Math.pow(y_coordinate, 2));
                telemetry.update();
                if (distance > 0.0) {
                    //distance input is determined by stick position magnitude
                    //angle input is determined by stick position angle
                    double angle = 50 * Math.asin(y_coordinate / distance);
                    telemetry.addLine(String.valueOf(angle));
                    int p = 0;
                    while (p < (300 * Math.abs(angle * y_coordinate))) {
                        if (x_coordinate < 0) {
                            frontLeftPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                        }
                        if (x_coordinate >= 0) {
                            frontLeftPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                        }
                        p++;
                    }
                    int j = 0;
                    while (j < (20000 * distance)) {
                        if (x_coordinate < 0) {
                            frontLeftPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                            j++;
                        }
                        if (x_coordinate >= 0) {
                            frontLeftPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                            j++;
                        }
                    }
                    //lines above control how motors are activated to conform to angle and distance values
                    FrontLeft.setPower(0);
                    FrontRight.setPower(0);
                    BackLeft.setPower(0);
                    BackRight.setPower(0);
                    int k = 0;
                    while (k < 80000) {
                        intakePower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                        Intake.setPower(-intakePower);
                        Intake2.setPower(intakePower);
                        k++;
                    }
                    Intake.setPower(0);
                    Intake2.setPower(0);
                }
            }
        }
        else {
            //Bucket controls
            if (gamepad2.b) {
                if (!xWasDown){
                    bucketPos = 0.6;
                }
            } else {
                bucketPos = 1.0;
            }
            //Slide controls
            if (Slide.getCurrentPosition() < 20) {
                //gamepad2 left stick
                slide = 1.5*gamepad2.left_stick_y;
            } else {
                slide = -0.25;
                Slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            //Intake + Spinner settings
            if (gamepad1.dpad_up) {
                intakeSetting = 1;
            }
            if (gamepad1.dpad_down) {
                intakeSetting = 2;
            }
            if (intakeSetting == 1) {
                intakeFactor = 1.0;
            }
            if (intakeSetting == 2) {
                intakeFactor = -1.0;
            }

            //Using the spinner
            if (gamepad2.right_trigger > 0.5) {
                runSpinner = true;
            }
            if (runSpinner) {
                spin = 0.20 + (startTime * 0.22);
                telemetry.addData("spinner power", spin);
                telemetry.addData("is it working", 2);
                if (startTime > 2) {
                    spin = 0;
                    runtime.reset();
                    telemetry.addData("done:)", 1);
                    runSpinner = false;
                }
            }

            //Movement variables based on user inputs
            force = gamepad1.right_trigger;
            drive = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            turn = -gamepad1.right_stick_x;
            spinnerPower = Range.clip(spin, -1.0, 2.0) * 2;
            intakePower = Range.clip(force, -1.0, 1.0) * 0.8;
            slidePower = Range.clip(slide, -1.0, 1.0) * 0.4;
            frontLeftPower = Range.clip(drive + turn + strafe, -1.0, 1.0) * 0.8;
            frontRightPower = Range.clip(drive - turn - strafe, -1.0, 1.0) * 0.8;
            backLeftPower = Range.clip(drive + turn - strafe, -1.0, 1.0) * 0.8;
            backRightPower = Range.clip(drive - turn + strafe, -1.0, 1.0) * 0.8;

            //make sure left and right power are outside threshold


            // public double clip(double number, double min, double max)

/*           telemetry.addData("drive", drive);
            telemetry.addData("turn", turn);
            telemetry.addData("strafe", strafe);
            telemetry.addData("force", force);
            telemetry.addData("spin", spin);
            telemetry.addData("intakeSetting", intakeSetting);
            telemetry.addData("spinnerSetting", spinnerSetting);*/
            //Telemetry
            telemetry.addData("encoder-front-left", FrontLeft.getCurrentPosition());
            telemetry.addData("encoder-back-left", BackLeft.getCurrentPosition());
            telemetry.addData("encoder-front-right", FrontRight.getCurrentPosition());
            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());
            telemetry.addData("encoder-slide", Slide.getCurrentPosition());            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());


            telemetry.addData("Bucket Position", bucketPos);
            telemetry.addData("Run Time", startTime);

            telemetry.update();
            //Sets all of the motors
            Bucket.setPosition(bucketPos);
            FrontLeft.setPower(multiplier * frontLeftPower);
            FrontRight.setPower(multiplier * frontRightPower);
            BackLeft.setPower(multiplier * backLeftPower);
            BackRight.setPower(multiplier * backRightPower);
            Intake.setPower(-intakeFactor * intakePower);
            Spinner.setPower(-spinnerPower);
            Intake2.setPower(intakeFactor * intakePower);
            Slide.setPower(slidePower);
        }
        telemetry.update();
    }
}