package com.example.drawingtest;

public class Geometry {

        /**
         * Computes the Squared Euclidean Distance between two points in 2D
         */


    /**
     * Computes the Euclidean Distance between two points in 2D
     */
    public static float EuclideanDistance(pt a, pt b) {
        return (float) Math.sqrt(SqrEuclideanDistance(a, b));
    }


    public static float SqrEuclideanDistance(pt a, pt b) {
        float dx = a.X - b.X;
        float dy = a.Y - b.Y;
        return dx * dx + dy * dy;
    }
}
