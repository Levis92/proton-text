package edu.chl.proton.model;

/**
 * Created by ludvig on 2017-05-01.
 */
public class DocumentFactory {
    //use getShape method to get object of type shape
    public Document getDocument(String documentType){
        if(documentType == null){
            return null;
        }
        //TODO: Create classes Plain and so on
        if(documentType.equalsIgnoreCase("PLAIN")){
            return new PlainDocument(); // Start with nothing

        } else if(documentType.equalsIgnoreCase("SLIDE")){
            return new SlideDocument(); // Mode where you can do some notes on each slide (MAIN POINT, DETAILS, PICTURE)

        } else if(documentType.equalsIgnoreCase("ASSIGNMENT")){
            return new AssignmentDocument(); // Template for assaignments, i.e. front page and subsections for each assignment.
        }

        return null;
    }
}
