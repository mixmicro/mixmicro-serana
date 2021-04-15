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
package lyx.component.skinny.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import lyx.component.skinny.Injection;
import lyx.component.skinny.SkinnyParallelCompress;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

/**
 * {@link SkinnyTarCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/15
 */
@Injection(name = "Tar")
public class SkinnyTarCompress extends SkinnyParallelCompress {

  private static final String TAR_SUFFIX = ".tar";

  @Override
  public boolean compress(File[] sourceFiles, String filePath, String fileName, boolean isDeleteSourceFile) {
    return compress(sourceFiles, new File(fileName, fileName), isDeleteSourceFile);
  }

  @Override
  public boolean compress(File[] sourceFiles, File file, boolean isDeleteSourceFile) {
    InputStream inputStream = null;
    ArchiveOutputStream archiveOutputStream = null;
    OutputStream fos = null;

    if (!file.getName().endsWith(TAR_SUFFIX)) {
      throw new IllegalArgumentException("Suffix name error, your input filename is: " + file.getName());
    }

    if (sourceFiles == null || sourceFiles.length <= 0) {
      return false;
    }

    try {
      fos = Files.newOutputStream(file.toPath());
      archiveOutputStream = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR, fos);
      for (File sourceFile : sourceFiles) {

        final TarArchiveEntry sEntry = new TarArchiveEntry(sourceFile, sourceFile.getName());
        archiveOutputStream.putArchiveEntry(sEntry);
        inputStream = new FileInputStream(sourceFile);
        byte[] buffer = new byte[super.getContext().getOutputSize()];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          archiveOutputStream.write(buffer, 0, length);
        }
      }
      archiveOutputStream.closeArchiveEntry();
      archiveOutputStream.finish();

      if (isDeleteSourceFile) {
        for (File sourceFile : sourceFiles) {
          sourceFile.deleteOnExit();
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (null != inputStream) {
          inputStream.close();
        }
        if (null != fos) {
          fos.close();
        }
        if (null != archiveOutputStream) {
          archiveOutputStream.close();
        }
      } catch (IOException ie) {
        ie.printStackTrace();
      }
    }
    return true;
  }

  @Override
  public boolean decompress(File file, String targetDir) {
    return decompress(file, new File(targetDir));
  }

  @Override
  public boolean decompress(File file, File targetDir) {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    TarArchiveInputStream tarArchiveInputStream = null;
    ArchiveEntry archiveEntry;
    try {
      inputStream = new FileInputStream(file);
      tarArchiveInputStream = new TarArchiveInputStream(inputStream);
      while (null != (archiveEntry = tarArchiveInputStream.getNextEntry())) {
        String archiveEntryFileName = archiveEntry.getName();

        if (!targetDir.isDirectory() && !targetDir.mkdirs()) {
          throw new IOException("failed to create directory " + targetDir);
        }

        File entryFile = new File(targetDir, archiveEntryFileName);
        byte[] buffer = new byte[super.getContext().getOutputSize()];
        outputStream = new FileOutputStream(entryFile);
        int length;
        while ((length = tarArchiveInputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (null != outputStream) {
          outputStream.close();
        }
        if (null != tarArchiveInputStream) {
          tarArchiveInputStream.close();
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

  @Override
  public List<String> listFiles(File file) {
    List<String> ret = new LinkedList<>();
    InputStream input = null;
    TarArchiveInputStream tarArchiveInputStream = null;
    ArchiveEntry archiveEntry;
    try {
      input = new FileInputStream(file);
      tarArchiveInputStream = new TarArchiveInputStream(input);

      while (null != (archiveEntry = tarArchiveInputStream.getNextEntry())) {
        String archiveEntryFileName = archiveEntry.getName();
        ret.add(archiveEntryFileName);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {

        if (null != tarArchiveInputStream) {
          tarArchiveInputStream.close();
        }
        if (null != input) {
          input.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return ret;
  }
}
