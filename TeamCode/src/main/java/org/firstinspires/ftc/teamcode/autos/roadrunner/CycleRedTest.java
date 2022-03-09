package org.firstinspires.ftc.teamcode.autos.roadrunner;

import android.graphics.Color;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.opmodes.Webcam;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "CycleRedTest.java", group = "Exercises")
public class CycleRedTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime(); //Declared AND Initialized
    public DcMotor FrontLeft; //Declared  but not initialized
    public DcMotor FrontRight;
    public DcMotor BackLeft;
    public DcMotor BackRight;
    public DcMotor Intake;
    public DcMotor Spinner;
    public DcMotor Intake2;
    public DcMotor Slide;
    public Servo Bucket;
    int x = 0;
    public Webcam webcamExample;
    final float[] hsvValues = new float[3];
    float gain = 2;
    boolean hasFreight;
    int level;
    Pose2d currentPose = new Pose2d(6, -64, Math.toRadians(-90));
    RevColorSensorV3 colorSensor;

    public void mecanumDrive(String driveType, double value1, double power) {

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (driveType.equals("forward")) {
            FrontLeft.setTargetPosition((int) (42.78 * value1)); //enter value in inches
            BackLeft.setTargetPosition((int) (42.78 * value1));
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
            BackLeft.setTargetPosition((int) (-47.53 * value1));
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
            BackLeft.setTargetPosition((int) (10.12 * value1));
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

    public void scoreFreight(int height) {
        Slide.setTargetPosition(height);
        Slide.setPower(1);
        Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (Slide.isBusy() && opModeIsActive()) {
        }
        resetStartTime();
        double bucketTime = runtime.seconds();
        while (opModeIsActive() && runtime.seconds() < bucketTime + 1.5) {
            Bucket.setPosition(0.3);
        }
        while (opModeIsActive() && runtime.seconds() < bucketTime + 2.5) {
            Bucket.setPosition(0.77);
        }

        Slide.setTargetPosition(0);
        Slide.setPower(1);
        Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void intakeColor() {

        runtime.reset();
        while (opModeIsActive() && hasFreight == false) {
            colorSensor();
        }
        if (hasFreight) {
            telemetry.addData("Freight", "Yes");

        } else {
            telemetry.addData("Freight", "No");
        }
        telemetry.update();
    }

    public void colorSensor() {
        // You can give the sensor a gain value, will be multiplied by the sensor's raw value before the
        // normalized color values are calculated. Color sensors (especially the REV Color Sensor V3)
        // can give very low values (depending on the lighting conditions), which only use a small part
        // of the 0-1 range that is available for the red, green, and blue values. In brighter conditions,
        // you should use a smaller gain than in dark conditions. If your gain is too high, all of the
        // colors will report at or near 1, and you won't be able to determine what color you are
        // actually looking at. For this reason, it's better to err on the side of a lower gain
        // (but always greater than  or equal to 1).
        float gain = 2;

        // Once per loop, we will update this hsvValues array. The first element (0) will contain the
        // hue, the second element (1) will contain the saturation, and the third element (2) will
        // contain the value. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
        // for an explanation of HSV color.
        final float[] hsvValues = new float[3];

        // Get a reference to our sensor object. It's recommended to use NormalizedColorSensor over
        // ColorSensor, because NormalizedColorSensor consistently gives values between 0 and 1, while
        // the values you get from ColorSensor are dependent on the specific sensor you're using.
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "Color");

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }


        Bucket.setPosition(0.77);

        // Loop until we are asked to stop

            colorSensor.setGain(gain);

            // If the button state is different than what it as, then act

                    if (colorSensor instanceof SwitchableLight) {
                        SwitchableLight light = (SwitchableLight) colorSensor;
                        light.enableLight(!light.isLightOn());
                    }

            // Get the normalized colors from the sensor
            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
             * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
             * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
             * for an explanation of HSV color. */

            // Update the hsvValues array by passing it to Color.colorToHSV()
            Color.colorToHSV(colors.toColor(), hsvValues);

            /* If this color sensor also has a distance sensor, display the measured distance.
             * Note that the reported distance is only useful at very close range, and is impacted by
             * ambient light and surface reflectivity. */
            if (colorSensor instanceof DistanceSensor) {
                telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
            }

            double dist = ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM);
            double hue = hsvValues[0];

            if (dist < 2.5) {
                if (60 < hue && hue < 95 || hue > 100 && hsvValues[2] > 0.1) {
                    telemetry.addLine("The intake has a block in it");
                    hasFreight = true;
                } else {
                    telemetry.addLine("The intake has a ball in it");
                    hasFreight = true;
                }
            } else {
                telemetry.addLine("The intake is empty");
                hasFreight = false;
            }

            telemetry.update();

        }

//    public void getPosition() {
//        SampleMecanumDrive myLocalizer = new SampleMecanumDrive(hardwareMap);
//
//        myLocalizer.update();
//
//        Pose2d currentPose = myLocalizer.getPoseEstimate();
//    }

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Spinner = hardwareMap.get(DcMotor.class, "Spinner");
        Intake2 = hardwareMap.get(DcMotor.class, "Intake2");
        Slide = hardwareMap.get(DcMotor.class, "Slide");
        Bucket = hardwareMap.get(Servo.class, "Bucket");

        ColorSensor color;
        color = hardwareMap.get(ColorSensor.class, "Color");

        // Get a reference to our sensor object. It's recommended to use NormalizedColorSensor over
        // ColorSensor, because NormalizedColorSensor consistently gives values between 0 and 1, while
        // the values you get from ColorSensor are dependent on the specific sensor you're using.
        color = hardwareMap.get(RevColorSensorV3.class, "Color");

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (color instanceof SwitchableLight) {
            ((SwitchableLight) color).enableLight(true);
        }

        webcamExample = new Webcam();
        webcamExample.init(hardwareMap);
        //Set starting pose

        Pose2d startPose = new Pose2d(6, -64, Math.toRadians(-90));

        drive.setPoseEstimate(startPose);

        // Get the normalized colors from the sensor

        resetStartTime();
        while(opModeIsActive() && runtime.seconds()< 1) {
            telemetry.addData("Hub Level", webcamExample.getShippingHubLevel());
            if(webcamExample.getShippingHubLevel() == 0) {
                resetStartTime();
            }
            if (webcamExample.getShippingHubLevel() == 1) { // Level = 1
                level = 1;

            } else if (webcamExample.getShippingHubLevel() == 2) { // Level = 2
                level = 2;
            } else { // Level = 3
                level = 3;
            }
            telemetry.addData("Hub Level", level);
            telemetry.update();
        }
        telemetry.addData("Hub Level", level);
        telemetry.update();

        if (isStopRequested()) return;

        waitForStart();

        //Cycle from starting position to hub
        TrajectorySequence traj = drive.trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .splineTo(new Vector2d(-15, -46), Math.toRadians(90))
                .build();
        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .splineTo(new Vector2d(-15, -52), Math.toRadians(90))
                .build();
        if(level ==1) {
            telemetry.addData("Level", "1");
            drive.followTrajectorySequence(traj5);
            scoreFreight(-400);
        } else if(level == 2) {
            telemetry.addData("Level", "2");
            drive.followTrajectorySequence(traj);
            scoreFreight(-700);
        }
         else{
            telemetry.addData("Level", "3");
            drive.followTrajectorySequence(traj);
            scoreFreight(-1100);

        }

        hasFreight = false;

        //Cycle to warehouse
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj.end())
                .setReversed(false)
                .splineTo(new Vector2d(38, -66), Math.toRadians(0))
                .build();
        drive.followTrajectorySequence(traj2);

while (!hasFreight && !isStopRequested()) {
            colorSensor();
            Intake.setPower(-0.4);
            Intake2.setPower(1);
            drive.setWeightedDrivePower(new Pose2d(-.15, 0, 0));
            drive.setWeightedDrivePower(new Pose2d(.225, 0, 0));
        }
        sleep(300);
        drive.update();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .setReversed(true)
                .splineTo(new Vector2d(-15, -46), Math.toRadians(90))
                .build();


        drive.followTrajectorySequence(traj3);

        Intake.setPower(0);
        Intake2.setPower(0);
        scoreFreight(-1050);
        hasFreight = false;

        drive.followTrajectorySequence(traj2);

        while (!hasFreight && !isStopRequested()) {
            colorSensor();
            Intake.setPower(-0.4);
            Intake2.setPower(1);
            drive.setWeightedDrivePower(new Pose2d(-.15, 0, 0));
            drive.setWeightedDrivePower(new Pose2d(.225, 0, 0));
        }
sleep(500);
        drive.update();
        drive.followTrajectorySequence(traj3);
        Intake.setPower(0);
        Intake2.setPower(0);
        scoreFreight(-1050);
hasFreight = false;

        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj.end())
                .setReversed(false)
                .splineTo(new Vector2d(35, -66), Math.toRadians(0))
                .build();
        drive.followTrajectorySequence(traj4);
        drive.update();
    }
}


