package com.example.cropshot;

import android.app.Activity;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;

public class FirebaseDetection {

    // Reference to the activity we're returning our value to
    private MainActivity activity;

    FirebaseDetection(MainActivity activity)
    {
        this.activity = activity;
    }

    public void runTextDetection(Bitmap croppedMap)
    {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(croppedMap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        detector.processImage(image).addOnSuccessListener(
                new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText texts) {
                        System.out.println("Succeeded process image!");
                        processTextRecognitionResults(texts);

                    }
                }

        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failed to parse image");
                    }
                }
        );
    }

    private void processTextRecognitionResults(FirebaseVisionText texts) {
        System.out.println(texts.getText());

        // Get all of the blocks in the current text
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();

        if (blocks.size() == 0)
            return;

        for (int i = 0; i < blocks.size(); i++) {

            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    // The text detection is not perfect, especially in the case of an image that
                    // is slightly more blurry. When weird issues come up, we add a case to account
                    // for those, hence the "instagam"
                    String str = elements.get(k).getText().toLowerCase();
                    if (checkForInstagramStr(str)) {
                        System.out.println("Instagram image detected!");
                        activity.cropIfImageDetected();
                        return;
                    }
                }
            }
        }
    }

    boolean checkForInstagramStr(String str)
    {
        return (str.contains("instagram") ||
                str.contains("instagam") ||
                str.contains("nstagam") ||
                str.contains("nstagram") ||
                str.contains("posts") ||
                str.contains("explore"));
    }

    // Used for file scanning
    void imgToDetect()
    {

    }

}
