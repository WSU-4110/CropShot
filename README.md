# CropShot
This project contains the source code for an android application named CropShot. The goal of this application is to automatically crop a screenshotted image from Instagram, so any unnecessary clutter is removed from the screenshot. This README file will go explain how to use the application and the features that are included in this application. 

![BeforeCrop](https://github.com/WSU-4110/CropShot/blob/master/images/imageopened.png)
![AfterCrop](https://github.com/WSU-4110/CropShot/blob/master/images/imagecropped.png)


## 1.	Getting CropShot on your phone
#### -	Go to the Google Play Store and search for “CropShot” (note: this application is only available on android phones)
#### -	Download the application
#### -	Open the application on your android device 
#### -	Allow the application to access the device’s gallery, camera, etc.

## 2.	Cropping an Image

![MainScreen](https://github.com/WSU-4110/CropShot/blob/master/images/appstartscreen.png)

#### -	On the main screen, click on the “Gallery” Button
#### -	Select any Instagram image that you have in your device

![BeforeCrop](https://github.com/WSU-4110/CropShot/blob/master/images/imageopened.png)

#### -	The image will show up in the center of the screen. 
#### -	Click on the “Auto Crop” button 
#### -	You will now be in a new screen. This is called the Post Crop Screen

![ImageCropped](https://github.com/WSU-4110/CropShot/blob/master/images/imagecropped.png)

#### -	From this screen, you have 4 options
  ###### -	Discard: You can choose to discard the image and return to the main screen
  ###### -	Save: This will save the newly cropped image as a separate image
  ###### -	Overwrite: This will delete the previously selected image in the gallery and replace it with the newly cropped image
  ###### -	Manual Crop: If you would like to crop the image even more, you can choose to manually crop the image yourself

## 3.	Manually Cropping
#### -	Once you select an image, you can choose to crop the image yourself by clicking on the “Manual Crop” Button (this button is also located in the Post Crop Screen)

![ManualCrop](https://github.com/WSU-4110/CropShot/blob/master/images/manualcrop.png)
![ManualCropCllicked](https://github.com/WSU-4110/CropShot/blob/master/images/manualcropclicked.png)

#### -	 Once you adjust the crop window to what is desired, you can save the image into the device’s gallery. 

## 4.	Settings
#### -	To adjust the settings, click on the “settings” button
#### -	To learn who developed the application and what version of the application you own, click on the “About” button
#### -	You can toggle between dark and light (normal) mode by clicking on the toggle button. Dark mode will only put the application in dark mode, not the whole device.
#### -	The user has the option to use ML and firebase detection. If this button is turned on, the application will use machine learning when deciding whether to crop an image. The firebase detection is not necessary for properly detecting MOST instagram images, but there are corner cases where this will rule out images that are false positives. Additionally, sometimes the firebase API has installation issues, if it seems like images that aren't cropping should be cropping, try turning this setting off and trying again.

![Settings](https://github.com/WSU-4110/CropShot/blob/master/images/settings.png)

#### - Application in dark mode:

![DarkMode](https://github.com/WSU-4110/CropShot/blob/master/images/darkmode.png)

## 5.	Auto Scan Through gallery
#### -	If you click on the “Auto-Scan” button, the application will automatically go through your gallery and crop every Instagram image. When choosing this option, your original images are kept and the cropped images are saved as new.
