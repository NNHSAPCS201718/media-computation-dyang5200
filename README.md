Unit 6: Media Computation
==============

*Standard 6 - Synthesize decisions, loops, and arrays and apply to computational media projects*

Collage Lab

# Introduction

The goal of this lab is for you to apply creatively the filters and transformations that you have developed throughout this unit.

# Requirements

* collage contains at least 4 copies of the image

* the 4 copies includes the original image and at least 3 modifications of the original image

    * you may find it convenient to start with a large, blank picturePicture canvas = new Picture(height, width);

    * you must invoke the cropAndCopy method that you wrote in the programming practice activity

* modifications must include one or more filters (changing colors) and one or more transformations (scaling, cropping, mirroring)

* the collage must be saved as an image file, by default it will be saved in the images folder:

    * finalPic.write("YourName.jpg");

    * this code doesn't work for some students for unknown reasons. You may need to specify an absolute path. For example:

        * finalPic.write("C:\\Users\\gschmit\\Desktop\\GitHub\\media-computation-gcschmit\\images\\Schmit.jpg");

# Extensions

* Add more copies.

* Add more filters

    * sepia:

        * convert to grayscale

        * if red < 60 then reduce all three components to 90% of their original value

        * else if red < 190 then reduce just blue to 80% of its original value

        * else reduce just blue to 90% of its original value

    * posterize

        * set all color component values in a range to one value (the midpoint of the range)

        * try with four ranges for each color (0-63, 64-127, 128-191, 192-255)

    * customizable posterize

        * posterize with parameter that specifies how many bins to create (try 8 colors)

    * edge detection

        * calculate the average color for the current pixel and the pixel at the same x but y+1 (Pixel has a getAverage method)

        * get the distance between the two averages

        * if the absolute value of the distance is greater than some value, then turn the current pixel black (Pixel has a colorDistance method) otherwise, turn the current pixel white

    * edge detection 2

        * edge detection but with calculation based on pixel to the right

    * pixelate

    * [geometric primitives](https://github.com/fogleman/primitive/blob/master/README.md) (warning: this is crazy hard but looks awesome)

* Add more transformations (e.g., rotation)

* Add more awesome.

# Submission

* Submit a link to your GitHub repository with this assignment.

* Please specify in your submission comment which methods I should call to create your collage.

