package panko;


import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import panko.ui.MainFrame;

public class SqliteClient
{
  private static final Logger log = LogManager.getLogger(SqliteClient.class);

  public static void main(String[] args)
  {
    log.debug("Starting application ...");
    MainFrame mainFrame = new MainFrame();

    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        mainFrame.initUI();
      }
    });
  }
}
