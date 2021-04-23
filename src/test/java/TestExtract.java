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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.sf.sevenzipjbinding.ArchiveFormat;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

/**
 * {@link TestExtract}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/23
 */
public class TestExtract {


  public static void main(String[] args) throws IOException {
    TestExtract app = new TestExtract("/Users/eliasyao/Desktop/rar5.rar");
    app.tryExtract("/Users/eliasyao/Desktop/");
  }

  private String archiveFilename;
  // 文件输出流
  private FileOutputStream output;// <------------------

  /**
   * 构造方法，初始化压缩文件路径
   *
   * @param filename
   */
  public TestExtract(String filename) {
    this.archiveFilename = filename;
  }

  /**
   * 尝试解压某个文件
   *
   * @param filepath
   * @throws IOException
   */
  public void tryExtract(String filepath) throws IOException {
    // 打开压缩文件
    RandomAccessFile randomAccessFile = new RandomAccessFile(archiveFilename, "r");
    IInArchive inArchive = SevenZip.openInArchive(ArchiveFormat.RAR5, // 自动选择解压格式
        new RandomAccessFileInStream(randomAccessFile));

    // 查询文件
    int count = inArchive.getNumberOfItems();
    for (int i = 0; i < count; i++) {
      String path = (String) inArchive.getProperty(i, PropID.PATH);
      Boolean isFolder = (Boolean) inArchive.getProperty(i, PropID.IS_FOLDER);

      if (isFolder) {
        // 文件夹勿需解压，跳过
        continue;
      }

      // 记录开始时间
      long start = System.currentTimeMillis();

      // 检查文件夹是否存在，不存在则创建
      File dir = new File(filepath);
      if (!dir.exists()) {
        dir.mkdirs();
      }

      // 打开文件流
      File file = new File(filepath + path);
      output = new FileOutputStream(file);//<------------

      // 解压
      inArchive.extract(new int[]{i}, false, callback);

      // 关闭文件流
      output.flush();
      output.close();
      output = null;

      // 显示解压用时
      long time = System.currentTimeMillis() - start;
      System.out.println("time: " + time);

      // 终止循环
    }

    // 关闭压缩文件
    inArchive.close();
  }

  // 实现 ISequentialOutStream 接口，打印方法参数。
  ISequentialOutStream out = new ISequentialOutStream() {
    @Override
    public int write(byte[] data) throws SevenZipException {
      try {
        if (output != null) {
          // 写文件
          output.write(data);//<----------
        }
      } catch (IOException e) {
        throw new SevenZipException(e);
      }

      return data.length;
    }
  };

  // 实现 IArchiveExtractCallback 接口，打印方法参数。
  IArchiveExtractCallback callback = new IArchiveExtractCallback() {
    @Override
    public void setTotal(long total) throws SevenZipException {
      System.out.println("total:" + total);
    }

    @Override
    public void setCompleted(long complete) throws SevenZipException {
      System.out.println("complete:" + complete);
    }

    @Override
    public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
      System.out.println("getStream:" + index + ", " + extractAskMode);
      return out;
    }

    @Override
    public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
      System.out.println("prepare:" + extractAskMode);
    }

    @Override
    public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
      System.out.println("result:" + extractOperationResult);
    }
  };

}