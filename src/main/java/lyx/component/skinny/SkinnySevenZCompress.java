/**
 * MIT License
 *
 * <p>Copyright (c) 2021 mixmicro
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lyx.component.skinny;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

/**
 * {@link SkinnySevenZCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/14
 */
@Injection(name = "7z")
public class SkinnySevenZCompress extends SkinnyParallelCompress {

  private static final String SEVENZ_SUFFIX = ".7z";

  public SkinnySevenZCompress() {

  }

  @Override
  public boolean compress(File[] sourceFiles, String file, String fileName, boolean isDeleteSourceFile) {
    return compress(sourceFiles, new File(file, fileName), isDeleteSourceFile);
  }

  @Override
  public boolean compress(File[] sourceFiles, File file, boolean isDeleteSourceFile) {
    InputStream inputStream = null;
    SevenZOutputFile sevenZOutputFile = null;

    if (!file.getName().endsWith(SEVENZ_SUFFIX)) {
      throw new IllegalArgumentException("Suffix name error, your input filename is: " + file.getName());
    }

    if (sourceFiles == null || sourceFiles.length <= 0) {
      return false;
    }

    final Date accessDate = new Date();
    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR, -1);
    final Date creationDate = cal.getTime();

    try {
      sevenZOutputFile = new SevenZOutputFile(file);
      for (File sourceFile : sourceFiles) {
        SevenZArchiveEntry entry = new SevenZArchiveEntry();
        entry.setName(sourceFile.getName());
        entry.setAccessDate(accessDate);
        entry.setCreationDate(creationDate);
        sevenZOutputFile.putArchiveEntry(entry);

        inputStream = new FileInputStream(sourceFile);
        byte[] buffer = new byte[super.getContext().getOutputSize()];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          sevenZOutputFile.write(buffer, 0, length);
        }
        sevenZOutputFile.closeArchiveEntry();
      }

      sevenZOutputFile.finish();

      if (isDeleteSourceFile) {
        for (File sourceFile : sourceFiles) {
          sourceFile.deleteOnExit();
        }
      }


    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (null != inputStream) {
          inputStream.close();
        }
        if (null != sevenZOutputFile) {
          sevenZOutputFile.close();
        }
      } catch (IOException ie) {
        ie.printStackTrace();
      }
      return true;
    }
  }

  @Override
  public boolean decompress(File file, String targetDir) {
    return decompress(file, new File(targetDir));
  }

  @Override
  public boolean decompress(File file, File targetDir) {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    ArchiveEntry archiveEntry;
    SevenZFile sevenZFile = null;
    try {
      sevenZFile = new SevenZFile(file);
      while (null != (archiveEntry = sevenZFile.getNextEntry())) {
        if (!targetDir.isDirectory() && !targetDir.mkdirs()) {
          throw new IOException("failed to create directory " + targetDir);
        }

        String archiveEntryFileName = archiveEntry.getName();
        File entryFile = new File(targetDir, archiveEntryFileName);
        byte[] buffer = new byte[super.getContext().getOutputSize()];
        outputStream = new FileOutputStream(entryFile);
        int length;
        while ((length = sevenZFile.read(buffer)) != -1) {
          outputStream.write(buffer, 0, length);
        }
      }
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (null != outputStream) {
          outputStream.close();
        }
        if (null != sevenZFile) {
          sevenZFile.close();
        }
        if (null != inputStream) {
          inputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }
}
