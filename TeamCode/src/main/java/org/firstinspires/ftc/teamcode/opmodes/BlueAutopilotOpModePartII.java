package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//@Disabled
@TeleOp(name="TeleOp_Blue", group="Iterative Opmode")
public class BlueAutopilotOpModePartII extends OpMode {

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
    int runSpinner;
    boolean driversNotifiedEndgame = false;

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
        bucketPos = 0.45;
        xWasDown = false;
        leftTriggerWasDown = false;
        runSpinner = 0;

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
        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
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

    //key press function
    public void loop() {


        ElapsedTime elapsedTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        startTime = runtime.seconds();
        //tracking object mode: User input towards object and robot automatically connects

            //Bucket controls
            if (gamepad2.b) {
                if (!xWasDown) {
                    bucketPos = 0;
                }
            } else {
                bucketPos = 0.45;
            }
            //Slide controls
        if (gamepad2.y) {
            slide = -1.0;
        }
        if (!gamepad2.a && !gamepad2.y) {
            slide = 0;
        }
        if (gamepad2.a) {
            slide = 1.0;
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
            if (gamepad2.right_bumper) {
                runSpinner = 1;
                runtime.reset();
                resetStartTime();
            }
            if (gamepad2.left_bumper) {
                runSpinner = 2;
                runtime.reset();
                resetStartTime();
            }
            if (runSpinner ==1 && startTime < 1.2) {
                spin = 0.20 + (startTime * 0.22);
                telemetry.addData("spinner power", spin);
                telemetry.addData("is it working", 2);
            } else if (runSpinner == 1 && startTime > 1.2 && startTime < 1.4) {
                spin = 1;
                telemetry.addData("spinner power", spin);
            } else if (startTime > 1.4) {
                spin = 0;
                telemetry.addData("done:)", 1);
                runSpinner = 0;

            }
            if (runSpinner ==2 && startTime < 1.2) {
                spin = -(0.20 + (startTime * 0.22));
                telemetry.addData("spinner power", spin);
                telemetry.addData("is it working", 2);
            } else if (runSpinner == 2 && startTime > 1.2 && startTime < 1.4) {
                spin = -1;
                telemetry.addData("spinner power", spin);
            } else if (startTime > 1.4) {
                spin = 0;
                telemetry.addData("done:)", 1);
                runSpinner = 0;

            }

            //Movement variables based on user inputs
            force = gamepad1.right_trigger;
            drive = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            turn = -gamepad1.right_stick_x;
            spinnerPower = Range.clip(spin, -1.0, 2.0) * 2;
            intakePower = Range.clip(force, -1.0, 1.0) * 0.8;
            slidePower = Range.clip(slide, -1.0, 1.0) * 0.4;
            frontLeftPower = Range.clip(drive + turn + strafe, -1.0, 1.0);
            frontRightPower = Range.clip(drive - turn - strafe, -1.0, 1.0);
            backLeftPower = Range.clip(drive + turn - strafe, -1.0, 1.0);
            backRightPower = Range.clip(drive - turn + strafe, -1.0, 1.0) * -1;

            //make sure left and right power are outside threshold


            // public double clip(double number, double min, double max)

/*
          telemetry.addData("drive", drive);
            telemetry.addData("turn", turn);
            telemetry.addData("strafe", strafe);
            telemetry.addData("force", force);
            telemetry.addData("spin", spin);
            telemetry.addData("intakeSetting", intakeSetting);
            telemetry.addData("spinnerSetting", spinnerSetting);*/
            //Telemetry
//            telemetry.addData("encoder-front-left", FrontLeft.getCurrentPosition());
//            telemetry.addData("encoder-back-left", BackLeft.getCurrentPosition());
//            telemetry.addData("encoder-front-right", FrontRight.getCurrentPosition());
//            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());
//            telemetry.addData("encoder-slide", Slide.getCurrentPosition());            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());
//
//
//            telemetry.addData("Bucket Position", bucketPos);
            telemetry.addData("Run Time", startTime);

            telemetry.update();
            //Sets all of the motors
            Bucket.setPosition(bucketPos);
            FrontLeft.setPower(multiplier * frontLeftPower);
            FrontRight.setPower(multiplier * frontRightPower);
            BackLeft.setPower(multiplier * backLeftPower);
            BackRight.setPower(multiplier * backRightPower);

//            if (((colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) >= 6)
//                    && colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) >= 9)
//                    || colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) > 12) {
//                resetStartTime();
//                if(startTime < 250) {
//                    Intake.setPower(intakeFactor * intakePower);
//                }
//            }
//
//            else {
//                Intake.setPower(-intakeFactor * intakePower);
//            }
            Intake.setPower(intakeFactor * -intakePower);
            Spinner.setPower(spinnerPower);
            Intake2.setPower(intakeFactor * intakePower);
            Slide.setPower(slidePower);

        if (elapsedTime.seconds() >= 85 && !driversNotifiedEndgame) { // 85 = 5 seconds before
            gamepad1.rumbleBlips(3);
            gamepad2.rumbleBlips(3);
            driversNotifiedEndgame = true;
        }

        telemetry.update();
    }
}
