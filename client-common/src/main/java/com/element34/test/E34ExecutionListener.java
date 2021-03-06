package com.element34.test;

import static com.google.common.base.Preconditions.checkNotNull;

import com.element34.E34Settings;
import com.element34.stream.JettyMonitor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class E34ExecutionListener {


  private final JettyMonitor monitor = new JettyMonitor();

  public void onStart() {
    if (E34Settings.ENABLE_LIVE) {
      monitor.start();
    }
  }

  public void unzipReport(File destination) {
    E34TestListener.DEST = new File(destination, E34Settings.DATA).getAbsolutePath();
    File index = new File(destination.getAbsolutePath(), "/e34report.html");
    JettyMonitor.SERVER_LIVE_REPORT = "file:///" + index.getAbsolutePath();
    System.out.println("com.element34.testE34.E34ExecutionListener " + index.getAbsolutePath());
    try {
      extract(destination);
      // replace settings :
      String content = new String(Files.readAllBytes(Paths.get(index.getAbsolutePath())));
      int port = E34Settings.E34_PORT;
      boolean live = E34Settings.ENABLE_LIVE;
      content = content.replaceFirst("var e34_port = 8080;", "var e34_port = " + port + ";");
      content = content.replaceFirst("var e34_live = true;", "var e34_live = " + live + ";");
      Files.write(Paths.get(index.getAbsolutePath()), content.getBytes(), StandardOpenOption.WRITE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    if (E34Settings.ENABLE_LIVE) {
      monitor.stop();
    }
  }


  private void extract(File dest) throws IOException {
    checkNotNull(dest);
    File report = new File(dest, "e34report.html");
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
