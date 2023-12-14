package com.example.drawingtest;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import java.util.Date;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Stack;

public class PDollarPlus {
    /**
     * Implements the $P+ recognizer
     */

        /**
         * Main function of the $P+ recognizer.
         * Classifies a candidate gesture against a set of training samples.
         * Returns the class of the closest neighbor in the template set.
         *
         * @param candidate
         * @param trainingSet
         * @return
         */
        public static String Classify(Gesture candidate, Gesture[] trainingSet)
        {
            float minDistance = Float.MAX_VALUE;
            String gestureClass = "";
            for (Gesture template : trainingSet)
            {
                float dist = GreedyCloudMatch(candidate.pts, template.pts);
                if (dist < minDistance)
                {
                    minDistance = dist;
                    gestureClass = template.Name;
                }
            }
            return gestureClass;
        }

        /**
         * Compare two gestures.
         */
        public static float GreedyCloudMatch(Gesture gesture1, Gesture gesture2)
        {
            return GreedyCloudMatch(gesture1.pts, gesture2.pts);
        }

        /**
         * Implements greedy search for a minimum-distance matching between two point clouds
         * using local shape descriptors (theta turning angles).
         */
        private static float GreedyCloudMatch(pt[] points1, pt[] points2)
        {
            float[] theta1 = ComputeLocalShapeDescriptors(points1);    // should be pre-processed in the Gesture class
            float[] theta2 = ComputeLocalShapeDescriptors(points2);    // should be pre-processed in the Gesture class
            return Math.min(
                    CloudDistance(points1, theta1, points2, theta2),
                    CloudDistance(points2, theta2, points1, theta1)
            );
        }

        /**
         * Computes the distance between two point clouds
         * using local shape descriptors (theta turning angles).
         */
        private static float CloudDistance(pt[] points1, float[] theta1, pt[] points2, float[] theta2)
        {
            boolean[] matched = new boolean[points2.length];
            //java.util.Arrays.fill(matched, 0, points2.length, false);

            float sum = 0; // computes the cost of the cloud alignment
            int[] index = new int[1];

            // match points1 to points2
            for (int i = 0; i < points1.length; i++)
            {
                sum += GetClosestptFromCloud(points1[i], theta1[i], points2, theta2, index);
                matched[index[0]] = true;
            }

            // match points2 to points1
            for (int i = 0; i < points2.length; i++)
                if (!matched[i])
                    sum += GetClosestptFromCloud(points2[i], theta2[i], points1, theta1, index);

            return sum;
        }


        /**
         * Searches for the point from point-cloud cloud that is closest to point p.
         */
        private static float GetClosestptFromCloud(pt p, float theta, pt[] cloud, float[] thetaCloud, int[] indexMin)
        {
            float min = Float.MAX_VALUE;
            indexMin[0] = -1;
            for (int i = 0; i < cloud.length; i++)
            {
                float dist = (float)Math.sqrt(Geometry.SqrEuclideanDistance(p, cloud[i]) + (theta - thetaCloud[i]) * (theta - thetaCloud[i]));
                if (dist < min)
                {
                    min = dist;
                    indexMin[0] = i;
                }
            }
            return min;
        }

        /**
         * Computes local shape descriptors (theta turning angles) at each point on the gesture path.
         */
        public static float[] ComputeLocalShapeDescriptors(pt[] points)
        {
            int n = points.length;
            float[] theta = new float[n];

            theta[0] = theta[n - 1] = 0;
            for (int i = 1; i < n - 1; i++)
                theta[i] = (float)(ShortAngle(points[i - 1], points[i], points[i + 1]) / Math.PI);
            return theta;
        }

        /**
         * Computes the smallest turning angle between vectors (a,b) and (b,c) in radians in the interval [0..PI].
         */
        public static float ShortAngle(pt a, pt b, pt c)
        {
            // compute path lengths for vectors (a,b) and (b,c)
            float length_ab = Geometry.EuclideanDistance(a, b);
            float length_bc = Geometry.EuclideanDistance(b, c);
            if (Math.abs(length_ab * length_bc) <= Float.MIN_VALUE)
                return 0;

            // compute cosine of the angle between vectors (a,b) and (b,c)
            double cos_angle = ((b.X - a.X) * (c.X - b.X) + (b.Y - a.Y) * (c.Y - b.Y)) / (length_ab * length_bc);

            // deal with special cases near limits of the [-1,1] interval
            if (cos_angle <= -1.0) return (float)Math.PI;
            if (cos_angle >= 1.0) return 0.0f;

            // return the angle between vectors (a,b) and (b,c) in the interval [0,PI]
            return (float)Math.acos(cos_angle);
        }
    }
