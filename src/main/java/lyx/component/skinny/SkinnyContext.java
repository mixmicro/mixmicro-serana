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

import static lyx.component.skinny.Skinny.DEFAULT_OUTPUT_SIZ;

/**
 * {@link SkinnyContext}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/14
 */
public class SkinnyContext {

  private long blockSize;
  private int blocks;
  private int outputSize;
  private String outputName;
  private boolean ignoreFolder;
  private String compressEncode;
  private String decompressEncode;

  protected SkinnyContext(
      long blockSize,
      int blocks,
      int outputSize,
      String outputName,
      boolean ignoreFolder,
      String compressEncode,
      String decompressEncode
  ) {
    this.blockSize = blockSize;
    this.blocks = blocks;
    this.outputSize = outputSize;
    this.outputName = outputName;
    this.ignoreFolder = ignoreFolder;
    this.compressEncode = compressEncode;
    this.decompressEncode = decompressEncode;
  }

  public long getBlockSize() {
    return blockSize;
  }

  public int getBlocks() {
    return blocks;
  }

  public int getOutputSize() {
    return outputSize;
  }

  public String getOutputName() {
    return outputName;
  }

  public boolean getIgnoreFolder() {
    return ignoreFolder;
  }

  public String getCompressEncode() {
    return compressEncode;
  }

  public String getDecompressEncode() {
    return decompressEncode;
  }
}
