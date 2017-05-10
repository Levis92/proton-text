package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
public class DocumentFactory {
    //use getShape method to get object of type shape
    private File file; //fileName maybe is more tydlig?


    public DocumentFactory(){
        // kolla om file redan finns, annars typ new file
    }

    //TODO: Checker for existing document file, if exist -> send file to documentClass (oklart om vi verkligen ska ha detta)
    private void checkExistingFile(){

    }

    public Document createDocument(DocumentType documentType){
        if(documentType == null){
            return null;
        }
        //TODO: Create classes Plain and so on
        if(documentType==DocumentType.PLAIN){
            //return new PlainDocument(file); // Start with nothing

        } if(documentType==DocumentType.MARKDOWN){
            DocTypeInterface markdown = new Markdown();
            return new Document(markdown);
        } else if(documentType==DocumentType.SLIDE){
            //return new SlideDocument(file); // Mode where you can do some notes on each slide (MAIN POINT, DETAILS, PICTURE)

        } else if(documentType==DocumentType.ASSIGNMENT){
            //return new AssignmentDocument(file); // Template for assaignments, i.e. front page and subsections for each assignment.
        }
        return null;
    }

    // if no document exists, create one. then send it
    public Document getDocument(String filePath){
        //check filepath, if md, create markdown

        return createDocument(DocumentType.MARKDOWN);
    }

}
