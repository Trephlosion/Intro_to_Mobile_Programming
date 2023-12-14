package com.example.drawingtest;

/**
 * The $ gesture recognizers (Java version)
 * <p>
 * Copyright (c) 2018, Mattia De Rosa. All rights reserved.
 * <p>
 * based on the $Q Super-Quick Recognizer (C# version) found at
 * http://depts.washington.edu/madlab/proj/dollar/qdollar.html
 * whose original header follows:
 * <p>
 * The $P+ Point-Cloud Recognizer (.NET Framework C# version)
 * <p>
 * Radu-Daniel Vatavu, Ph.D.
 * University Stefan cel Mare of Suceava
 * Suceava 720229, Romania
 * radu.vatavu@usm.ro
 * <p>
 * The academic publication for the $P+ recognizer, and what should be
 * used to cite it, is:
 * <p>
 * Vatavu, R.-D. (2017). Improving Gesture Recognition Accuracy on Touch Screens for Users with Low Vision.
 * In Proceedings of CHI '17, the 35th ACM Conference on Human Factors in Computing Systems (Denver, Colorado, USA, May 2017).
 * New York: ACM Press. http://dx.doi.org/10.1145/3025453.3025941
 * <p>
 * This software is distributed under the "New BSD License" agreement:
 * <p>
 * Copyright (c) 2017, Radu-Daniel Vatavu. All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the University Stefan cel Mare of Suceava,
 * nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Radu-Daniel Vatavu BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **/
public class pt {
    public float X, Y;       // point coordinates
    public int StrokeID;     // the stroke index to which this point belongs
    public int intX, intY;   // integer coordinates for LUT indexing (used by $Q's lower bounding optimization; see QptCloudRecognizer.cs)

    public pt(float x, float y, int strokeId) {
        this.X = x;
        this.Y = y;
        this.StrokeID = strokeId;
        this.intX = 0;
        this.intY = 0;
    }
}
