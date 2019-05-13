package niofeigncore.register;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

public class AllTypeFilter implements TypeFilter {

    private List<TypeFilter> delegates;

    public AllTypeFilter(List<TypeFilter> delegates) {
        Assert.notNull(delegates, "This argument is required, it must not be null");
        this.delegates = delegates;
    }

    @Override
    public boolean match(MetadataReader metadataReader,
                         MetadataReaderFactory metadataReaderFactory) throws IOException {
        for (TypeFilter filter : this.delegates) {
            if (!filter.match(metadataReader, metadataReaderFactory)) {
                return false;
            }
        }
        return true;
    }
}
