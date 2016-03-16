/*

import java.io.*;
import org.apache.lucene.index.Term;



import org.apache.log4j.Logger;


/**
 * Classe utilisant des threads pour mettre � jour des documents dans l'index.
 * <br/><br/>
 * Class using threads for updating documents in index.
 *
 * @author Rida Benjelloun (ridabenjelloun@gmail.com)
 */
public class ThreadIndexUpdating
    extends Thread {
   static Logger logger = Logger.getRootLogger();

  private String dir = "";
  private String field = "";
  private String content = "";
  private String fileToIndex = "";
  private String config = "";
  private Term term = null;
  private boolean populated = false;
  private boolean populatedWithTerm = false;

  public ThreadIndexUpdating(String dir, String field, String content,
                             String fileToIndex, String config) {
    this.dir = dir;
    this.field = field;
    this.content = content;
    this.fileToIndex = fileToIndex;
    this.config = config;
    populated = true;
  }

  public ThreadIndexUpdating(String dir, Term term, String fileToIndex,
                             String config) {
    this.dir = dir;
    this.term = term;
    this.fileToIndex = fileToIndex;
    this.config = config;
    boolean populatedWithTerm = true;
  }

  public void run() {
    if (populated == true) {

      try {
        LuceneActions.getSingletonInstance().updateDoc(dir, field, content,
            fileToIndex, config);
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
          logger.error(ex.getMessage());
        }

      }
      catch (LiusException e) {
        logger.error(e.getMessage());
      }
      catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
    else if (populatedWithTerm == true) {

      try {
        LuceneActions.getSingletonInstance().updateDoc(dir, term, fileToIndex,
            config);
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
          logger.error(ex.getMessage());
        }

      }
      catch (LiusException e) {
        logger.error(e.getMessage());
      }
      catch (IOException e) {
        logger.error(e.getMessage());
      }

    }

  }

}