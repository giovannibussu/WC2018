package worldcup;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class LimitOffsetPageRequest 
{
	public final Pageable pageable;
	public final long realOffset;
	public final int realLimit;
	
	public LimitOffsetPageRequest(Long offset, Integer limit)
	{
		realOffset = (offset == null || offset < 0) ? 0 : offset;
		
		realLimit  = (limit == null) ? 25 :
					 (limit <= 0) ? 0 : 
					 (limit >= 1000) ? 1000 : limit;
		
		if (realLimit == 0)
		{
			pageable = EmptyPageRequest.INSTANCE;
		}
		else
		{
			pageable = PageRequest.of(getPagina(realOffset,realLimit).intValue(), realLimit);
		}
	}
	
	public LimitOffsetPageRequest(Long offset, Integer limit, Sort sort)
	{
		realOffset = (offset == null || offset < 0) ? 0 : offset;
		
		realLimit  = (limit == null) ? 25 :
			 (limit <= 0) ? 0 : 
			 (limit >= 1000) ? 1000 : limit;
		
		if (realLimit == 0)
		{
			pageable = EmptyPageRequest.INSTANCE;
		}
		else
		{
			pageable = PageRequest.of(getPagina(realOffset,realLimit).intValue(), realLimit, sort);
		}
	}
	
	private static Long getPagina(long offset, int limit)
	{		
		return Math.floorDiv(offset, limit);
	}
}

/* 
 * fpi.link.it:8445
 * 
 * hostname dock1: 192.168.10.104
 */
 