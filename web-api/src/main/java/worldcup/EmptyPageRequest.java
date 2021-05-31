package worldcup;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class EmptyPageRequest implements Pageable {
   
	public static final EmptyPageRequest INSTANCE = new EmptyPageRequest();

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public Sort getSort() {
    	return Sort.unsorted();
    }

    @Override
    public Pageable next() {
        return INSTANCE;
    }

    @Override
    public Pageable previousOrFirst() {
        return INSTANCE;
    }

    @Override
    public Pageable first() {
        return INSTANCE;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
