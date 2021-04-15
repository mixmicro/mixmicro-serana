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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import lyx.component.skinny.Compress;
import lyx.component.skinny.Skinny;
import lyx.component.skinny.Skinny.CompressType;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

/**
 * {@link TestTarCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/14
 */
public class TestTarCompress {

  Compress compress;

  {
    compress = Skinny.builder()
        .outputSiz(1024 * 4)
        .compressionTyp(CompressType.TAR)
        .build().getCompress();
  }

  private void testCompress() {
    File[] files = new File[]{new File("/Users/eliasyao/Desktop/skinny/testdata/test.json")};
    compress.compress(files, new File("/Users/eliasyao/Desktop/skinny/testdata/test.tar"), false);
  }

  private void testList(){
    List<String> strings = compress.listFiles(new File("/Users/eliasyao/Desktop/skinny/testdata/test.tar"));
    System.out.println(strings);
  }

  private void testDeCompress() {
    compress.decompress(new File("/Users/eliasyao/Desktop/skinny/testdata/test.tar"),
        "/Users/eliasyao/Desktop/skinny/testdata/temp");
  }

  public void testCount() throws Exception {
    final OutputStream fos = Files.newOutputStream(new File("/Users/eliasyao/Desktop/skinny/testdata/test.tar").toPath());

    final ArchiveOutputStream tarOut =new ArchiveStreamFactory()
        .createArchiveOutputStream(ArchiveStreamFactory.TAR, fos);

    final File file1 = new File("/Users/eliasyao/Desktop/skinny/testdata/test.json");
//        final File file1 = getFile("/Users/eliasyao/Desktop/skinny/testdata/test.json");
    final TarArchiveEntry sEntry = new TarArchiveEntry(file1, file1.getName());
    tarOut.putArchiveEntry(sEntry);

    final InputStream in = Files.newInputStream(file1.toPath());
    final byte[] buf = new byte[4196];

    int read = 0;
    while ((read = in.read(buf)) > 0) {
      tarOut.write(buf, 0, read);
    }

    in.close();
    tarOut.closeArchiveEntry();
    tarOut.close();

  }

  public static void main(String[] args) throws Exception {
    TestTarCompress t = new TestTarCompress();
    t.testCompress();
    t.testList();
    t.testDeCompress();
  }
}
