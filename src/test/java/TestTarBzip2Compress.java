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
import lyx.component.skinny.Compress;
import lyx.component.skinny.Skinny;
import lyx.component.skinny.Skinny.CompressType;

/**
 * {@link TestTarBzip2Compress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/15
 */
public class TestTarBzip2Compress {

  Compress compress;

  {
    compress = Skinny.builder()
        .outputSiz(1024 * 4)
        .compressionTyp(CompressType.TARBZ2)
        .outputName("te.json")
        .build().getCompress();
  }

  private void testCompress() {
    File[] files = new File[]{new File("/Users/eliasyao/Desktop/skinny/testdata/test.json")};
    compress.compress(files, new File("/Users/eliasyao/Desktop/skinny/testdata/test.tar.bz2"), false);
  }

  private void testDeCompress() {
    compress.decompress(new File("/Users/eliasyao/Desktop/skinny/testdata/test.tar.bz2"),
        "/Users/eliasyao/Desktop/skinny/testdata/temp");
  }

  public static void main(String[] args) {
    TestTarBzip2Compress t = new TestTarBzip2Compress();
    t.testCompress();
    t.testDeCompress();
  }
}
