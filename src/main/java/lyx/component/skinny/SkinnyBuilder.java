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

import static lyx.component.skinny.Skinny.DEFAULT_ENCODING;
import static lyx.component.skinny.Skinny.DEFAULT_OUTPUT_SIZ;

import lyx.component.skinny.Skinny.CompressType;

/**
 * {@link SkinnyBuilder}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/13
 */
public class SkinnyBuilder {

  /**
   * true if need to parallel.
   */
  private boolean isParallel = false;
  /**
   * size of single block.
   */
  private long blockSize;
  /**
   * the block number.
   */
  private int blocks;
  private CompressType typ;
  private String outputName;
  /**
   * Only used for rar5.
   */
  private boolean ignoreFolder = true;
  /**
   * output siz. Default is 4kb, if you want bigger siz that you can set it through {@link SkinnyBuilder#outputSiz(int)}
   */
  private int outputSize = DEFAULT_OUTPUT_SIZ;
  private String compressEncode = DEFAULT_ENCODING;
  private String decompressEncode = DEFAULT_ENCODING;

  public SkinnyBuilder() {

  }

  public SkinnyBuilder isParallel(boolean isParallel) {
    this.isParallel = isParallel;
    return this;
  }

  public SkinnyBuilder blockSize(long blockSize) {
    this.blockSize = blockSize;
    return this;
  }

  public SkinnyBuilder blocks(int blocks) {
    this.blocks = blocks;
    return this;
  }

  public SkinnyBuilder compressionTyp(CompressType typ) {
    this.typ = typ;
    return this;
  }

  public SkinnyBuilder outputSiz(int outputSize) {
    this.outputSize = outputSize;
    return this;
  }

  public SkinnyBuilder outputName(String outputName) {
    this.outputName = outputName;
    return this;
  }

  public SkinnyBuilder ignoreFolder(boolean ignoreFolder) {
    this.ignoreFolder = ignoreFolder;
    return this;
  }

  public SkinnyBuilder compressEncode(String compressEncode) {
    this.compressEncode = compressEncode;
    return this;
  }

  public SkinnyBuilder decompressEncode(String decompressEncode) {
    this.decompressEncode = decompressEncode;
    return this;
  }


  public Skinny build() {
    return new Skinny(
        typ,
        new SkinnyContext(
            blockSize,
            blocks,
            outputSize,
            outputName,
            ignoreFolder,
            compressEncode,
            decompressEncode));
  }
}
