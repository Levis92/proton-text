package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
public class DocumentFactory {
    private FileUtility file;

    public DocumentFactory(){
    }

    /**
     * Creates a document to write in.
     * @param documentType decides what type of document will be created, and what rules that document has.
     * @return Document with writing rules.
     */
    public Document createDocument(DocumentType documentType){
        if(documentType == null){
            return null;
        }
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

    /**
     * Opens the existing document, if document does not exist, creates one.
     * @param fileName is the file path to the file.
     * @return a document, and a new if it doesn't already exist.
     */
    public Document getDocument(String fileName){
        try {
            file = new FileUtility(fileName);
           if (fileName.toLowerCase().substring(fileName.length()-3).equals(".md")
                    && (fileName.toLowerCase().contains("slide"))) {
                IDoc slide = new Markdown();
                return new Document(slide, file);
            } else if(fileName.substring(fileName.length()-3).equals(".md")) {
               IDoc markdown = new Markdown();
             return new Document(markdown, file);
            } else {
               IDoc plain = new Plain();
                return new Document(plain,file);
            }

        } catch (NullPointerException eNull) { //if no document exists
            if (fileName.toLowerCase().substring(fileName.length()-3).equals(".md")
                    && (fileName.toLowerCase().contains("slide"))) {
                return createDocument(DocumentType.SLIDE);
            } else if(fileName.substring(fileName.length()-3).equals(".md") ||
                    fileName.substring(fileName.length()-3).equals(".txt")) {
                return createDocument(DocumentType.MARKDOWN);
            } else {
                return createDocument(DocumentType.PLAIN);
            }
        }

    }

}
