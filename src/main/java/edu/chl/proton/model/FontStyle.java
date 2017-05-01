package edu.chl.proton.model;

/**
 * Created by ludvig on 2017-05-01.
 */
public class FontStyle {


    protected void setFont(String fontType){
        if(fontType == null){

        }
        //TODO: Make fonts
        if(fontType.equalsIgnoreCase("SANS")){
            return; //set SANS

        } else if(fontType.equalsIgnoreCase("SLIDE")){
            return;

        } else (fontType.equalsIgnoreCase("ASSIGNMENT")){
            return; // default
        }
    }

    protected void setPosture(Enum postureType){


    }


    /*
- String font
- Enum posture
- int size
    ~ setFont(String): void
~ setPosture(Enum): void
~ setSize(int): void
~ getFont(): String
~ getPosture(): Enum
~ getSize(): int
*/


}
