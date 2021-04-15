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
import lyx.component.skinny.Injection;
import lyx.component.skinny.SkinnyParallelCompress;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

/**
 * {@link SkinnyGzipCompress} Gzip compress only support single file compression
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/15
 */
@Injection(name = "Gzip")
public class SkinnyGzipCompress extends SkinnyParallelCompress {

  private static final String GZ_SUFFIX = ".gz";

  @Override
  public boolean compress(File[] sourceFiles, String filePath, String fileName, boolean isDeleteSourceFile) {
    return compress(sourceFiles, new File(filePath, fileName), isDeleteSourceFile);
  }

  @Override
  public boolean compress(File[] sourceFiles, File file, boolean isDeleteSourceFile) {
    InputStream inputStream = null;
    GzipCompressorOutputStream gzipCompressorOutputStream = null;

    if (!file.getName().endsWith(GZ_SUFFIX)) {
      throw new IllegalArgumentException("Suffix name error, your input filename is: " + file.getName());
    }

    if (sourceFiles == null || sourceFiles.length <= 0) {
      return false;
    }

    try {
      gzipCompressorOutputStream = new GzipCompressorOutputStream(Files.newOutputStream(file.toPath()));
      for (File sourceFile : sourceFiles) {
        inputStream = new FileInputStream(sourceFile);
        byte[] buffer = new byte[super.getContext().getOutputSize()];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          gzipCompressorOutputStream.write(buffer, 0, length);
        }
      }
      gzipCompressorOutputStream.flush();

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
        if (null != gzipCompressorOutputStream) {
          gzipCompressorOutputStream.close();
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
    GzipCompressorInputStream gzipCompressorInputStream = null;
    try {
      inputStream = new FileInputStream(file);
      gzipCompressorInputStream = new GzipCompressorInputStream(inputStream);

      if (!targetDir.isDirectory() && !targetDir.mkdirs()) {
        throw new IOException("failed to create directory " + targetDir);
      }

      byte[] buffer = new byte[super.getContext().getOutputSize()];
      outputStream = new FileOutputStream(new File(targetDir, super.getContext().getOutputName()));
      int length;
      while ((length = gzipCompressorInputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
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
        if (null != gzipCompressorInputStream) {
          gzipCompressorInputStream.close();
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
