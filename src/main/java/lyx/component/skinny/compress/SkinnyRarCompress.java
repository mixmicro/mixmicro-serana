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

import com.github.junrar.ContentDescription;
import com.github.junrar.Junrar;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lyx.component.skinny.Injection;
import lyx.component.skinny.SkinnyParallelCompress;

/**
 * {@link SkinnyRarCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/15
 */
@Injection(name = "Rar")
public class SkinnyRarCompress extends SkinnyParallelCompress {

  private static final String RAR_SUFFIX = ".rar";

  @Override
  public boolean compress(File[] sourceFiles, String filePath, String fileName, boolean isDeleteSourceFile) {
    throw new UnsupportedOperationException("RAR Compress haven't been support,but you can decompress");
  }

  @Override
  public boolean compress(File[] sourceFiles, File file, boolean isDeleteSourceFile) {
    throw new UnsupportedOperationException("RAR Compress haven't been support,but you can decompress");
  }

  @Override
  public boolean decompress(File file, String targetDir) {
    return decompress(file, new File(targetDir));
  }

  @Override
  public boolean decompress(File file, File targetDir) {
    if (!file.getName().endsWith(RAR_SUFFIX)) {
      throw new IllegalArgumentException("Suffix name error, your input filename is: " + file.getName());
    }

    try {
      if (!targetDir.isDirectory() && !targetDir.mkdirs()) {
        throw new IOException("failed to create directory " + targetDir);
      }
      Junrar.extract(file, targetDir);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public List<String> listFiles(File file) {
    List<ContentDescription> contentDescriptions = null;
    try {
      contentDescriptions = Junrar.getContentsDescription(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return contentDescriptions.stream().map((desc) -> desc.path).collect(Collectors.toList());
  }
}
