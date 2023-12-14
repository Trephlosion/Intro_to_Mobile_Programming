package com.example.drawingtest;

public class Gesture {
    public pt[] pts = null;            // gesture points (normalized)
    public String Name = "";                 // gesture class

    private static final int SAMPLING_RESOLUTION = 32;                             // default number of points on the gesture path
    private static final int MAX_INT_COORDINATES = 1024;                           // $Q only: each point has two additional x and y integer coordinates in the interval [0..MAX_INT_COORDINATES-1] used to operate the LUT table efficiently (O(1))
    public static int LUT_SIZE = 64;                                        // $Q only: the default size of the lookup table is 64 x 64
    public static int LUT_SCALE_FACTOR = MAX_INT_COORDINATES / LUT_SIZE;    // $Q only: scale factor to convert between integer x and y coordinates and the size of the LUT

    public int[][] LUT = null;               // lookup table

    /**
     * Constructs a gesture from an array of points
     *
     * @param points
     * @param gestureName
     */
    public Gesture(pt[] points, String gestureName) {
        this(points, gestureName, true);
    }

    /**
     * Constructs a gesture from an array of points
     *
     * @param points
     */
    public Gesture(pt[] points) {
        this(points, "", true);
    }


    /**
     * Constructs a gesture from an array of points
     *
     * @param points
     * @param gestureName
     * @param constructLUT
     */
    public Gesture(pt[] points, String gestureName, boolean constructLUT) {
        this.Name = gestureName;

        // normalizes the array of points with respect to scale, origin, and number of points
        this.pts = Scale(points);
        this.pts = TranslateTo(pts, Centroid(pts));
        this.pts = Resample(pts, SAMPLING_RESOLUTION);

        if (constructLUT) {
            // constructs a lookup table for fast lower bounding (used by $Q)
            this.TransformCoordinatesToIntegers();
            this.ConstructLUT();
        }
    }

    /**
     * Constructs a gesture from an array of points
     *
     * @param points
     * @param constructLUT
     */
    public Gesture(pt[] points, boolean constructLUT) {
        this(points, "", constructLUT);
    }

    //region gesture pre-processing steps: scale normalization, translation to origin, and resampling

    /**
     * Performs scale normalization with shape preservation into [0..1]x[0..1]
     *
     * @param points
     * @return
     */
    private pt[] Scale(pt[] points) {
        float minx = Float.MAX_VALUE, miny = Float.MAX_VALUE, maxx = -Float.MAX_VALUE, maxy = -Float.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            if (minx > points[i].X) minx = points[i].X;
            if (miny > points[i].Y) miny = points[i].Y;
            if (maxx < points[i].X) maxx = points[i].X;
            if (maxy < points[i].Y) maxy = points[i].Y;
        }

        pt[] newpts = new pt[points.length];
        float scale = Math.max(maxx - minx, maxy - miny);
        for (int i = 0; i < points.length; i++)
            newpts[i] = new pt((points[i].X - minx) / scale, (points[i].Y - miny) / scale, points[i].StrokeID);
        return newpts;
    }

    /**
     * Translates the array of points by p
     *
     * @param points
     * @param p
     * @return
     */
    private pt[] TranslateTo(pt[] points, pt p) {
        pt[] newpts = new pt[points.length];
        for (int i = 0; i < points.length; i++)
            newpts[i] = new pt(points[i].X - p.X, points[i].Y - p.Y, points[i].StrokeID);
        return newpts;
    }

    /**
     * Computes the centroid for an array of points
     *
     * @param points
     * @return
     */
    private pt Centroid(pt[] points) {
        float cx = 0, cy = 0;
        for (int i = 0; i < points.length; i++) {
            cx += points[i].X;
            cy += points[i].Y;
        }
        return new pt(cx / points.length, cy / points.length, 0);
    }

    /**
     * Resamples the array of points into n equally-distanced points
     *
     * @param points
     * @param n
     * @return
     */
    public pt[] Resample(pt[] points, int n) {
        pt[] newpts = new pt[n];
        newpts[0] = new pt(points[0].X, points[0].Y, points[0].StrokeID);
        int numpts = 1;

        float I = PathLength(points) / (n - 1); // computes interval length
        float D = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].StrokeID == points[i - 1].StrokeID) {
                float d = Geometry.EuclideanDistance(points[i - 1], points[i]);
                if (D + d >= I) {
                    pt firstpt = points[i - 1];
                    while (D + d >= I) {
                        // add interpolated point
                        float t = Math.min(Math.max((I - D) / d, 0.0f), 1.0f);
                        if (Float.isNaN(t)) t = 0.5f;
                        newpts[numpts++] = new pt(
                                (1.0f - t) * firstpt.X + t * points[i].X,
                                (1.0f - t) * firstpt.Y + t * points[i].Y,
                                points[i].StrokeID
                        );

                        // update partial length
                        d = D + d - I;
                        D = 0;
                        firstpt = newpts[numpts - 1];
                    }
                    D = d;
                } else D += d;
            }
        }

        if (numpts == n - 1) // sometimes we fall a rounding-error short of adding the last point, so add it if so
            newpts[numpts++] = new pt(points[points.length - 1].X, points[points.length - 1].Y, points[points.length - 1].StrokeID);
        return newpts;
    }

    /**
     * Computes the path length for an array of points
     *
     * @param points
     * @return
     */
    private float PathLength(pt[] points) {
        float length = 0;
        for (int i = 1; i < points.length; i++)
            if (points[i].StrokeID == points[i - 1].StrokeID)
                length += Geometry.EuclideanDistance(points[i - 1], points[i]);
        return length;
    }

    /**
     * Scales point coordinates to the integer domain [0..MAXINT-1] x [0..MAXINT-1]
     */
    private void TransformCoordinatesToIntegers() {
        for (int i = 0; i < pts.length; i++) {
            pts[i].intX = (int) ((pts[i].X + 1.0f) / 2.0f * (MAX_INT_COORDINATES - 1));
            pts[i].intY = (int) ((pts[i].Y + 1.0f) / 2.0f * (MAX_INT_COORDINATES - 1));
        }
    }

    /**
     * Constructs a Lookup Table that maps grip points to the closest point from the gesture path
     */
    private void ConstructLUT() {
        this.LUT = new int[LUT_SIZE][];
        for (int i = 0; i < LUT_SIZE; i++)
            LUT[i] = new int[LUT_SIZE];

        for (int i = 0; i < LUT_SIZE; i++)
            for (int j = 0; j < LUT_SIZE; j++) {
                int minDistance = Integer.MAX_VALUE;
                int indexMin = -1;
                for (int t = 0; t < pts.length; t++) {
                    int row = pts[t].intY / LUT_SCALE_FACTOR;
                    int col = pts[t].intX / LUT_SCALE_FACTOR;
                    int dist = (row - i) * (row - i) + (col - j) * (col - j);
                    if (dist < minDistance) {
                        minDistance = dist;
                        indexMin = t;
                    }
                }
                LUT[i][j] = indexMin;
            }
    }

    //endregion
}
