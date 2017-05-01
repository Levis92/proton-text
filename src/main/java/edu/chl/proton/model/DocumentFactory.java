package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
public class DocumentFactory {
    //use getShape method to get object of type shape
    private File file; //fileName maybe is more tydlig?
    String documentType;

    private DocumentFactory(String documentType, File file){
        this.documentType= documentType;
        this.file=file;

        // kolla om file redan finns, annars typ new file
    }

    //TODO: Checker for existing document file, if exist -> send file to documentClass (oklart om vi verkligen ska ha detta)
    private void checkExistingFile(){

    }

    public Document getDocument(String documentType){
        if(documentType == null){
            return null;
        }
        //TODO: Create classes Plain and so on
        if(documentType.equalsIgnoreCase("PLAIN")){
            //return new PlainDocument(file); // Start with nothing

        } else if(documentType.equalsIgnoreCase("MARKDOWN")){
            //return new Markdown(file); // Cursor cursor, File file, List<Parts> parts, List<String> lines

        } else if(documentType.equalsIgnoreCase("SLIDE")){
            //return new SlideDocument(file); // Mode where you can do some notes on each slide (MAIN POINT, DETAILS, PICTURE)

        } else if(documentType.equalsIgnoreCase("ASSIGNMENT")){
            //return new AssignmentDocument(file); // Template for assaignments, i.e. front page and subsections for each assignment.
        }

        return null;
    }
}
