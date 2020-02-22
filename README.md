# FRC 2016 Test Computer Vision
This software was used to test the efficacy of vision target detection using a regular camera, as a comparison to the Xbox Kinect.

### Hardware: 
This software ran on my laptop and used the built-in webcam.

### How does it work?
An explanation of vision targets is available here: https://docs.wpilib.org/en/latest/docs/software/vision-processing/introduction/target-info-and-retroreflection.html

A green LED light ring was affixed around the webcam to illuminate the vision target. A mask is created by excluding all non-green colors, then annotations are drawn on the display around blobs that match the expected characteristics of the target.

### Attributions:
Special thanks go to Atul R. for developing an implementation of the C++ imshow function in Java: https://github.com/master-atul/ImShow-Java-OpenCV
