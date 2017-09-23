package com.element34.test;

import static com.google.common.base.Preconditions.checkNotNull;

import com.element34.Hardcoded;
import com.element34.stream.JettyMonitor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class E34ExecutionListener {


  private final JettyMonitor monitor = new JettyMonitor();

  public void onStart() {

    // ReportSink.addListener((event) -> logger.info(event.toString()));

    if (!Hardcoded.DISABLE_LIVE) {
      monitor.start();
    }

  }

  public void unzipReport(File destination) {
    Hardcoded.DEST = new File(destination, Hardcoded.DATA).getAbsolutePath();
    try {
      extract(destination);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    if (!Hardcoded.DISABLE_LIVE) {
      monitor.stop();
    }
  }


  private void extract(File dest) throws IOException {
    checkNotNull(dest);
    File report = new File(dest, "e34report.html");
    if (report.exists()) {
      return;
    }
    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("e34_report_fe.zip");
    ZipInputStream zis = new ZipInputStream(stream);
    ZipEntry ze = zis.getNextEntry();
    byte[] buffer = new byte[2048];

    while (ze != null) {
      String fileName = ze.getName();
      if (ze.isDirectory()) {
        ze = zis.getNextEntry();
        continue;
      }
      fileName = fileName.replace("dist/", "");

      File newFile = new File(dest + File.separator + fileName);

      //create all non exists folders
      //else you will hit FileNotFoundException for compressed folder
      new File(newFile.getParent()).mkdirs();

      FileOutputStream fos = new FileOutputStream(newFile);

      int len;
      while ((len = zis.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }

      fos.close();
      ze = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();
  }


}
