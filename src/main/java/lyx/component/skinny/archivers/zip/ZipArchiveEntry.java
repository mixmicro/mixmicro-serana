package lyx.component.skinny.archivers.zip;

import lyx.component.skinny.archivers.ArchiveEntity;

import java.util.Date;

/**
 * {@link ZipArchiveEntry}
 *
 * @author <a href="mailto:siran0611@gmail.com">siran.yao</a>
 * @version ${project.version}
 * @date 2021/4/13
 */
public class ZipArchiveEntry implements ArchiveEntity {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public Date getLastModifiedDate() {
        return null;
    }
}
