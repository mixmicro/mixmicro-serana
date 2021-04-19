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

/**
 * {@link Skinny}As everyone knows that pure java have a standard gzip library,
 * * you can use if you want easy to use and fewer errors,but if you want (de)compress
 * * faster or big data, the standard library can't satisfied you but skinny can do it.
 * * <p>
 * * The Skinny will split compression into blocks that are compressed in parallel.
 * * This can be used for compressing big amounts data. The output is standard gzip file.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/13
 */
public class Skinny {

  protected static final int DEFAULT_OUTPUT_SIZ = 1024 * 4; // 4kb.
  protected static final String DEFAULT_ENCODING = "UTF-8";
  private CompressType typ;
  private SkinnyContext context;

  private Skinny() {
    throw new UnsupportedOperationException(
        "[Illegal Construction] if you want instanced skinny that you can use builder constructor.");
  }

  protected Skinny(CompressType typ, SkinnyContext context) {
    this.typ = typ;
    this.context = context;
  }

  public static SkinnyBuilder builder() {
    return new SkinnyBuilder();
  }

  public Compress getCompress() {
    return SkinnyFactory.INSTANCE.getCompressor(typ.desc, context);
  }

  public enum CompressType {
    SEVENZ(0, "7z"),
    ZIP(1, "Zip"),
    IMAGE(2, "Image"),
    AR(3, "Ar"),
    BZIP2(4, "Bzip2"),
    CPIO(5, "Cpio"),
    GZIP(6, "Gzip"),
    JAR(7, "Jar"),
    PDF(8, "Pdf"),
    RAR(9, "Rar"),
    TARBZ2(10, "TarBz2"),
    TAR(11, "Tar"),
    TARGZ(12, "TarGz"),
    XZ(13, "Xz"),
    RAR5(14, "Rar5");

    private int code;
    private String desc;

    CompressType(int code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    public static String getName(int code) {
      for (CompressType value : values()) {
        if (value.code == code) {
          return value.desc;
        }
      }
      throw new IllegalArgumentException("[Illegal Argument] The maximum code currently supported for compression type is 2.");
    }
  }
}
