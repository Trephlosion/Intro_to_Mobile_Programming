package com.example.drawingtest;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import com.example.drawingtest.DrawingView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

class UnistrokeResult {
    String name;
    float score;
    Long time;

    UnistrokeResult(String name, float score, Long time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }
}

class CGPoint {
    float x;
    float y;


    CGPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    float distanceToPt(CGPoint point) {
        float xDist = x - point.x;
        float yDist = y - point.y;
        return (float) Math.sqrt(xDist * xDist + yDist * yDist);
    }
}

class CGPointArray extends ArrayList<CGPoint> {
    float pathLength() {
        float length = 0;
        for (int i = 1; i < size(); i++) {
            length += get(i - 1).distanceToPt(get(i));
        }
        return length;
    }

    float boundingBox() {
        float minX = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < size(); i++) {
            float x = get(i).x;
            float y = get(i).y;

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        return (maxX - minX) * (maxY - minY);
    }

    CGPoint centroid() {
        float xSum = 0;
        float ySum = 0;

        for (int i = 0; i < size(); i++) {
            xSum += get(i).x;
            ySum += get(i).y;
        }

        float xCentroid = xSum / size();
        float yCentroid = ySum / size();

        return new CGPoint(xCentroid, yCentroid);
    }

    float indicativeAngle() {
        CGPoint centroid = centroid();
        return (float) Math.atan2(centroid.y - get(0).y, centroid.x - get(0).x);
    }

    float[] vectorized() {
        float sum = 0;
        float[] vector = new float[2 * size()];

        for (int i = 0; i < size(); i++) {
            vector[2 * i] = get(i).x;
            vector[2 * i + 1] = get(i).y;
            sum += get(i).x * get(i).x + get(i).y * get(i).y;
        }

        float magnitude = (float) Math.sqrt(sum);

        for (int i = 0; i < vector.length; i++) {
            vector[i] /= magnitude;
        }

        return vector;
    }
}

class UnistrokeRecognizer {
    static final int numPoints = 64;
    static final float squareSize = 250;
    static final CGPoint origin = new CGPoint(0, 0);
    static final float angleRange = (float) Math.toRadians(45);
    static final float anglePrecision = (float) Math.toRadians(2);
    static final float diagonal = (float) Math.sqrt(squareSize * squareSize + squareSize * squareSize);
    static final float halfDiagonal = 0.5f * diagonal;

    private final List<UnistrokeTemplate> templates = new ArrayList<>();

    void addTemplate(String name, CGPointArray points) {
        templates.add(new UnistrokeTemplate(name, points));
    }

    void addTemplate(UnistrokeTemplate template) {
        templates.add(template);
    }

    void deleteTemplates() {
        templates.clear();
    }

    UnistrokeResult recognize(CGPointArray points, boolean isUsingProtractor) {
        long startTime = System.currentTimeMillis();
        CGPointArray pts = resample(points, numPoints);
        float radians = pts.indicativeAngle();
        pts = rotate(pts, -radians);
        pts = scale(pts, squareSize);
        pts = translate(pts, origin);

        float[] vector = pts.vectorized();

        float b = Float.POSITIVE_INFINITY;
        int u = -1;
        int i = 0;

        for (UnistrokeTemplate tpl : templates) {
            float d;
            if (isUsingProtractor) {
                d = optimalCosineDistance(tpl.vectorized(), vector);
            } else {
                d = distanceAtBestAngle(pts, tpl, -angleRange, angleRange, anglePrecision);
            }

            if (d < b) {
                b = d;
                u = i;
            }
            i++;
        }

        long dt = System.currentTimeMillis() - startTime;

        return (u == -1)
                ? new UnistrokeResult("No match", 0, dt)
                : new UnistrokeResult(templates.get(u).name,
                isUsingProtractor ? 1.0f / b : 1.0f - b / halfDiagonal, dt);
    }

    private CGPointArray resample(CGPointArray points, int n) {
        CGPointArray pt = new CGPointArray();
        float increment = pt.pathLength() / (n - 1);
        float D = 0;
        CGPointArray resampledPoints = new CGPointArray();
        resampledPoints.add(points.get(0));

        for (int i = 1; i < points.size(); i++) {
            CGPoint pointIndex = points.get(i);
            CGPoint pointIndexB4 = points.get(i - 1);
            float d = pointIndexB4.distanceToPt(pointIndex);
            if (D + d >= increment) {
                float qx = pointIndexB4.x + ((increment - D) / d) * (pointIndex.x - pointIndexB4.x);
                float qy = pointIndexB4.y + ((increment - D) / d) * (pointIndex.y - pointIndexB4.y);
                CGPoint q = new CGPoint(qx, qy);
                resampledPoints.add(q);
                points.add(i, q);
                D = 0;
            } else {
                D += d;
            }
        }


        if (resampledPoints.size() == n - 1) {
            resampledPoints.add(resampledPoints.get(resampledPoints.size() - 1));
        }

        return resampledPoints;
    }

//    private float distanceAtAngle(CGPointArray points, UnistrokeTemplate template, float angle) {
//        CGPointArray rotatedPoints = rotate(points, angle);
//        return rotatedPoints.distance(template.points);
//    }

    public static float distanceAtAngle(CGPointArray points, UnistrokeTemplate template, float angle) {
        CGPointArray rotatedPoints = rotate(points, angle);
        return distanceBetweenPoints(rotatedPoints, template.points);
    }


    private static float distanceBetweenPoints(CGPointArray points1, CGPointArray points2) {
        if (points1.size() != points2.size()) {
            throw new IllegalArgumentException("Points arrays must have the same size");
        }

        float distance = 0;

        for (int i = 0; i < points1.size(); i++) {
            distance += calculateDistance(points1.get(i), points2.get(i));
        }

        return distance / points1.size();
    }

    private static CGPoint calculateCentroid(CGPointArray points) {
        float xSum = 0;
        float ySum = 0;

        for (CGPoint point : points) {
            xSum += point.x;
            ySum += point.y;
        }

        float xCentroid = xSum / points.size();
        float yCentroid = ySum / points.size();

        return new CGPoint(xCentroid, yCentroid);
    }

    private static float calculateDistance(CGPoint point1, CGPoint point2) {
        float xDist = point1.x - point2.x;
        float yDist = point1.y - point2.y;
        return (float) Math.sqrt(xDist * xDist + yDist * yDist);
    }

    private float distanceAtBestAngle(CGPointArray points, UnistrokeTemplate template,
                                      float fromAngle, float toAngle, float anglePrecision) {
        float phi = 0.5f * (-1.0f + (float) Math.sqrt(5.0)); // Golden Ratio

        float a = fromAngle;
        float b = toAngle;
        float x1 = phi * a + (1 - phi) * b;
        float f1 = distanceAtAngle(points, template, x1);
        float x2 = (1 - phi) * a + phi * b;
        float f2 = distanceAtAngle(points, template, x2);

        while (Math.abs(b - a) > anglePrecision) {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = phi * a + (1 - phi) * b;
                f1 = distanceAtAngle(points, template, x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = (1 - phi) * a + phi * b;
                f2 = distanceAtAngle(points, template, x2);
            }
        }

        return Math.min(f1, f2);
    }

    private float[] vectorized(CGPointArray points) {
        float sum = 0;
        float[] vector = new float[2 * points.size()];
        for (int i = 0; i < points.size(); i++) {
            vector[2 * i] = points.get(i).x;
            vector[2 * i + 1] = points.get(i).y;
            sum += points.get(i).x * points.get(i).x + points.get(i).y * points.get(i).y;
        }

        float magnitude = (float) Math.sqrt(sum);
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= magnitude;
        }

        return vector;
    }

    private static CGPointArray rotate(CGPointArray points, float radians) {
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);
        CGPoint centroid = points.centroid();
        CGPointArray rotatedPoints = new CGPointArray();

        for (int i = 0; i < points.size(); i++) {
            float qx = (points.get(i).x - centroid.x) * cos - (points.get(i).y - centroid.y) * sin + centroid.x;
            float qy = (points.get(i).x - centroid.x) * sin + (points.get(i).y - centroid.y) * cos + centroid.y;
            rotatedPoints.add(new CGPoint(qx, qy));
        }

        return rotatedPoints;
    }

    private CGPointArray scale(CGPointArray points, float size) {
        float width = points.boundingBox();
        CGPointArray scaledPoints = new CGPointArray();
        for (int i = 0; i < points.size(); i++) {
            float qx = points.get(i).x * (size / width);
            float qy = points.get(i).y * (size / width);
            scaledPoints.add(new CGPoint(qx, qy));
        }
        return scaledPoints;
    }

    private CGPointArray translate(CGPointArray points, CGPoint translation) {
        CGPoint centroid = points.centroid();
        CGPointArray translatedPoints = new CGPointArray();
        for (int i = 0; i < points.size(); i++) {
            float qx = points.get(i).x + translation.x - centroid.x;
            float qy = points.get(i).y + translation.y - centroid.y;
            translatedPoints.add(new CGPoint(qx, qy));
        }
        return translatedPoints;
    }

    private float optimalCosineDistance(float[] v1, float[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vector size mismatch!");
        }

        float a = 0;
        float b = 0;
        for (int i = 0; i < v1.length; i += 2) {
            a += v1[i] * v2[i] + v1[i + 1] * v2[i + 1];
            b += v1[i] * v2[i + 1] - v1[i + 1] * v2[i];
        }

        float angle = (float) Math.atan(b / a);
        return (float) Math.acos(a * Math.cos(angle) + b * Math.sin(angle));
    }
}

class UnistrokeTemplate {
    String name;
    CGPointArray points;

    UnistrokeTemplate(String name, CGPointArray pts) {
        this.name = name;
        this.points = resample(pts, UnistrokeRecognizer.numPoints);
        float radians = indicativeAngle(points);
        this.points = rotate(this.points, -radians);
        this.points = scale(this.points, UnistrokeRecognizer.squareSize);
        this.points = translate(this.points, UnistrokeRecognizer.origin);
    }

    float[] vectorized() {
        return points.vectorized();
    }

    public CGPointArray getPoints() {
        return points;
    }

    private CGPointArray resample(CGPointArray points, int n) {
        CGPointArray pt = new CGPointArray();
        float increment = pt.pathLength() / (n - 1);
        float D = 0;
        CGPointArray resampledPoints = new CGPointArray();
        resampledPoints.add(points.get(0));

        for (int i = 1; i < points.size(); i++) {
            float d = points.get(i - 1).distanceToPt(points.get(i));
            if (D + d >= increment) {
                float qx = points.get(i - 1).x + ((increment - D) / d) * (points.get(i).x - points.get(i - 1).x);
                float qy = points.get(i - 1).y + ((increment - D) / d) * (points.get(i).y - points.get(i - 1).y);
                CGPoint q = new CGPoint(qx, qy);
                resampledPoints.add(q);
                points.add(i, q);
                D = 0;
            } else {
                D += d;
            }
        }

        if (resampledPoints.size() == n - 1) {
            resampledPoints.add(resampledPoints.get(resampledPoints.size() - 1));
        }

        return resampledPoints;
    }

    private float indicativeAngle(CGPointArray points) {
        CGPoint centroid = points.centroid();
        return (float) Math.atan2(centroid.y - points.get(0).y, centroid.x - points.get(0).x);
    }

    private CGPointArray rotate(CGPointArray points, float radians) {
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);
        CGPoint centroid = points.centroid();
        CGPointArray rotatedPoints = new CGPointArray();

        for (int i = 0; i < points.size(); i++) {
            float qx = (points.get(i).x - centroid.x) * cos - (points.get(i).y - centroid.y) * sin + centroid.x;
            float qy = (points.get(i).x - centroid.x) * sin + (points.get(i).y - centroid.y) * cos + centroid.y;
            rotatedPoints.add(new CGPoint(qx, qy));
        }

        return rotatedPoints;
    }

    private CGPointArray scale(CGPointArray points, float size) {
        float width = points.boundingBox();
        CGPointArray scaledPoints = new CGPointArray();
        for (int i = 0; i < points.size(); i++) {
            float qx = points.get(i).x * (size / width);
            float qy = points.get(i).y * (size / width);
            scaledPoints.add(new CGPoint(qx, qy));
        }
        return scaledPoints;
    }

    private CGPointArray translate(CGPointArray points, CGPoint translation) {
        CGPoint centroid = points.centroid();
        CGPointArray translatedPoints = new CGPointArray();
        for (int i = 0; i < points.size(); i++) {
            float qx = points.get(i).x + translation.x - centroid.x;
            float qy = points.get(i).y + translation.y - centroid.y;
            translatedPoints.add(new CGPoint(qx, qy));
        }
        return translatedPoints;
    }
}


public class ImgRecognitionTest extends AppCompatActivity {

    private DrawingView imgRecog;
    private LinearLayout sideMenu;
    private ToggleButton btnToggleMenu;

    private double totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_recognition_test);

        imgRecog = findViewById(R.id.EchoSketchView);
        TextView guess = findViewById(R.id.shape);
        Button btnClear = findViewById(R.id.btnClear);
        TextView timer = findViewById(R.id.time);
        TextView Accuracy = findViewById(R.id.pts);
        int Rounds = 3;


    UnistrokeRecognizer recognizer = new UnistrokeRecognizer();

    // Add templates
    recognizer.addTemplate("Circle", createCirclePoints());
    recognizer.addTemplate("Triangle", createTrianglePoints());
    recognizer.addTemplate("Square", createSquarePoints());
    recognizer.addTemplate("Smile", createSmilePoints());

    // Recognize user input
    CGPointArray userInput = DrawingView.createUserInput(); // Replace with actual user input
    UnistrokeResult result = recognizer.recognize(userInput, false);

    // Output the result
    guess.setText(result.name);
    timer.setText(result.time + " milliseconds");
    Accuracy.setText(result.score + " points");

}


    private static CGPointArray createCirclePoints() {
        // Sample points for a circle
        CGPointArray circle = new CGPointArray();
        float centerX = 0;
        float centerY = 0;
        float radius = 100;
        int numPoints = 100;

        for (int i = 0; i < numPoints; i++) {
            double angle = (2 * Math.PI * i) / numPoints;
            float x = (float) (centerX + radius * Math.cos(angle));
            float y = (float) (centerY + radius * Math.sin(angle));
            circle.add(new CGPoint(x, y));
        }

        return circle;
    }

    private static CGPointArray createTrianglePoints() {
        // Sample points for a triangle
        CGPointArray triangle = new CGPointArray();
        triangle.add(new CGPoint(0, 0));
        triangle.add(new CGPoint(100, 0));
        triangle.add(new CGPoint(50, 87));

        return triangle;
    }

    private static CGPointArray createSquarePoints() {
        // Sample points for a square
        CGPointArray square = new CGPointArray();
        square.add(new CGPoint(0, 0));
        square.add(new CGPoint(100, 0));
        square.add(new CGPoint(100, 100));
        square.add(new CGPoint(0, 100));

        return square;
    }

    private static CGPointArray createSmilePoints() {
        // Sample points for a simple smile
        CGPointArray smile = new CGPointArray();
        smile.add(new CGPoint(0, 0));
        smile.add(new CGPoint(30, 10));
        smile.add(new CGPoint(60, 0));
        smile.add(new CGPoint(30, -10));

        return smile;
    }


}
