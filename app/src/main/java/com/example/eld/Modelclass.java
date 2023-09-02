package com.example.eld;

public class Modelclass {
    String heading,discription;
   int image;

    public Modelclass(String heading, String discription, int image) {
        this.heading = heading;
        this.discription = discription;
        this.image = image;
    }
    public Modelclass() {
    }
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
