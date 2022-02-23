/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.opencv.kotlin.FreightFinder;
import org.firstinspires.ftc.teamcode.opencv.ShippingElementRecognizer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;


public class WebcamExample {
    OpenCvWebcam webcam;
    ShippingElementRecognizer pipeline = new ShippingElementRecognizer();

    public WebcamExample() {

    }

    public int getShippingHubLevel() {
        return pipeline.getShippingHubLevel();
    }

    public void initCV(HardwareMap hardwareMap) {
        // Setup for multiple cameras
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        int[] viewportContainerIds = OpenCvCameraFactory.getInstance()
                .splitLayoutForMultipleViewports(
                        cameraMonitorViewId, //The container we're splitting
                        2, //The number of sub-containers to create
                        OpenCvCameraFactory.ViewportSplitMethod.HORIZONTALLY); //Whether to split the container vertically or horizontally

        // Setup first camera
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), viewportContainerIds[0]);
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

        // Second camera

        /*
         * Wait for the user to press start on the Driver Station
         */
    }

    public void runCV(Telemetry telemetry) {
        /*
         * Send some stats to the telemetry
         */
        telemetry.addData("Frame Count", webcam.getFrameCount());
        telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
        telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
        telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
        telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
        telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
//            telemetry.addData("number of white contours", freightFinder.getWhiteContours().size());
//            telemetry.addData("number of orange contours", freightFinder.getYellowContours().size());
        telemetry.addData("shippinghublevel", pipeline.getShippingHubLevel());
        telemetry.addData("leftrectaverage", pipeline.getLeftValue());
        telemetry.addData("rightrectaverage", pipeline.getRightValue());

        telemetry.update();

        /*
         * NOTE: stopping the stream from the camera early (before the end of the OpMode
         * when it will be automatically stopped for you) *IS* supported. The "if" statement
         * below will stop streaming from the camera when the "A" button on gamepad 1 is pressed.
         */


        /*
         * For the purposes of this sample, throttle ourselves to 10Hz loop to avoid burning
         * excess CPU cycles for no reason. (By default, telemetry is only sent to the DS at 4Hz
         * anyway). Of course in a real OpMode you will likely not want to do this.
         */


        /*
         * An example image processing pipeline to be run upon receipt of each frame from the camera.
         * Note that the processFrame() method is called serially from the frame worker thread -
         * that is, a new camera frame will not come in while you're still processing a previous one.
         * In other words, the processFrame() method will never be called multiple times simultaneously.
         *
         * However, the rendering of your processed image to the viewport is done in parallel to the
         * frame worker thread. That is, the amount of time it takes to render the image to the
         * viewport does NOT impact the amount of frames per second that your pipeline can process.
         *
         * IMPORTANT NOTE: this pipeline is NOT invoked on your OpMode thread. It is invoked on the
         * frame worker thread. This should not be a problem in the vast majority of cases. However,
         * if you're doing something weird where you do need it synchronized with your OpMode thread,
         * then you will need to account for that accordingly.
         */
        class SamplePipeline extends OpenCvPipeline {
            boolean viewportPaused;

            /*
             * NOTE: if you wish to use additional Mat objects in your processing pipeline, it is
             * highly recommended to declare them here as instance variables and re-use them for
             * each invocation of processFrame(), rather than declaring them as new local variables
             * each time through processFrame(). This removes the danger of causing a memory leak
             * by forgetting to call mat.release(), and it also reduces memory pressure by not
             * constantly allocating and freeing large chunks of memory.
             */

            @Override
            public Mat processFrame(Mat input) {
                /*
                 * IMPORTANT NOTE: the input Mat that is passed in as a parameter to this method
                 * will only dereference to the same image for the duration of this particular
                 * invocation of this method. That is, if for some reason you'd like to save a copy
                 * of this particular frame for later use, you will need to either clone it or copy
                 * it to another Mat.
                 */

                /*
                 * Draw a simple box around the middle 1/2 of the entire frame
                 */
                Imgproc.rectangle(
                        input,
                        new Point(
                                input.cols() / 4f,
                                input.rows() / 4f),
                        new Point(
                                input.cols() * (3f / 4f),
                                input.rows() * (3f / 4f)),
                        new Scalar(0, 255, 0), 4);

                /**
                 * NOTE: to see how to get data from your pipeline to your OpMode as well as how
                 * to change which stage of the pipeline is rendered to the viewport when it is
                 * tapped, please see {@link PipelineStageSwitchingExample}
                 */

                return input;
            }

            @Override
            public void onViewportTapped() {
                /*
                 * The viewport (if one was specified in the constructor) can also be dynamically "paused"
                 * and "resumed". The primary use case of this is to reduce CPU, memory, and power load
                 * when you need your vision pipeline running, but do not require a live preview on the
                 * robot controller screen. For instance, this could be useful if you wish to see the live
                 * camera preview as you are initializing your robot, but you no longer require the live
                 * preview after you have finished your initialization process; pausing the viewport does
                 * not stop running your pipeline.
                 *
                 * Here we demonstrate dynamically pausing/resuming the viewport when the user taps it
                 */

                viewportPaused = !viewportPaused;

                if (viewportPaused) {
                    webcam.pauseViewport();
                } else {
                    webcam.resumeViewport();
                }
            }
        }
    }
}