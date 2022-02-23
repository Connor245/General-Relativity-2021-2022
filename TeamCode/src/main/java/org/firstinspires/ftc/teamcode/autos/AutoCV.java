package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.opencv.ShippingElementRecognizer;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


public class AutoCV {

    @Autonomous(name = "Auto Minus Duck (Blue)", group = "Autonomous")
    public class NoDuckBlue extends LinearOpMode {

        OpenCvWebcam webcam;

        public void runOpMode() throws InterruptedException {


            // Camera things
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);
            ShippingElementRecognizer pipeline = new ShippingElementRecognizer();
            webcam.setPipeline(pipeline);
            webcam.setMillisecondsPermissionTimeout(2500);
            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    webcam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
                }

                @Override
                public void onError(int errorCode) {
                    // This will be called if the camera could not be opened
                }
            });

            waitForStart();
            // Start button is pressed

            // Get the placement of the shipping element 100 times and pick the most frequent position
            int level;
            int[] counts = {0, 0, 0};
            for (int i = 0; i < 50; i++) {
                wait(10);
                if (pipeline.getShippingHubLevel() == 0) {
                    i = 0;
                    continue;
                }
                counts[pipeline.getShippingHubLevel() - 1]++;
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


