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

import de.innosystec.unrar.rarfile.FileHeader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lyx.component.skinny.Compress;
import lyx.component.skinny.ExtractItemsStandardCallback;
import lyx.component.skinny.Skinny;
import lyx.component.skinny.Skinny.CompressType;
import net.sf.sevenzipjbinding.SevenZip;
import org.apache.commons.compress.utils.IOUtils;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;

/**
 * {@link TestRarCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/15
 */
public class TestRarCompress {

  Compress compress;

  {
    compress = Skinny.builder()
        .outputSiz(1024 * 4)
        .compressionTyp(CompressType.RAR5)
        .build().getCompress();
  }


  private void testList() {
    List<String> strings = compress.listFiles(new File("/Users/eliasyao/Desktop/asa.rar"));
    System.out.println(strings);
  }

  private void testDeCompress() {
    compress.decompress(new File("/Users/eliasyao/Desktop/asa.rar"),
        "/Users/eliasyao/Desktop/skinny/testdata/temp");
  }

  private void test() throws Exception {
//    try {
//      BufferedInputStream inputStream = new BufferedInputStream(
//          Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("/Users/eliasyao/Desktop/宁波地华2.27.rar")));
//
//      FileOutputStream outFileRar = new FileOutputStream("/Users/eliasyao/Desktop/123.rar");
//      inputStream.reset();
//      IOUtils.copy(inputStream, outFileRar);
//
//      Archive archive = new Archive(new NativeStorage(new File("/Users/eliasyao/Desktop/123.rar")));
//      final List<de.innosystec.unrar.rarfile.FileHeader> list = archive.getFileHeaders();
//
//      for (FileHeader header : list) {
//        if (header.isDirectory()) {
//          continue;
//        }
//        final File newFile = new File(new File("/Users/eliasyao/Desktop/skinny/testdata/temp"), getFileName(header));
//        System.out.println(newFile);
//        try (FileOutputStream out = new FileOutputStream(newFile)) {
//          archive.extractFile(header, out);
//        }
//      }
//    } catch (Exception e) {
//    }

    SevenZip.initSevenZipFromPlatformJAR();
    final Map<String, byte[]> extract = ExtractItemsStandardCallback.extract(new File("/Users/eliasyao/Desktop/asa.rar"), true);

    extract.forEach((fileName, bytes) -> {
      final Path path = Paths.get("/Users/eliasyao/Desktop/skinny/testdata/temp", fileName);
      try {
        Files.write(path, bytes);
      } catch (IOException e) {
      }
    });
  }

  private String getFileName(FileHeader header) {
    return header.getFileNameW().length() > 0 ? header.getFileNameW() : header.getFileNameString();
  }

  public static void main(String[] args) throws Exception {
    TestRarCompress t = new TestRarCompress();
    t.testList();
//    t.testDeCompress();
  }
}
