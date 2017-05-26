package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
public class DocumentFactory {
    //use getShape method to get object of type shape
    private FileUtility file; //fileName maybe is more tydlig?


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
            IDoc plain = new Plain();
            return new Document(plain);

        } else if(documentType==DocumentType.MARKDOWN){
            IDoc markdown = new Markdown();
            return new Document(markdown);
        } else if(documentType==DocumentType.SLIDE){
            IDoc slide = new Markdown(); //TODO: Implement template for slide view, maybe that we create a button for new Slide?
            return new Document(slide);
        } else if(documentType==DocumentType.ASSIGNMENT){
            //return new AssignmentDocument(file); // Template for assaignments, i.e. front page and subsections for each assignment.
        }
        return null;
    }

    // if no document exists, create one. then send it
    public Document getDocument(String string){
        //check filepath, if md, create markdown

        try {
            FileUtility docFile = new FileUtility(string);
           if (string.toLowerCase().substring(string.length()-3).equals(".md")
                    && (string.toLowerCase().contains("slide"))) {
                IDoc slide = new Markdown();
                return new Document(slide, docFile);
            } else if(string.substring(string.length()-3).equals(".md")) {
               IDoc markdown = new Markdown();
             return new Document(markdown, docFile);
            } else {
               IDoc plain = new Plain();
                return new Document(plain,docFile);
            }

        } catch (NullPointerException eNull) { //if no document exists
            if (string.toLowerCase().substring(string.length()-3).equals(".md")
                    && (string.toLowerCase().contains("slide"))) {
                return createDocument(DocumentType.SLIDE);
            } else if(string.substring(string.length()-3).equals(".md")) {
                return createDocument(DocumentType.MARKDOWN);
            } else {
                return createDocument(DocumentType.PLAIN);
            }
        }

    }

}
