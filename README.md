# Robot2023
Robot2023
To use Talon motors add the following link to your vscode WPILIB vendor libaries:
https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2023-latest.json

To use NavX add:
https://dev.studica.com/releases/2023/NavX.json

# Coding Rules for the season
   1. Always push to GitHub before leaving
   2. Pull main before starting at each practice
   3. All merges to main must be done via PR


# Coding flow for a practice is: 
   1. Pull main
   1. Merge main into your branch OR  start a new branch
   1. Do work
   1. Push your branch
   1. [If you are done] Make a PR to merge your branch to main



## Instructions for PhotonVision

Released firmware at https://github.com/PhotonVision/photonvision/releases

Install instructions: https://docs.photonvision.org/en/latest/docs/getting-started/installation/sw_install/raspberry-pi.html

##### Setup after a burning a fresh image

- Insert new SD card into RaspPi, and connect RaspPi into field kit vie ethernet cable
- Connect laptop into field kit with ethernet cable.  Open browser and navigate to 10.2.81.100:5800.  PhotonVision 
- Settings -> Set team number to 281
- Settings -> ip to static -> 10.2.81.99
- Change browser URL to visit new IP -> 10.2.81.99
- don't change camera name
- Go to Cameras -> calibration
  - https://docs.photonvision.org/en/latest/docs/getting-started/pipeline-tuning/calibration.html
- When calibrating, make sure to set decimation
- On robot code - change firmware version and camera name to match the latest image
