package lyx.component.skinny;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

public final class ExtractItemsStandardCallback {

  private static final Logger LOG = Logger.getLogger(ExtractItemsStandardCallback.class.getName());
  private static final String READ = "r";
  private FileOutputStream output;
  private ISequentialOutStream out;
  private IArchiveExtractCallback callback;

  public ExtractItemsStandardCallback() {
    out = data -> {
      try {
        if (output != null) {
          output.write(data);
        }
      } catch (IOException e) {
        throw new SevenZipException(e);
      }

      return data.length;
    };

    callback = new IArchiveExtractCallback() {
      @Override
      public void setTotal(long total) {
      }

      @Override
      public void setCompleted(long complete) {
      }

      @Override
      public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) {
        return out;
      }

      @Override
      public void prepareOperation(ExtractAskMode extractAskMode) {
      }

      @Override
      public void setOperationResult(ExtractOperationResult extractOperationResult) {
      }
    };

  }

  public List list(File file) {
    if (file == null || !file.canRead()) {
      return null;
    }
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(file, READ);
      IInArchive inArchive = SevenZip.openInArchive(null,
          new RandomAccessFileInStream(randomAccessFile));

      int count = inArchive.getNumberOfItems();
      final List ret = new ArrayList<>(count);
      for (int i = 0; i < count; i++) {
        String path = (String) inArchive.getProperty(i, PropID.PATH);
        Boolean isFolder = (Boolean) inArchive.getProperty(i, PropID.IS_FOLDER);

        String fullPathNoEndSeparator = FilenameUtils.getName(path);

        if (isFolder) {
          continue;
        }
        ret.add(fullPathNoEndSeparator);
      }
      inArchive.close();
      return ret;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void extract(final File file, File targetFile, final boolean ignoreFolder) {
    if (file == null || !file.canRead()) {
      LOG.info("Usage: java ExtractItemsStandard <arch-name>");
      return;
    }

    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(file, READ);
      IInArchive inArchive = SevenZip.openInArchive(null,
          new RandomAccessFileInStream(randomAccessFile));

      int count = inArchive.getNumberOfItems();

      for (int i = 0; i < count; i++) {
        String path = (String) inArchive.getProperty(i, PropID.PATH);
        Boolean isFolder = (Boolean) inArchive.getProperty(i, PropID.IS_FOLDER);

        String fullPathNoEndSeparator = FilenameUtils.getFullPathNoEndSeparator(path);
        File f = new File(targetFile + "/" + fullPathNoEndSeparator);

        if (!f.isDirectory() && !f.mkdirs()) {
          throw new IOException("failed to create directory " + f.getName());
        }

        if (isFolder) {
          continue;
        }

        output = new FileOutputStream(targetFile + "/" + path);
        inArchive.extract(new int[]{i}, false, callback);
      }
      output.flush();
      output.close();
      inArchive.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}